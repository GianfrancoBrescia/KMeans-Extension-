package server;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import database.DbAccess;
import database.DatabaseConnectionException;

import java.util.List;
import java.util.ArrayList;

/**
 * <p>Title: KmeansServer (Estensione)</p>
 * <p>Description: Il progetto <b>KmeansServer (Estensione)</b> realizza un Server in grado di acquisire le richieste effettuate da parte del 
 * Client e di inviare le rispettive risposte. Inoltre il Server colleziona le classi per l'esecuzione dell'algoritmo kmeans (scoperta di 
 * cluster, (de)serializzazione)).</p>
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: Dipartimento di Informatica, Università degli studi di Bari</p>
 * <p>Class description: ServerApp<br>
 * Definizione della classe ServerApp che modella una GUI per il Server. In particolare essa rappresenta un JFrame che mostra le diverse 
 * operazioni che i Client effettuano durante il periodo connessione - disconnesione. La finestra possiede un'area di testo non editabile in cui 
 * vengono inserite tutte le operazioni che vengono richieste al Server man mano che si verificano. Una seconda area di testo non editabile 
 * mostra le operazioni per i singoli Client selezionabili attraverso una JComboBox.<br>
 * Ogni volta che un Client si connette al Server viene aggiunto un nuovo elemento alla JComboBox in modo tale da permettere la successiva 
 * selezione per mostrare le operazioni per quel singolo Client. La selezione può avvenire nel caso in cui si connettono almeno due Client, 
 * altrimenti c'è a disposizione l'area di testo "globale" che contiene tutte le operazioni effettuate da quel singolo Client.<br>
 * L'avvio della GUI comporterà la visualizzazione di una finestra modale per l'inserimento dei dati relativi alla base di dati. Se i dati 
 * inseriti non sono corretti, e quindi non è possibile connettersi al database, per assicurare un utilizzo corretto del sistema da parte dei 
 * Client, sarà necessario reinserire i dati.
 * <center><img src="images/schermata_Server.jpg"></center><br><br>
 * <center><img src="images/schermata_Server_2.jpg"></center><br><br>
 * <center><img src="images/schermata_Server_3.jpg"></center><br><br>
 * Quando si decide di avviare un nuovo processo di Clustering si avrà la seguente schermata<br><br>
 * <center><img src="images/centroids_from_database.jpg"></center><br><br>
 * Invece, quando si decide di recuperare cluster precedentemente serializzati in un file si avrà la seguente schermata<br><br>
 * <center><img src="images/centroids_from_file.jpg"></center><br></p>
 * @author Gianfranco Brescia
 * @version 2.0
 */
@SuppressWarnings("serial")
public class ServerApp extends JFrame {
	/**Area di testo non editabile contenente tutte le operazioni che avvengono sul Server da qualsiasi Client*/
	JTextArea serverOutput = new JTextArea(20, 17);
	
	/**Area di testo non editabile che contiene tutte le operazioni effettuate da un singolo Client*/
	private JTextArea clientInfo = new JTextArea(20, 25);
	
	/**Scroll per entrambe le due aree di testo*/
	private JScrollPane serverScroll;
	
	/**JComboBox per la selezione delle operazioni relative ad un unico Client*/
	private JComboBox<Integer> comboClient = new JComboBox<Integer>();
	
	/**ArrayList contenente tutte le operazioni effettuate dai Client. L'ArrayList è così gestito:<br>
	 * - ciascun indice dell'ArrayList equivale all'identificativo del Client
	 * - l'identificativo del client è creato dalla classe ServerOneClient e rappresenta un numero intero progressivo*/
	private List<String> operaz = new ArrayList<String>();
		
	/**Finestra modale per l'inserimento dei dati per permettere l'utilizzo del database*/
	private DatabaseDialog dbDialog;
	
	/**Dati inseriti all'interno della finestra modale dbDialog*/
	private List<String> dialogData;
	
	/**Variabile booleana che indica se non è stata effettuata ancora alcuna operazione. Il flag è utilizzato all'interno dell'ascoltatore 
	 * associato alla JComboBox comboClient in modo tale da evitare che vengano richiamate le funzioni al suo interno, non appena la JComboBox 
	 * viene creata. Una volta che almeno un'operazione è stata effettuata il flag diventa false e i metodi presenti nell'ascoltatore possono 
	 * essere chiamati.*/
	boolean start = true;
	
	
	/**
	 * Questo metodo è il costruttore della classe ServerApp che si occupa di assegnare alla finestra un titolo e di inizializzare il contenuto 
	 * del JFrame
	 * @param nome Titolo della finestra
	 */
	ServerApp(String nome){
		this.setTitle(nome);
		initApp();
	}
	
