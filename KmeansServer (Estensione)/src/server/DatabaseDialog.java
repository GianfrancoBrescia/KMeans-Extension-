package server;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;

import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * <p>Title: KmeansServer (Estensione)</p>
 * <p>Description: Il progetto <b>KmeansServer (Estensione)</b> realizza un Server in grado di acquisire le richieste effettuate da parte del 
 * Client e di inviare le rispettive risposte. Inoltre il Server colleziona le classi per l'esecuzione dell'algoritmo kmeans (scoperta di 
 * cluster, (de)serializzazione)).</p>
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: Dipartimento di Informatica, Università degli studi di Bari</p>
 * <p>Class description: DatabaseDialog<br>
 * Definizione della classe DatabaseDialog che modella una finestra modale per la lettura dei dati che verranno successivamente utilizzati per 
 * la connessione al database. La finestra prevede tre campi di testo che rappresentano rispettivamente porta, nome utente e password.<br>
 * Questa classe è di supporto per permettere l'utilizzo del database in ambienti con impostazioni differenti.</p>
 * @author Gianfranco Brescia
 * @version 2.0
 */
@SuppressWarnings("serial")
public class DatabaseDialog extends JDialog {
	
	/**Stringa rappresentante la porta su cui è attivo il servizio del DBMS*/
	private String port;
	
	/**Casella di testo che conterrà il nome utente*/
	private JTextField user;
	
	/**Casella di testo che conterrà la password per accedere al database*/
	private JTextField pwd;
	
	/**Pannello che conterrà ogni singola coppia label-textfield a cui verrà associato un FlowLayout*/
	private JPanel listPanel;
	
	/**ArrayList che conterrà i valori inseriti*/
	private ArrayList<String> values;
	
	/**
	 * Questo metodo è il costruttore della classe DatabaseDialog che si occupa di inizializzare la finestra in modalità modale, di assegnarla al 
	 * frame specificato e di fornire per questa finestra un titolo. La finestra viene resa non ridimensionabile, viene assegnata inoltre una 
	 * dimensione e viene inserito un bottone. A tale bottone corrisponderà un'azione che effettuerà il salvataggio dei dati e la chiusura della 
	 * finestra.<br>
	 * La distruzione delle risorse associate alla finestra avviene esternamente visto che la chiusura comporta semplicemente il nascondere 
	 * della finestra.
	 * @param frame Frame a cui associare la finestra
	 * @param name Titolo da associare alla finestra
	 */
	public DatabaseDialog(Frame frame, String name) {
		super(frame, name, true);
		JButton btnSend = new JButton("OK");
		super.setLocationRelativeTo(frame);
		super.setLocation(this.getX() - 150, this.getY() - 125);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		this.setSize(new Dimension(300, 250));
		this.setResizable(false);
		setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));
		port = "3306";
		user = new JTextField(10);
		pwd = new JTextField(10);
		
		//Username
		listPanel = new JPanel(new FlowLayout());
		listPanel.add(new JLabel("Username:"));
		listPanel.add(user);
		add(listPanel);

		//Password
		listPanel = new JPanel(new FlowLayout());
		listPanel.add(new JLabel("Password:"));
		listPanel.add(pwd);
		add(listPanel);

		btnSend.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				if( port.equals("") || user.getText().equals("") || pwd.getText().equals("") )
					showMessage();
				else {
					values = new ArrayList<String>();
					values.add(port);	
					values.add(user.getText());
					values.add(pwd.getText());
					chiudi();
				}
			}
		});
		listPanel = new JPanel(new FlowLayout());
		listPanel.add(btnSend);
		add(listPanel);
	}
	
	/**
	 * Questo metodo visualizza un messaggio di errore nel caso di un mancato inserimento di dati all'interno di almeno una textfield presente 
	 * nella finestra
	 */
	private void showMessage(){
		JOptionPane.showMessageDialog(this, "Errore! - Inserire tutti i valori!");
	}
	
	/**
	 * Questo metodo si occupa di nascondere la finestra senza distruggere le risorse ad essa assegnate
	 */
	private void chiudi(){
		this.setVisible(false);
	}
	
	/**
	 * Questo metodo restituisce l'insieme dei valori inseriti all'interno della finestra
	 * @return values 
	 */
	ArrayList<String> getSelectedValues(){
		return values;
	}
	
	/**
	 * Questo metodo si occupa di ripulire le textfield presenti all'interno della finestra
	 */
	void resetFields(){
		this.user.setText("");
		this.pwd.setText("");
	}
}