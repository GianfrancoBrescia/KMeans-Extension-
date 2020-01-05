package analysis;

import mining.ClusterSet;
import data.Attribute;
import org.jfree.data.general.DefaultPieDataset;

/**
 * <p>Title: KmeansServer (Estensione)</p>
 * <p>Description: Il progetto <b>KmeansServer (Estensione)</b> realizza un Server in grado di acquisire le richieste effettuate da parte del 
 * Client e di inviare le rispettive risposte. Inoltre il Server colleziona le classi per l'esecuzione dell'algoritmo kmeans (scoperta di 
 * cluster, (de)serializzazione)).</p>
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: Dipartimento di Informatica, Universit&agrave degli studi di Bari</p>
 * <p>Class description: PieGraph<br>
 * Definizione della classe PieGraph che si occupa di modellare i dati relativi ad un grafico a torta. Il metodo setGraph, definito 
 * nell'interfaccia ChartI e ridefinito in questa classe, si occupa di istanziare l'oggetto della classe ChartData, relativo ai dati
 * utili per un grafico a torta.</p>
 * @author Gianfranco Brescia
 * @version 2.0
 */
public class PieGraph implements ChartI {
	/**
	 * Questo metodo si occupa di calcolare la percentuale
	 * @param val Valore di cui calcolare la percentuale
	 * @param tot Totalit&agrave dei valori
	 * @return Percentuale calcolata di val rispetto a tot
	 */
	private double getPerc(double val, double tot) {
		return (100*val)/tot;
	}

	/**
	 * Questo metodo si occupa di ottenere la totalit&agrave dei valori presenti nel ClusterSet. Questo metodo esegue una sommatoria di tutti i dati 
	 * presenti in ciascun Cluster.
	 * @param data ClusterSet di cui effettuare il calcolo
	 * @return tot Totalit&agrave dei valori presenti nel ClusterSet
	 */
	private double getTot(ClusterSet data) {
		double tot = 0;
		for(int i=0; i<data.getLength(); i++) {
			tot+= data.get(i).getClusterSize();
		}
		return tot;
	}

	/**
	 * Questo metodo si occupa di creare l'oggetto ChartData. L'obiettivo principale della creazione di questo oggetto &egrave dovuto al fatto che le 
	 * sue componenti saranno inviate poi al Client per la corretta visualizzazione del grafico a torta.<br>
	 * Questo metodo si occupa principalmente di: <br>
	 * - calcolare la percentuale che ciascun cluster, contenuto all'interno del ClusterSet data, rappresenta (attraverso il metodo getPerc)<br>
	 * - aggiungere tale valore al dataset che verr&agrave utilizzato dal Client per la visualizzazione del grafico. Il dataset creato viene utilizzato
	 *   poi dall'oggetto mainData, istanza della classe ChartData, che verr&agrave restituito dal metodo.
	 *   @param data ClusterSet da considerare per la creazione dei dati relativi al grafico
	 *   @param varX Attributo per l'asse X
	 *   @param varY Attributo per l'asse Y
	 *   @param action Stringa che indica se il Client ha richiesto un'operazione di scoperta dei cluster dalla base di dati, oppure se ha 
	 *   richiesto un'operazione di lettura da file
	 *   @return mainData Oggetto istanza della classe ChartData che contiene tutti i dati necessari per la creazione di un grafico
	 */
	public ChartData setGraph(ClusterSet data, Attribute varX, Attribute varY, String action) {
		ChartData mainData = null;
		DefaultPieDataset dataset = new DefaultPieDataset();
		double tot = this.getTot(data);
		for(int i=0; i<data.getLength(); i++){
			double clPerc = getPerc(data.get(i).getClusterSize(), tot);
			dataset.setValue("Cluster " + i, clPerc);
		}
		mainData = new ChartData(dataset);
		return mainData;
	}
}