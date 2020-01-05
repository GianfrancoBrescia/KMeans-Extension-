import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;

import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.jfree.data.general.Dataset;
import org.jfree.data.general.PieDataset;
import org.jfree.data.xy.XYDataset;

/**
 * <p>Title: KmeansClient (Estensione)</p>
 * <p>Description: Il progetto <b>KmeansClient (Estensione)</b> realizza un sistema Client in grado di collegarsi al Server tramite l'indirizzo 
 * Ip e il numero di porta su cui il Server � in ascolto. Una volta instaurata la connessione l'utente pu� scegliere se avviare un nuovo 
 * processo di clustering oppure recuperare cluster precedentemente serializzati in un qualche file con visualizzazione del rispettivo 
 * grafico.</p>
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: Dipartimento di Informatica, Universit� degli studi di Bari</p>
 * <p>Class description: KMeans<br>
 * Definizione della classe KMeans che definisce l'interfaccia grafica del Client, attraverso un JFrame, suddivisa in due sezioni organizzate in tab: la PRIMA SEZIONE, 
 * identificata dal tab DB con relativa icona, si occupa di effettuare richieste al Server per l'esecuzione di un'attivit� di scoperta dei 
 * cluster sulla base di dati, eseguendo l'algoritmo del kmeans. In questo caso il Client rimarr� in attesa dei risultati da parte del 
 * Server, mostrando, se tutto procede correttamente, il risultato dell'algoritmo kmeans con il relativo grafico, altrimenti verr� 
 * mostrato un messaggio nel caso di errore. Inoltre la corretta esecuzione dell'algoritmo in questo caso produrr� sul Server un file il cui 
 * nome corrisponder� al nome della tabella specificato nell'apposita casella di testo concatenato al numero di cluster che sono stati 
 * scoperti.<br><br>
 * <center><img src="images/centroids_from_database.jpg"></center><br><br>
 * <center><img src="images/centroids_from_database_2.jpg"></center><br><br>
 * <center><img src="images/centroids_from_database_3.jpg"></center><br><br>
 * <center><img src="images/centroids_from_database_4.jpg"></center><br><br>
 * La SECONDA SEZIONE, invece, identificata dal tab FILE con relativa icona, si occupa di effettuare richieste al Server per effettuare 
 * una lettura da file contenente attivit� precendenti di scoperta dei cluster sulla base di dati. Il nome del file corrisponder� alla 
 * concatenazione tra il nome della tabella letta in precedenza e il numero di cluster inserito nell'apposita casella di testo. Nel caso 
 * in cui tale file esista verranno mostrati a video i risultati dell'algoritmo kmeans con annesso grafico, altrimenti verr� visualizzato un 
 * messaggio di errore.<br><br>
 * <center><img src="images/centroids_from_file.jpg"></center><br><br>
 * <center><img src="images/centroids_from_file_2.jpg"></center><br><br>
 * <center><img src="images/centroids_from_file_3.jpg"></center><br><br>
 * <center><img src="images/centroids_from_file_4.jpg"></center><br><br>
 * In entrambe le tipologie di richieste nel momento in cui esse vengono effettuate e, nel caso in cui tutto proceda correttamente, verr�
 * mostrata una finestra di dialogo modale per la scelta del grafico e se necessario degli attributi (relativi al grafico stesso).</p>
 * @author Gianfranco Brescia
 * @version 2.0
 */
@SuppressWarnings("serial")
public class KMeans extends JFrame {
	/**Stream di output che permette al Client di inviare le richieste al Server*/
	private ObjectOutputStream out;
	
	/**Stream di input che permette al Client di ricevere i risultati da parte del Server*/
	private ObjectInputStream in;
	
	/**Contiene il risultato dei dati da inserire all'interno del grafico, calcolati dal Server. Pu� essere di tipo 
	 * differente in base al tipo di grafico scelto nella finestra di dialogo modale che viene visualizzata.*/
	private Dataset streamDataset;
		
