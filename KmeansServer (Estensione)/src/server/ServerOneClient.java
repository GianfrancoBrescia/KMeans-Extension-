package server;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;

import data.Data;
import data.OutOfRangeSampleSize;
import mining.KmeansMiner;
import database.DatabaseConnectionException;
import database.EmptySetException;
import database.NoValueException;

import org.jfree.data.general.DatasetUtilities;
import org.jfree.data.xy.XYDataset;

import analysis.ChartData;
import analysis.ChartI;
import analysis.GraphFactory;

/**
 * <p>Title: KmeansServer (Estensione)</p>
 * <p>Description: Il progetto <b>KmeansServer (Estensione)</b> realizza un Server in grado di acquisire le richieste effettuate da parte del 
 * Client e di inviare le rispettive risposte. Inoltre il Server colleziona le classi per l'esecuzione dell'algoritmo kmeans (scoperta di 
 * cluster, (de)serializzazione)).</p>
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: Dipartimento di Informatica, Università degli studi di Bari</p>
 * <p>Class description: ServerOneClient<br>
 * Definizione della classe ServerOneClient che estende la classe Thread, modellando appunto un thread in grado di ascoltare le richieste da 
 * parte di un singolo Client. Questa classe stabilisce con il Client un protocollo di comunicazione che si basa su uno scambio di messaggi 
 * (oggetti istanza della classe String). Infatti per ogni operazione che va a buon fine la classe ServerOneClient scrive sullo stream di 
 * output il messaggio "OK", altrimenti scrive un messaggio relativo all'eccezione. Questa classe si occupa di effettuare particolari 
 * operazioni in base alle richieste da parte del Client. Per la gestione delle richieste viene eseguito il metodo run().</p>
 * @author Gianfranco Brescia
 * @version 2.0
 */
public class ServerOneClient extends Thread {
	/**Socket che connette il Server con il Client*/
	private Socket socket;
	
	/**Stream di input che permette al Server di ricevere le richieste da parte del Client*/
	private ObjectInputStream in;
	
	/**Stream di output che permette al Server di inviare i risultati al Client*/
	private ObjectOutputStream out;
	
	/**Oggetto kmeans istanza della classe KmeansMiner*/
	private KmeansMiner kmeans;
	
	/**Oggetto data istanza della classe Data*/
	private Data data; 
	
	/**Numero di Client in contatto con il Server*/
	static private int countClient = 0;
	
	/**Codice del singolo Client*/
	int clientId;
	
	/**Oggetto istanza della classe ServerApp*/
	private ServerApp frame;
	
	/**Operazione che sta svolgendo il singolo Client*/
	private String operaz;
	
	/**Variabile booleana che indica se un'operazione è stata effettuata o meno*/
	private boolean flagOp = false; 
	
	/**
	 * Questo metodo è il costruttore della classe ServerOneClient che inizializza gli attributi socket, in e out. Associa un identificativo
	 * al Client connesso e inizializza anche l'attributo frame. Inoltre avvia il thread.
	 * @param s Oggetto istanza della classe Socket che modella la comunicazione con il Client e permette di ottenere i relativi stream di input
	 * e output
	 * @param app Oggetto istanza della classe ServerApp
	 * @throws IOException Questa eccezione è sollevata nel caso in cui risultano esserci dei problemi nell'ottenere i relativi stream di input e output
	 */
	public ServerOneClient(Socket s, ServerApp app) throws IOException {
		socket = s;
		in = new ObjectInputStream(socket.getInputStream());
		out = new ObjectOutputStream(socket.getOutputStream());
		clientId = ++countClient;
		frame = app;
		app.clientConnect(this.clientId);
		super.start();
	}
	
