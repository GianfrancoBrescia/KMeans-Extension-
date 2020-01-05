import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;

import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.LegendItem;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.LegendTitle;
import org.jfree.data.general.PieDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.ui.RectangleEdge;
import org.jfree.util.Rotation;

/**
 * <p>Title: KmeansClient (Estensione)</p>
 * <p>Description: Il progetto <b>KmeansClient (Estensione)</b> realizza un sistema Client in grado di collegarsi al Server tramite l'indirizzo 
 * Ip e il numero di porta su cui il Server &egrave in ascolto. Una volta instaurata la connessione l'utente pu&ograve scegliere se avviare un nuovo 
 * processo di clustering oppure recuperare cluster precedentemente serializzati in un qualche file con visualizzazione del rispettivo 
 * grafico.</p>
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: Dipartimento di Informatica, Universit&agrave degli studi di Bari</p>
 * <p>Class description: StreamChart<br>
 * Definizione della classe StreamChart che si occupa di creare un determinato tipo di grafico basandosi su un insieme di dati passati dal 
 * Server attraverso lo stream di Input/Output. Una volta creato il grafico sar&agrave necessario richiamare la funzione createChartPanel() che 
 * restituisce il pannello (JPanel) contenente il grafico da assegnare ad un contenitore.<br>
 * Il grafico creato pu&ograve contenere una legenda e se necessario (soprattutto nel grafico ad assi cartesiani) verr&agrave creato un pannello a parte
 * per la legenda contenente anche delle funzioni per migliorare la visibilit&agrave del grafico.</p>
 * @author Gianfranco Brescia
 * @version 2.0
 */
class StreamChart {
	/**Pannello che conterr&agrave se necessario la legenda del grafico*/
	private JPanel legendPanel;
	
	/**Numero totale di elementi per il grafico utilizzato all'interno di un grafico a torta, per indicare la totalit&agrave di elementi in base 
	 * ai quali &egrave calcolata ciascuna percentuale assegnata ad ogni parte del grafico.*/
	static private int totalElem;
	
	/**
	 * Questo metodo &egrave il costruttore della classe StreamChart che si occupa di inizializzare il pannello per la legenda, in modo da renderlo 
	 * utilizzabile successivamente e settare il layout per il pannello
	 */
	StreamChart() {
		this.legendPanel = new JPanel();
		this.legendPanel.setLayout(new BoxLayout(this.legendPanel, BoxLayout.PAGE_AXIS));
	}
	
	/**
	 * Questo metodo inizializza l'attributo statico totalElem
	 * @param tot Parametro intero che inizializzer&agrave totalElem
	 */
	static void setTotalElem(int tot) {
		StreamChart.totalElem = tot;
	}
	
	/**
	 * Questo metodo si occupa di creare un grafico a torta detto anche di tipo PIE e si basa sui dati passati come parametro. Esso potr&agrave 
	 * possedere o meno la legenda in base al numero di elementi contenuti nel dataset.<br>
	 * Il dataset di tipo PieDataset sar&agrave creato dal Server e conterr&agrave le diverse sezioni del grafico. Verr&agrave visualizzato un messaggio nel
	 * caso in cui il dataset sia vuoto. Inoltre &egrave disponibile la funzionalit&agrave di rotazione con lo scroll del mouse utile soprattutto nel caso 
	 * in cui i dati presenti nel dataset siano molti.
	 * @param dataset Insieme dei dati su cui si baser&agrave il grafico
	 * @return chart Grafico da mostrare
	 */
	JFreeChart createChart(PieDataset dataset) {
		boolean flaglegend = true;
		if(dataset.getItemCount() > 10)
			flaglegend = false;
		JFreeChart chart = ChartFactory.createPieChart3D("Grafico KMeans - " + StreamChart.totalElem + " Esempi",  dataset, flaglegend, true, false);
		PiePlot3D plot = (PiePlot3D) chart.getPlot();
		plot.setLabelGenerator(new StandardPieSectionLabelGenerator("{0} = {2}"));
		plot.setStartAngle(290);
		plot.setDirection(Rotation.CLOCKWISE);
		plot.setForegroundAlpha(0.7f);
		plot.setNoDataMessage("Nessun dato da visualizzare!");
		return chart;
	}
	
