import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;

import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * <p>Title: KmeansClient (Estensione)</p>
 * <p>Description: Il progetto <b>KmeansClient (Estensione)</b> realizza un sistema Client in grado di collegarsi al Server tramite l'indirizzo 
 * Ip e il numero di porta su cui il Server &egrave in ascolto. Una volta instaurata la connessione l'utente pu&ograve scegliere se avviare un nuovo processo
 * di clustering oppure recuperare cluster precedentemente serializzati in un qualche file con visualizzazione del rispettivo grafico.</p>
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: Dipartimento di Informatica, Universit&agrave degli studi di Bari</p>
 * <p>Class description: LegendPanel<br>
 * Definizione della classe LegendPanel che modella ciascun elemento della legenda per un determinato grafico. Ogni elemento &egrave un JPanel ed &egrave
 * costituito da una figura (quadrato) e da un'etichetta entrambi allineati secondo un FlowLayout.</p>
 * @author Gianfranco Brescia
 * @version 2.0
 */
@SuppressWarnings("serial")
class LegendPanel extends JPanel {
	/**Nome che ciascun elemento avr&agrave nella legenda*/
	private String label;
	
	/**Colore assegnato ad ogni forma disegnata nel pannello*/
	private Paint color;
	
	/**
	 * Questo metodo &egrave il costruttore della classe LegendPanel che si occupa di assegnare al pannello un layout di tipo FlowLayout e di 
	 * inizializzare gli attributi label e color
	 * @param label Etichetta che ciascun elemento della legenda avr&agrave
	 * @param color Colore che verr&agrave assegnato a ciascuna forma.
	 */
	LegendPanel(String label, Paint color) {
		setLayout(new FlowLayout());
		this.label = label;
		this.color = color;
		add(new JLabel(this.label));
	}
	
	/**
	 * Questo metodo sovrascrive il metodo della classe JComponent e viene richiamato non appena il pannello per questo elemento della legenda 
	 * risulter&agrave essere visibile. Questo metodo si occupa di creare un quadrato da assegnare a ciascun elemento della legenda, fornendo per 
	 * ciascuno di essi un colore.
	 * @param g Componente grafica
	 */
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setPaint(this.color);
		g2.fillRect(0, 0, 20, 20);
	}
}