	/**Questo metodo sovrascrive il metodo run() della superclasse Thread al fine di gestire le richieste del Client*/
	@SuppressWarnings({ "unused", "rawtypes" })
	public void run() {
		String tableDb = "";
		int iterFromTable = 0;
		int numIter = 0;
		try {
			while( true ) {
				Object input = in.readObject();
				int sceltaClient = (Integer)input;
				switch( sceltaClient ) {
					case 0:
						if( operaz == null )
							operaz = "Connessione al Database";
						tableDb = (String)in.readObject();
						try{
							data = new Data(tableDb, frame.getSelectedValues());
						}catch(SQLException e){
							out.writeObject("Errore! Nome Tabella Errato!");
							break;
						}catch (EmptySetException e) {
							out.writeObject(e.getMessage());
							break;
						}catch (DatabaseConnectionException e) {
							out.writeObject(e.getMessage());
							break;
						}catch (NoValueException e) {
							out.writeObject(e.getMessage());
							break;
						}
						flagOp = true;
						out.writeObject("OK");
						break;
					case 1:
						iterFromTable = (Integer)in.readObject();
						operaz+= "\nEsecuzione KMeans (k = " + iterFromTable + ")";
						kmeans = new KmeansMiner(iterFromTable);
						try{
							numIter = kmeans.kmeans(data);
						}catch (OutOfRangeSampleSize e) {
							out.writeObject(e.getMessage());
							break;
						}
						out.writeObject("OK");
						out.writeObject(iterFromTable);
						out.writeObject(kmeans.getC().toString(data));
						break;
					case 2:
						operaz+= "\nSalvataggio del file eseguito con successo\n";
						try{
							kmeans.salva(tableDb+iterFromTable);
						}catch(IOException e){
							out.writeObject("Errore! Salvataggio del file non riuscito!");
							break;
						}
						out.writeObject("OK");
						break;
					case 3:
						String filetableName = (String)in.readObject();
						String iterate = (String)in.readObject();
						try{
							kmeans = new KmeansMiner(filetableName+iterate);
							operaz+= "\nEsecuzione KMeans da File (k = " + iterate + ")";
						}
						catch(FileNotFoundException e){
							out.writeObject("Errore! File Non Trovato!");
							break;
						}
						out.writeObject("OK");
						out.writeObject(kmeans.getC().toString());
						break;
					case 4:
						String chartType = "";
						int attrX, attrY;
						ChartData chartData = null;
						String[] attributes = null;
						String action = (String)in.readObject();
						if(action.equals("file"))
							attributes = kmeans.kmAttributesString();
						else
							attributes = data.getAttributesString();
						out.writeObject(attributes);
						ArrayList values = (ArrayList)in.readObject();
						attrX = (Integer)values.get(0);
						attrY = (Integer)values.get(1);
						chartType = (String)values.get(2);
						ChartI mainChart =	GraphFactory.creaGrafico(chartType, action, this.data);
						if(action.equals("file"))
							chartData = mainChart.setGraph(kmeans.getC(), kmeans.getCAttrib(attrX), kmeans.getCAttrib(attrY), action);
						else	
							chartData = mainChart.setGraph(kmeans.getC(), data.getAttributeSchema().get(attrX), data.getAttributeSchema().get(attrY), action);
						operaz+= "\nVisualizzazione del grafico di tipo( " + chartType + " )\n";
						out.writeObject("OK");
						if(chartType.equals("scatter")){
							XYDataset mainDataset = (XYDataset)chartData.getDataset();
							out.writeObject(mainDataset);
							out.writeObject(chartData.getLabelX());
							out.writeObject(chartData.getLabelY());
							out.writeObject(DatasetUtilities.findMaximumDomainValue(mainDataset));
							out.writeObject(DatasetUtilities.findMaximumRangeValue(mainDataset));
						} else{
							this.out.writeObject(chartData.getDataset());
							if(data != null)
								out.writeObject(this.data.getNumberOfExamples());
							else
								out.writeObject(this.kmeans.getC().getLength());
						}
						frame.setOp(clientId, operaz);
						break;
				}
			}
		}catch(IOException e) {
			if( !this.flagOp ) {
				frame.setOp(clientId, "Nessuna Operazione Effettuata\n");
			}
			frame.clientDisconnect(clientId);
		}catch(ClassNotFoundException e) {
			frame.setOutputText(e.getMessage());
		}finally{
			try {
				socket.close();
			}catch (IOException e) {
				frame.setOutputText(e.getMessage());
			}
		}	
	}
}