	/**
	 * Questo metodo si occupa di creare un grafico con assi cartesiani detto anche di tipo SCATTER. I dati da inserire sono contenuti 
	 * all'interno del parametro dataset di tipo XYDataset. Il grafico avr&agrave come valore massimo per l'asse X il valore maxX e come valore 
	 * massimo per l'asse Y il valore maxY.<br>
	 * Ciascun asse &egrave rappresentato dalle variabili xLabel e yLabel, che contribuiscono a descrivere meglio il significato dei corrispondenti 
	 * assi. Nel caso in cui il dataset risulter&agrave essere vuoto verr&agrave mostrato un messaggio a video. In questo tipo di grafico se il numero di 
	 * valori presenti all'interno del dataset supera il quantitativo di 10, allora verr&agrave creato un pannello apposito per la legenda. Tale 
	 * legenda verr&agrave creata con l'ausilio della classe LegendPanel. La legenda quindi, se creata, possieder&agrave delle funzioni per migliorare la 
	 * visibilit&agrave del grafico.<br>
	 * &Egrave disponibile uno zoom per il grafico, utilizzabile attraverso lo scroll del mouse, per osservare meglio i dati.
	 * @param dataset Insieme di dati che verranno inseriti all'interno del grafico
	 * @param xLabel Nome da assegnare all'asse X
	 * @param yLabel Nome da assegnare all'asse Y
	 * @param maxX Massimo valore per l'asse X
	 * @param maxY Massimo valore per l'asse Y
	 * @return chart Grafico da mostrare
	 */
	JFreeChart createChart(XYDataset dataset, String xLabel, String yLabel, Double maxX, Double maxY) {
		JFreeChart chart = ChartFactory.createScatterPlot("Grafico KMeans", xLabel, yLabel, dataset, PlotOrientation.VERTICAL, true, true, false);
		XYPlot plot = (XYPlot) chart.getPlot();
		plot.setDomainZeroBaselineVisible(true);
		plot.setRangeZeroBaselineVisible(true);
		plot.setNoDataMessage("Nessun dato da visualizzare!");
		XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) plot.getRenderer();
		renderer.setSeriesOutlinePaint(0, Color.black);
		renderer.setUseOutlinePaint(true);
		LegendTitle legend = chart.getLegend();
		legend.setPosition(RectangleEdge.RIGHT);
		if(renderer.getLegendItems().getItemCount() > 10) {
			legend.setVisible(false);
			setLegend(renderer);
		} else
			legend.setVisible(true);
		NumberAxis domainAxis = (NumberAxis) plot.getDomainAxis();
		domainAxis.setAutoRangeIncludesZero(false);
		domainAxis.setTickMarkInsideLength(2.0f);
		domainAxis.setTickMarkOutsideLength(0.0f);
		domainAxis.setRange(-2.0, maxX + 2);
		NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
		rangeAxis.setTickMarkInsideLength(2.0f);
		rangeAxis.setTickMarkOutsideLength(0.0f);
		rangeAxis.setRange(-2.0, maxY + 2);
		return chart;
	}
	
	/**
	 * Questo metodo si occuper&agrave di creare un pannello apposito per la legenda (chiamato solo quando necessario dal metodo createChart). Il 
	 * pannello per la legenda sar&agrave contenuto all'interno dell'attributo legendPanel. Questo metodo prende in input il parametro renderer che 
	 * contiene la legenda di default creata per un grafico di tipo SCATTER. A partire da questa legenda verr&agrave creata la nuova legenda 
	 * utilizzando le varie etichette e colori nella legenda di default. Il pannello conterr&agrave anche una JComboBox utile per visualizzare solo 
	 * determinati dati sul grafico piuttosto che tutti i dati contemporaneamente. Il pannello creato infine verr&agrave posto subito sotto il 
	 * grafico. Tale metodo, quindi, assicura di visualizzare il grafico anche a fronte di una grossa mole di dati, evitando di essere coperto
	 * interamente dalla legenda.
	 * @param renderer Legenda di default creata per un grafico di tipo SCATTER
	 */
	@SuppressWarnings("rawtypes")
	private void setLegend(final XYLineAndShapeRenderer renderer) {
		Iterator<?> it = renderer.getLegendItems().iterator();
		int i = 0, j = 0;
		final JComboBox<Comparable> comboSer = new JComboBox<Comparable>();
		JLabel title = new JLabel("Legenda");
		JPanel serPanel = new JPanel();
		title.setSize(new Dimension(200, 30));
		title.setFont(new Font(null, Font.BOLD, 20));
		serPanel.setLayout(new FlowLayout());
		serPanel.add(new JLabel("Visualizza solo Cluster: "));
		serPanel.add(comboSer);
		
		comboSer.addActionListener(new ActionListener() {
			int lastSer = 1;
			public void actionPerformed(ActionEvent e) {
				renderer.setBaseSeriesVisible(false);
				Object item = comboSer.getSelectedItem();
				if(item.equals("Tutti")){
					renderer.setBaseSeriesVisible(true);
				}else{
					Integer index = (Integer)item;
					renderer.setSeriesVisible(this.lastSer, false);
					renderer.setSeriesVisible(index, true);
					this.lastSer = index;
				}
			}
		});
		legendPanel.add(serPanel);
		legendPanel.add(title);
		comboSer.addItem("Tutti");
		while(it.hasNext()){
			LegendItem item = (LegendItem)it.next();
			LegendPanel lp = new LegendPanel(item.getLabel(), renderer.getSeriesPaint(i));
			comboSer.addItem(j);
			legendPanel.add(lp);
			j++;
			i++;
		}
	}
	
	/**
	 * Questo metodo si occupa di creare un pannello contenente il grafico da visualizzare. Il grafico sar&agrave quello specificato come parametro.
	 * Questo metodo crea il pannello a partire da ChartPanel e inserisce tale pannello in un JPanel in modo da renderlo utilizzabile 
	 * all'esterno.
	 * @param chart Grafico da inserire all'interno del pannello
	 * @return mainContainer Pannello da utilizzare e contenente il grafico
	 */
	JPanel createChartPanel(JFreeChart chart) {
		JPanel mainContainer = new JPanel();
		JPanel chartContainer = new JPanel();
		chartContainer.setLayout(new BorderLayout());
		chartContainer.setPreferredSize(new Dimension(700, 650));
		ChartPanel chartPanel = new ChartPanel(chart);				
		chartPanel.setMouseWheelEnabled(true);
		chartPanel.setMouseZoomable(true);
		chartContainer.add(chartPanel, BorderLayout.CENTER);
		chartContainer.add(this.legendPanel, BorderLayout.SOUTH);
		mainContainer.add(chartContainer);
		return mainContainer;
	}
}