	/**
	 * <p>Title: KmeansClient (Estensione)</p>
	 * <p>Description: Il progetto <b>KmeansClient (Estensione)</b> realizza un sistema Client in grado di collegarsi al Server tramite 
	 * l'indirizzo ip e il numero di porta su cui il Server � in ascolto. Una volta instaurata la connessione l'utente pu� scegliere se avviare 
	 * un nuovo processo di clustering oppure recuperare cluster precedentemente serializzati in un qualche file con visualizzazione del 
	 * rispettivo grafico.</p>
	 * <p>Copyright: Copyright (c) 2018</p>
	 * <p>Company: Dipartimento di Informatica, Universit� degli studi di Bari</p>
	 * <p>Class description: TabbedPane<br>
	 * Definizione della Inner Class TabbedPane che estende la classe JPanel e che rappresenta la schermata principale dell'interfaccia 
	 * grafica che conterr� le due sezioni (tab) principali</p>
	 * @author Gianfranco Brescia
	 * @version 2.0
	 */
	private class TabbedPane extends JPanel {
		/**Tab per l'utilizzo delle funzionalit� sulla base di dati*/
		private JPanelCluster panelDB;
		
		/**Tab per l'utilizzo delle funzionalit� sul file*/
		private JPanelCluster panelFile;
		
		/**
		 * <p>Title: KmeansClient (Estensione)</p>
		 * <p>Description: Il progetto <b>KmeansClient (Estensione)</b> realizza un sistema Client in grado di collegarsi al Server tramite 
		 * l'indirizzo ip e il numero di porta su cui il Server � in ascolto. Una volta instaurata la connessione l'utente pu� scegliere se 
		 * avviare un nuovo processo di clustering oppure recuperare cluster precedentemente serializzati in un qualche file con visualizzazione 
		 * del rispettivo grafico.</p>
		 * <p>Copyright: Copyright (c) 2018</p>
		 * <p>Company: Dipartimento di Informatica, Universit� degli studi di Bari</p>
		 * <p>Class description: JPanelCluster<br>
		 * Definizione della Inner Class JPanelCluster che estende la classe JPanel e che rappresenta ogni singola sezione (tab) che fa 
		 * parte dell'interfaccia grafica. La sezione creata verr� poi aggiunta ad un JTabbedPane. Ciascuna sezione contiene al suo 
		 * interno:<br> 
		 * - due caselle di testo (una per il nome della tabella e una per il numero di cluster)<br>
		 * - un'area di testo scorrevole non editabile che conterr� il risultato da parte del Server<br>
		 * - un bottone per l'invio della richiesta al Server (che � diversa per ciascun tab)
		 * </p>
		 * @author Gianfranco Brescia
		 * @version 2.0
		 */
		private class JPanelCluster extends JPanel {
			/**
			 * Casella di testo in cui verr� inserito il nome della tabella. Tale nome coincider� sia con il nome della tabella del 
			 * database sia con il nome del file creato sul Server.
			 */
			private JTextField tableText = new JTextField(20);
			
			/**Casella di testo in cui verr� inserito il numero di cluster che si intende scoprire*/
			private JTextField kText = new JTextField(4);
			
			/**Area di testo non editabile che conterr� l'output fornito dal Server*/
			private JTextArea clusterOutput = new JTextArea(20, 20);
			
			/**
			 * Bottone che si occuper� di effettuare una particolare richiesta al Server. A questo bottone verr� associato un nome e 
			 * un ascoltatore (entrambi passati come argomenti al costruttore).
			 */
			private JButton executeButton;
			
			/**Pannello che conterr� il grafico*/
			private JPanel panelChart = new JPanel();
			
