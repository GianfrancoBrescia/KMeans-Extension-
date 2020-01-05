package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JOptionPane;

/**
 * <p>Title: KmeansServer (Estensione)</p>
 * <p>Description: Il progetto <b>KmeansServer (Estensione)</b> realizza un Server in grado di acquisire le richieste effettuate da parte del 
 * Client e di inviare le rispettive risposte. Inoltre il Server colleziona le classi per l'esecuzione dell'algoritmo kmeans (scoperta di 
 * cluster, (de)serializzazione)).</p>
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: Dipartimento di Informatica, Università degli studi di Bari</p>
 * <p>Class description: MultiServer<br>
 * Definizione della classe MultiServer che estende la classe Thread e che modella un thread in grado di ascoltare differenti richieste per 
 * differenti Client andando quindi a realizzare un Server che ascolta più richieste. Per ogni richiesta viene creato un thread a parte. Sarà 
 * tale thread ad ascoltare le richieste per un determinato Client.</p>
 * @author Gianfranco Brescia
 * @version 2.0
 */
class MultiServer extends Thread {
	/**Porta su cui il server sarà in ascolto*/
	private int PORT;
	
	/**Riferimento alla GUI del Server che permette la scrittura, nell'apposita area di testo, delle operazioni che i Client fanno o non fanno*/
	private ServerApp frame;
	
	/**
	 * Questo metodo è il costruttore della classe MultiServer che inizializza la porta su cui il Server sarà in ascolto e il riferimento alla
	 * GUI del Server. Inoltre questo metodo invoca il metodo run().
	 * @param port Porta su cui il Server sarà in ascolto
	 * @param app Riferimento ad un oggetto istanza della classe ServerApp
	 */
	MultiServer(int port, ServerApp app) {
		PORT = port;
		frame = app;
		super.start();
	}
	
	/**
	 * Questo metodo istanzia un oggetto istanza della classe ServerSocket che si occuperà di gestire le richieste da parte del Client. 
	 * Ad ogni nuova richiesta di connessione si istanzia ServerOneClient. 
	 */
	public void run() {
		ServerSocket s = null;
		try {
			frame.setDB();
			s = new ServerSocket(PORT);
			try {
				frame.serverOutput.setText("---- Server Avviato Correttamente! ---- \n Server in attesa di richieste...");
				while( true ) {
					Socket socket = s.accept();
					try {
						new ServerOneClient(socket, frame);
					}catch(IOException e) {
						JOptionPane.showMessageDialog(frame, e);
					}
				}
			}catch(IOException e) {
				JOptionPane.showMessageDialog(frame, "Errore nell'attesa di connessioni da parte dei client");
			}finally {
				try {
					s.close();
				}catch(IOException e) {
					JOptionPane.showMessageDialog(frame, "\nErrore! - Impossibile chiudere il Server sulla porta: " + PORT);
				}
			}
		}catch(IOException e) {
			frame.serverOutput.setText("Errore! - Impossibile creare il Server sulla porta: " + PORT);
		}
	}
}