	/**
	 * Questo metodo viene richiamato dal costruttore per creare la finestra. Esso assegna alla finestra una dimensione, la rende non 
	 * ridimensionabile e inizializza la finestra modale relativa all'inserimento delle informazioni del database.<br>
	 * La finestra è suddivisa in due parti:<br>
	 * 1. nella parte sinistra ci sarà l'area di testo con le operazioni effettuate da tutti i Client<br>
	 * 2. nella parte destra ci sarà l'area di testo con le operazioni effettuate da un singolo Client selezionato attraverso la JComboBox 
	 * comboClient<br>
	 * Inoltre questo metodo si occuperà di avviare il Server richiamando il metodo startServer.
	 */
	
	private void initApp(){
		setSize(new Dimension(740, 550));
		getContentPane().setLayout(new FlowLayout());
		setResizable(false);
		dbDialog = new DatabaseDialog(this, "Impostazioni Database");		
		serverOutput.setEditable(false);
		serverOutput.setLineWrap(true);
		serverOutput.setMargin(new Insets(10,10,10,10));

		JPanel mainLeft = new JPanel();
		JPanel mainRight = new JPanel();
		mainLeft.setPreferredSize(new Dimension(400, 500));
		mainLeft.setLayout(new BorderLayout());
		mainLeft.add(new JLabel("Operazioni dei Client connessi"), BorderLayout.NORTH);
		serverScroll = new JScrollPane(serverOutput);
		serverOutput.setEditable(false);
		serverOutput.setLineWrap(true);
		mainLeft.add(serverScroll, BorderLayout.CENTER);
		mainRight.setLayout(new BorderLayout());		
		JPanel panClients = new JPanel();
		panClients.setLayout(new BoxLayout(panClients, BoxLayout.PAGE_AXIS));
		panClients.add(new JLabel("Seleziona Client:"));
		comboClient.setEnabled(false);
		panClients.add(comboClient);
		mainRight.add(panClients, BorderLayout.NORTH);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				int scelta = JOptionPane.showConfirmDialog(null, "Sicuro di voler uscire?\nL'uscita comporterà " + "la chiusura del server", "KMeans", JOptionPane.YES_NO_OPTION);
				if(scelta == JOptionPane.YES_OPTION)
					System.exit(0);
			}
		});
		
		comboClient.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(start == false){
					if (e.getStateChange() == ItemEvent.SELECTED) {
						getClientActions();
					}
				}
			}

		});

		serverScroll = new JScrollPane(clientInfo);
		clientInfo.setEditable(false);
		clientInfo.setLineWrap(true);
		mainRight.add(serverScroll, BorderLayout.SOUTH);

		add(mainLeft);
		add(mainRight);
		setLocationRelativeTo(null);
		setVisible(true);
		
		startServer();
	}
	
	/**
	 * Questo metodo istanzia un oggetto della classe MultiServer passandogli la porta (8080) su cui sarà in ascolto e il riferimento a questa 
	 * finestra
	 */
	private void startServer(){
		new MultiServer(8080, this);
	}
	
	/**
	 * Questo metodo si occupa di rendere visibile la finestra modale relativa all'inserimento dei dati per permettere l'utilizzo del database. 
	 * Una volta che l'inserimento è avvenuto, viene effettuato un controllo per verificare che i dati inseriti siano corretti. Questo metodo 
	 * infatti richiama il metodo testDB che effettua un test di connessione sul database per assicurare che i dati inseriti siano corretti.<br>
	 * Nel caso in cui il test di connessione al database vada a buon fine, vengono salvati i dati, la finestra di dialogo viene chiusa e le 
	 * risorse associate alla finestra vengono distrutte. Altrimenti la finestra di dialogo per l'inserimento dei dati del database verrà 
	 * visualizzata nuovamente.
	 */
	void setDB(){
		boolean dbFlag = false;
		while(!dbFlag){
			dbDialog.setVisible(true);
			dialogData = dbDialog.getSelectedValues();
			dbFlag = testDB();
			if(!dbFlag){
				dbDialog.resetFields();
			}
		}
		JOptionPane.showMessageDialog(this, "Connessione al database avvenuta correttamente!");
		dbDialog.dispose();
	}
	
	/**
	 * Questo metodo si occupa di effettuare un test di connessione sulla base di dati per assicurare un utilizzo corretto del sistema, in modo 
	 * tale da permettere al Client di poter effettuare operazioni di scoperta sulla base di dati senza ricevere errori. Questo metodo richiama 
	 * il metodo initConnection(), definito nella classe DbAccess, che effettua una connessione al database. Se la connessione va a buon fine il 
	 * metodo restituisce <b>true</b> altrimenti restituisce <b>false</b>, e la finestra di dialogo per l'inserimento dei dati del database 
	 * verrà visualizzata nuovamente.
	 * @return res Valore booleano che restituisce <b>true</b> se il test va a buon fine, <b>false</b> altrimenti
	 */
	
	boolean testDB(){
		boolean res = false;
		DbAccess mainDB = new DbAccess(dialogData);
		try {
			mainDB.initConnection();
			res = true;
		} catch (DatabaseConnectionException e) {
			JOptionPane.showMessageDialog(this, e.getMessage() + "\nRiprova!");
		} catch (ClassNotFoundException e) {
			JOptionPane.showMessageDialog(this, e.getMessage());
		}
		return res;
	}
	
	/**
	 * Questo metodo si occupa di aggiungere alla JComboBox comboClient l'id del Client. Una volta connesso mostra un messaggio di avvenuta 
	 * connessione nella JTextArea serverOutput.
	 * @param id Identificativo del Client 
	 */
	void clientConnect(int id) {
		comboClient.addItem(id);
		this.comboClient.setEnabled(true);
		@SuppressWarnings("unused")
		String servOut = serverOutput.getText();
		setOutputText("\n----- Connesso - Client id: " + id);
	}
	
	/**
	 * Questo metodo si occupa di mostrare un messaggio di avvenuta disconnessione nella JTextArea serverOutput da parte del Client identificato 
	 * dal suo id
	 * @param id Identificativo del Client
	 */
	void clientDisconnect(int id){
		@SuppressWarnings("unused")
		String servOut = serverOutput.getText();
		this.setOutputText("----- Disconnesso - Client id: " + id + "\n");
	}
	
	/**
	 * Questo metodo si occupa di aggiungere l'operazione effettuata dal Client, identificato dal parametro id, all'ArrayList delle operazioni 
	 * effettuate dai Client. Inoltre questo metodo aggiunge l'operazione effettuata con l'identificativo del Client all'area di testo 
	 * serverOutput.
	 * @param id Identificativo del Client
	 * @param op Operazione effettuata
	 */
	void setOp(int id, String op){
		int index = id - 1;
		String inx = "Client " + id + ": ";
		operaz.add(index, op);
		for(String item : operaz.get(id - 1).split("-"))
			setOutputText("\n" + inx + item);
		start = false;
	}
	
	/**
	 * Questo metodo è chiamato dall'ascoltatore associato alla JComboBox comboClient per mostrare nell'area di testo clientInfo le operazioni 
	 * effettuate dal Client con identificativo equivalente a quello inserito nella JComboBox
	 */
	void getClientActions(){
		int id = 0;
		if( (Integer)comboClient.getSelectedItem() != null ) {
			id = (Integer)comboClient.getSelectedItem();			
			clientInfo.setText("Operazione effettuate dal Client " + id + "\n");
			for(String item : operaz.get(id - 1).split("-"))
				clientInfo.setText(clientInfo.getText() + "\n" + item);
		}
	}
	
	/**
	 * Questo metodo restituisce i valori inseriti all'interno della finestra modale relativa alle impostazioni del database
	 * @return dialogData 
	 */
	List<String> getSelectedValues(){
		return dialogData;
	}
	
	/**
	 * Questo metodo imposta il testo da visualizzare 
	 * @param text Testo da visualizzare
	 */
	void setOutputText(String text){
		serverOutput.setText(serverOutput.getText() + "\n" + text);
	}
	
	/**Questo metodo è il main dell'applicazione e rappresenta il punto di avvio dell'applicazione. Inoltre in questo metodo viene creata la 
	 * GUI del Server.
	 * @param args Argomento di input
	 */
	public static void main(String[] args) {
		new ServerApp("KmeansServer (Estensione)");
	}
}