			/**
			 * Questo metodo � il costruttore della classe JPanelCluster che si occupa di creare ciascuna sezione (tab) inizializzando 
			 * il pannello come segue:<br>
			 * Nella parte sinistra ci saranno:<br>
			 * - il JPanel upPanel (panel iniziale) conterr� le due textfield per la lettura del nome della tabella e il numero di cluster<br>
			 * - il JPanel centralPanel (panel centrale) conterr� l'area di testo scorrevole non editabile contenente l'output del Server<br>
			 * - il JPanel downPanel (panel finale) conterr� il bottone per l'invio della richiesta al Server<br>
			 * Nella parte destra, invece, ci sar� il pannello contenente il relativo grafico e quando necessario anche la legenda con alcune 
			 * funzionalit� utili per gestire al meglio la visualizzazione del grafico stesso.<br>
			 * Inoltre questo metodo aggiunge l'ascoltatore a al bottone executeButton.
			 * @param buttonName Nome da assegnare al bottone executeButton
			 * @param a Ascoltatore da assegnare al bottone 
			 */
			JPanelCluster(String buttonName, java.awt.event.ActionListener a) {
				super(new FlowLayout());
				JScrollPane outputScrollPane = new JScrollPane(clusterOutput);
				JScrollPane graphPane = new JScrollPane(panelChart);
				JPanel mainPanelLeft = new JPanel();
				JPanel mainPanelRight = new JPanel();
				mainPanelLeft.setPreferredSize(new Dimension(350, 600));
				mainPanelLeft.setLayout(new BorderLayout());
				mainPanelRight.setLayout(new BorderLayout());
				
				JPanel upPanel = new JPanel();
				JPanel centralPanel = new JPanel();
				JPanel downPanel = new JPanel();
				//upPanel
				upPanel.add(new JLabel("Table: "));
				upPanel.add(tableText);
				upPanel.add(new JLabel("k: "));
				upPanel.add(kText);
				clusterOutput.setEditable(false);
				clusterOutput.setLineWrap(true);
				//centralPanel
				centralPanel.setLayout(new BorderLayout());
				centralPanel.add(outputScrollPane, BorderLayout.CENTER);
				//downPanel
				downPanel.setLayout(new FlowLayout());
				downPanel.add(executeButton = new JButton(buttonName));
				
				mainPanelLeft.add(upPanel, BorderLayout.NORTH);
				mainPanelLeft.add(centralPanel, BorderLayout.CENTER);
				mainPanelLeft.add(downPanel, BorderLayout.SOUTH);
				graphPane.setPreferredSize(new Dimension(550, 550));
				mainPanelRight.add(graphPane, BorderLayout.WEST);
								
				add(mainPanelLeft);
				add(mainPanelRight);
				executeButton.addActionListener(a);
			}
		}
		
