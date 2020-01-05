import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.WindowConstants;

/**
 * <p>Title: KmeansClient (Estensione)</p>
 * <p>Description: Il progetto <b>KmeansClient (Estensione)</b> realizza un sistema Client in grado di collegarsi al Server tramite l'indirizzo 
 * Ip e il numero di porta su cui il Server &egrave in ascolto. Una volta instaurata la connessione l'utente pu&ograve scegliere se avviare un nuovo 
 * processo di clustering oppure recuperare cluster precedentemente serializzati in un qualche file con visualizzazione del rispettivo 
 * grafico.</p>
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: Dipartimento di Informatica, Universit&agrave degli studi di Bari</p>
 * <p>Class description: AttributeDialog<br>
 * Definizione della classe AttributeDialog che modella una finestra modale con lo scopo di acquisire gli attributi da utilizzare e il tipo di 
 * grafico in cui tali attributi verranno utilizzati. La finestra modale sar&agrave composta da tre JComboBox. Di queste, due servono per la lettura
 * degli attributi e la terza serve per la scelta del grafico.</p>
 * @author Gianfranco Brescia
 * @version 2.0
 */
@SuppressWarnings({ "rawtypes", "serial" })
class AttributeDialog extends JDialog {
	/**JComboBox per la selezione dell'attributo relativo all'asse X*/
	private JComboBox xCombo;
	
	/**JComboBox per la selezione dell'attributo relativo all'asse Y*/
	private JComboBox yCombo;
	
	/**JComboBox per la selezione del tipo di grafico*/
	private JComboBox chartCombo;
	
	/**Array che conterr&agrave i valori delle selezioni effettuate nella finestra*/
	private ArrayList<Object> values = new ArrayList<Object>();
	
	/**
	 * Questo metodo &egrave il costruttore della classe AttributeDialog che inizializza la finestra di dialogo modale che verr&agrave aggiunta al Frame 
	 * specificato come parametro. L'argomento attributeList conterr&agrave gli attributi passati dal Server utili per specificare quali di questi 
	 * verranno utilizzati nel grafico. L'array verr&agrave letto per utilizzare i suoi elementi all'interno delle due JComboBox per gli assi
	 * cartesiani.<br>
	 * Il click sul bottone OK della finestra comporter&agrave il salvataggio della selezione effettuata (riguardante le JComboBox) e la chiusura della 
	 * finestra stessa.
	 * @param frame Frame di appartenenza per l'AttributeDialog
	 * @param attributeList Array contenente gli attributi che verranno inseriti nelle JComboBox all'atto dell'inizializzazione
	 */
	@SuppressWarnings("unchecked")
	AttributeDialog(Frame frame, String[] attributeList) {
		super(frame, "Scegli gli attributi e il grafico", true);
		JPanel main = new JPanel();
		JButton btnSend = new JButton("OK");
		super.setLocationRelativeTo(frame);
		super.setLocation(100, 100);
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE); 
		setSize(new Dimension(300, 250));
		setResizable(false);
		main.setLayout(new FlowLayout());
		xCombo = new JComboBox(attributeList);
		yCombo = new JComboBox(attributeList);
		chartCombo = new JComboBox();
		chartCombo.addItem("scatter");
		chartCombo.addItem("pie");
		//topPanel
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new FlowLayout());
		topPanel.add(new JLabel("Scegli Attributo Asse x: "));
		topPanel.add(xCombo);
		//centerPanel
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new FlowLayout());
		centerPanel.add(new JLabel("Scegli Attributo Asse y: "));
		centerPanel.add(yCombo);
		//downPanel
		JPanel downPanel = new JPanel();
		downPanel.setLayout(new FlowLayout());
		downPanel.add(new JLabel("Scegli tipo di Grafico: "));
		downPanel.add(chartCombo);
		
		JPanel adv = new JPanel();
		adv.setLayout(new BorderLayout());
		adv.add(new JLabel("La scelta degli attributi non è influente"), BorderLayout.NORTH);
		adv.add(new JLabel("nel caso del grafico a torta (pie)"), BorderLayout.CENTER);
		adv.add(new JSeparator(), BorderLayout.SOUTH);
		
		main.add(topPanel);
		main.add(centerPanel);
		main.add(adv);
		main.add(downPanel);
		
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				values.add(xCombo.getSelectedIndex());
				values.add(yCombo.getSelectedIndex());	
				values.add(chartCombo.getSelectedItem());
				chiudi();
			}
		});
		main.add(btnSend);
		add(main);
	}
	
	/**
	 * Questo metodo &egrave utilizzato per chiudere la finestra una volta che &egrave stato cliccato il bottone presente all'interno della stessa. Questo 
	 * metodo si occupa semplicemente di nascondere la finestra. Le risorse associate alla finestra non vengono distrutte.
	 */
	private void chiudi(){
		setVisible(false);
	}
	
	/**
	 * Questo metodo restituisce l'insieme dei valori selezionati dall'utente nella finestra
	 * @return values 
	 */
	ArrayList<Object> getSelectedValues(){
		return values;
	}
}