		/**
		 * Questo metodo � il costruttore della classe TabbedPane che inizializza i membri panelDB e panelFile e li aggiunge ad un oggetto
		 * istanza della classe TabbedPane. Tale oggetto (di istanza TabbedPane) � quindi inserito nel pannello che si sta costruendo.
		 */
		TabbedPane() {
			super(new GridLayout(1,1));
			JTabbedPane jTabbedPane = new JTabbedPane();
			java.net.URL imgURL = getClass().getResource("db.jpg");
			ImageIcon iconDB = new ImageIcon(imgURL);
			panelDB = new JPanelCluster("MINE", new java.awt.event.ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						learningFromDBAction();
					}catch(SocketException e1) {
						JOptionPane.showMessageDialog(panelFile, "Errore! Impossibile connettersi al Server\n\nDettagli:\n" + e1);
					}catch(IOException e1) {
						JOptionPane.showMessageDialog(panelFile, e1);
					}catch(ClassNotFoundException e1) {
						JOptionPane.showMessageDialog(panelFile, e1);
					}
				}
			});
			jTabbedPane.addTab("DB", iconDB, panelDB, "Kmeans from Database");
			imgURL = getClass().getResource("file.jpg");
			ImageIcon iconFile = new ImageIcon(imgURL);
			panelFile = new JPanelCluster("STORE FROM FILE", new java.awt.event.ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						learningFromFileAction();
					}catch(SocketException e1) {
						JOptionPane.showMessageDialog(panelFile, "Errore! Impossibile connettersi al Server\n\nDettagli:\n" + e1);
					}catch(IOException e1) {
						JOptionPane.showMessageDialog(panelFile, e1);
					}catch(ClassNotFoundException e1) {
						JOptionPane.showMessageDialog(panelFile, e1);
					}
				}
			});
			jTabbedPane.addTab("FILE", iconFile, panelFile, "Kmeans from File");
			add(jTabbedPane);
			jTabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		}
		
		/**
		 * Questo metodo restituisce il riferimento all'oggetto istanza della classe Frame a cui verr� momentaneamente aggiunto 
		 * l'AttributeDialog
		 * @return Frame a cui aggiungere l'AttributeDialog
		 */
		private Frame findParentFrame() {
			Component c = getParent();
			while( true ) {
				if( c instanceof Frame ) 
					return (Frame)c;
				c = c.getParent();
			}
		}
		
		/**
		 * Questo metodo effettua un'attivit� di scoperta dei cluster sulla base di dati. Questo metodo acquisisce le due caselle di 
		 * testo corrispondenti al nome della tabella del database e al numero di cluster da scoprire. Qualora il valore 
		 * acquisito non fosse un numero positivo viene visualizzato un messaggio di errore all'interno di una JOptionPane.<br>
		 * Le diverse richieste al server vengono effettuate attraverso una serie di valori numerici:<br>
		 * 1. Il Client invia al Server il comando 0 e il nome della tabella, restando in attesa di una risposta da parte del Server.
		 * Questo comando corrisponde (sul Server) a un'interrogazione sulla base di dati. In caso di risposta diversa da "OK", 
		 * viene visualizzato un messaggio di errore in una JOptionPane e termina l'esecuzione del metodo.<br>
		 * 2. Altrimenti, il Client invia al Server il comando 1 e il numero di cluster da scoprire e aspetta una risposta da parte del
		 * Server. Questo comando corrisponde (sul Server) alla vera e propria attivit� di scoperta attraverso l'algoritmo kmeans. In caso
		 * di risposta diversa da "OK", viene visualizzato un messaggio di errore in una JOptionPane e termina l'esecuzione del metodo.<br>
		 * 3. Altrimenti il Client legge il numero di iterazioni e i cluster cos� come sono trasmessi dal Server e li visualizza in  
		 * panelDB.clusterOuput. Il Client invia al Server il comando 2 e aspetta una risposta da parte del Server. Questo comando 
		 * corrisponde (sul Server) al salvataggio su file dell'attivit� di scoperta per letture successive. In caso di risposta diversa 
		 * da "OK", viene visualizzato un messaggio di errore in una JOptionPane e termina l'esecuzione del metodo<br>
		 * Viene inviato al Server il comando 4 e il tipo di azione che si sta effettuando (ovvero un'attivit� di scoperta dalla base di dati).
		 * Questo comando corrisponde (sul Server) alla generazione di tutti quei dati per la creazione del grafico. Infatti inviato questo 
		 * comando viene richiamato il metodo chartAction che utilizzer� i dati restituiti dal Server per il grafico che verr� inserito nel
		 * pannello utile a questo scopo. Nel caso in cui anche quest'ultima operazione vada a buon fine verr� mostrato un messaggio
		 * che confermer� il successo dell'attivit� terminando l'esecuzione del metodo.
		 * @throws SocketException Questa eccezione � sollevata quando termina la comunicazione tra il Client e il Server
		 * @throws IOException Questa eccezione � sollevata in caso di errori nelle operazioni di lettura dei risultati ottenuti dal Server
		 * @throws ClassNotFoundException Questa eccezione � sollevata nelle operazioni di lettura dei risultati da file nel caso in cui
		 * gli oggetti passati sullo stream sono istanza di una classe non presente sul Client
		 */
		private void learningFromDBAction() throws SocketException, IOException, ClassNotFoundException {
			int k;
			String tableName = "";
			String result = "";
			tableName = panelDB.tableText.getText();
			if( tableName.equals("") ) {
				JOptionPane.showMessageDialog(this, "Errore! Inserire il nome della tabella");
				return;
			}
			try {
				k = new Integer(panelDB.kText.getText()).intValue();
			}catch(NumberFormatException e) {
				JOptionPane.showMessageDialog(this, e.toString());
				return;
			}
			out.writeObject(0);
			out.writeObject(tableName);
			result = (String)in.readObject();
			if( !result.equals("OK") ) {
				JOptionPane.showMessageDialog(this, result);
				return;
			}else {
				out.writeObject(1);
				out.writeObject(k);
				result = (String)in.readObject();
				if( !result.equals("OK") ) {
					JOptionPane.showMessageDialog(this, result);
					return;
				}else {
					Integer integer = (Integer)in.readObject();
					panelDB.clusterOutput.setText("Numero iterate: " + integer + "\n\n" + (String)in.readObject());
					out.writeObject(2);
					result = (String)in.readObject();
					if( !result.equals("OK") ) {
						JOptionPane.showMessageDialog(this, result);
						return;
					}else {
						out.writeObject(4);
						out.writeObject("db");
						chartAction(this.panelDB);
					}
				}
			}
		}
		
		/**
		 * Questo metodo effettua la lettura di un determinato file sul Server. Questo metodo acquisisce il nome della tabella 
		 * (da panelFile.tableText) e il numero dei cluster (da panelFile.kText). Il Client invia al Server il comando 3, il nome della
		 * tabella e il numero di cluster e aspetta una risposta da parte del Server. In caso di risposta positiva verr� visualizzato, in una 
		 * JOptionPane, un messaggio che confermi il successo dell'attivit�, visualizzando poi il risultato nell'area di testo corrispondente.<br>
		 * Successivamente viene inviato al Server il comando 4 e il tipo di azione che si sta effettuando (ovvero un'attivit� di lettura da file). Questo 
		 * comando corrisponde (sul Server) alla generazione di tutti quei dati per la creazione del grafico. Inviato questo comando viene 
		 * richiamato il metodo chartAction che utilizzer� i dati restituiti dal Server per il grafico che verr� inserito nel pannello utile a
		 * questo scopo.<br>
		 * In caso di risposta diversa da "OK" verr� mostrato un messaggio di errore, terminando l'esecuzione del metodo.
		 * @throws SocketException Questa eccezione � sollevata quando termina la comunicazione tra il Client e il Server
		 * @throws IOException Questa eccezione � sollevata in caso di errori nelle operazioni di lettura dei risultati ottenuti dal Server
		 * @throws ClassNotFoundException Questa eccezione � sollevata nelle operazioni di lettura dei risultati da file nel caso in cui
		 * gli oggetti passati sullo stream sono istanza di una classe non presente sul Client
		 */
		private void learningFromFileAction() throws SocketException, IOException, ClassNotFoundException {
			String tableName = panelFile.tableText.getText();
			String numberOfCluster = panelFile.kText.getText();
			String result = "";
			if( tableName.equals("") ) {
				JOptionPane.showMessageDialog(this, "Errore! Inserire il nome della tabella");
				return;
			}
			if( numberOfCluster.equals("") ) {
				JOptionPane.showMessageDialog(this, "Errore! Inserire un valore per k");
				return;
			}
			out.writeObject(3);
			out.writeObject(tableName);
			out.writeObject(numberOfCluster);
			result = (String)in.readObject();
			if( !result.equals("OK") ) {
				JOptionPane.showMessageDialog(this, result);
				return;
			}else {
				String kmeansString = (String)in.readObject();
				String newStr = kmeansString.replaceAll("\\)- ", "\\)\n");
				out.writeObject(4);
				out.writeObject("file");
				panelFile.clusterOutput.setText(newStr);
				chartAction(this.panelFile);
				return;
			}
		}
	
		/**
		 * Questo metodo consente di creare il grafico.<br>
		 * La creazione del grafico dipende dagli attributi e dal tipo di grafico scelti nell'apposito AttributeDialog. Tale finestra modale verr� 
		 * creata da questo metodo e verr� inizializzata con gli attributi letti dal Server. Una volta effettuata la selezione i valori 
		 * corrispondenti a questa finestra vengo inviati al Server. Come risposta il metodo riceve un dataset il cui riferimento verr� passato 
		 * all'attributo streamDataset. Tale dataset verr� utilizzato per la creazione del grafico che sar� a carico della classe StreamChart. 
		 * Infatti qui viene istanziato un oggetto istanza di tale classe, sul quale vegono richiamati i metodi createChart e createChartPanel, 
		 * che in base al tipo di grafico selezionato, si occuperanno di creare il grafico inserendolo nel pannello passato come parametro, 
		 * in modo da permettere la sua aggiunta al JFrame.
		 * @param panelAction Riferimento ad uno dei due tab. Grazie a questo riferimento � possibile aggiungere il grafico nel pannello per il 
		 * grafico presente in uno dei due tab. 
		 * @throws IOException Questa eccezione � sollevata in caso di errori nelle operazioni di lettura dei risultati ottenuti dal Server
		 * @throws ClassNotFoundException Questa eccezione � sollevata nelle operazioni di lettura dei risultati da file nel caso in cui gli 
		 * oggetti passati sullo stream sono istanza di una classe non presente sul Client 
		 */
		@SuppressWarnings("rawtypes")
		private void chartAction(JPanelCluster panelAction) throws IOException, ClassNotFoundException {
			String result = "";
			String[] attributes = (String[])in.readObject();
			AttributeDialog attrD = new AttributeDialog(findParentFrame(), attributes);
			attrD.setVisible(true);
			ArrayList valoriSelez = attrD.getSelectedValues();
			attrD.dispose();
			out.writeObject(valoriSelez);
			result = (String)in.readObject();
			if(!result.equals("OK")){
				JOptionPane.showMessageDialog(this, result);
				return;
			} else {
				JOptionPane.showMessageDialog(this, "Attivit� effettuata con successo!");
				streamDataset = (Dataset)in.readObject();	
				StreamChart mainChart = new StreamChart();
				panelAction.panelChart.removeAll();
				if(streamDataset instanceof PieDataset){
					int total = (Integer)in.readObject();
					StreamChart.setTotalElem(total);
					panelAction.panelChart.add(mainChart.createChartPanel(mainChart.createChart((PieDataset)streamDataset)));
				} else {
					String labelX = (String)in.readObject();
					String labelY = (String)in.readObject();
					Double maxX = (Double)in.readObject();
					Double maxY = (Double)in.readObject();
					panelAction.panelChart.add(mainChart.createChartPanel(mainChart.createChart((XYDataset)streamDataset, labelX, labelY, maxX, maxY)));
				}
			}
		}
	}
	
	/**
	 * Questo metodo si occupa di creare l'interfaccia grafica andando a inserire al suo interno le due sezioni (tab) per la gestione delle richieste da 
	 * parte dell'utente. Inoltre viene stabilita una connessione al Server e quindi si ottengono i relativi stream di output e di input, 
	 * andando a inizializzare i due attributi out e in.<br>
	 * Nel caso in cui non sia possibile stabilire una connessione con il Server viene mostrato un messaggio di errore. 
	 */
	@SuppressWarnings("resource")
	public void init() {
		setTitle("KmeansClient (Estensione)");
		setSize(950, 690);
		Container cp = getContentPane();
		cp.setLayout(new BoxLayout(cp, BoxLayout.Y_AXIS));
		String ip = this.getParameter("ServerIP");
		int port = 8080;
		try {
			InetAddress addr = InetAddress.getByName(ip);
			Socket socket = new Socket(addr, port);
			TabbedPane tab = new TabbedPane();
			getContentPane().add(tab);
			out = new ObjectOutputStream(socket.getOutputStream());
			in = new ObjectInputStream(socket.getInputStream());
		}catch(IOException e) {
			JOptionPane.showMessageDialog(this, "Impossibile connettersi al Server!");
			cp.add(Box.createRigidArea(new Dimension(20, 20)));
			cp.add(new JLabel("Impossibile connettersi al Server!"));
			cp.add(Box.createRigidArea(new Dimension(20,20)));
			cp.add(new JLabel("Non � possibile eseguire alcuna operazione."));
		}
	}
	
	/**
	 * Questo metodo restituisce l'indirizzo ip della macchina che richiede il servizio dati
	 * @param param Indirizzo ip del Server
	 * @return serverAddress
	 */
	public String getParameter(String param) {
		String serverAddress= "127.0.0.1";
		if(param == "ServerIP") 
			serverAddress = JOptionPane.showInputDialog("Inserire un indirizzo IP valido della macchina su cui �\n" + " in esecuzione il servizio dati sulla porta 8080, altrimenti\n" + " verr� utilizzato quello di default(127.0.0.1): ");
		return serverAddress;
	}
	
	/**
	 * Questo metodo istanzia un oggetto di tipo KMeans e permette di visualizzare l'interfaccia grafica definita dal JFrame
	 * @param args Argomento passato in input
	 */
	public static void main(String args[]) {
		KMeans kmeans = new KMeans();
		kmeans.init();
		kmeans.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		kmeans.setVisible(true);
	}
}

