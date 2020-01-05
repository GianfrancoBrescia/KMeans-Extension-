package analysis;

import org.jfree.data.general.Dataset;

/**
 * <p>Title: KmeansServer (Estensione)</p>
 * <p>Description: Il progetto <b>KmeansServer (Estensione)</b> realizza un Server in grado di acquisire le richieste effettuate da parte del 
 * Client e di inviare le rispettive risposte. Inoltre il Server colleziona le classi per l'esecuzione dell'algoritmo kmeans (scoperta di 
 * cluster, (de)serializzazione)).</p>
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: Dipartimento di Informatica, Università degli studi di Bari</p>
 * <p>Class description: ChartData<br>
 * Definizione della classe ChartData che modella un oggetto responsabile di rappresentare un insieme di dati, che verranno utilizzati dal 
 * Client per la creazione di un grafico. I membri della classe, infatti, vanno a costituire gli elementi base per la rappresentazione di un 
 * grafico, tra cui: l'insieme dei dati appartenenti al grafico, l'etichetta per l'asse x e l'etichetta per l'asse y. Essi vengono inviati al 
 * Client tramite gli stream di I/O.</p>
 * @author Gianfranco Brescia
 * @version 2.0
 */
public class ChartData {
	/**Dataset appartenente al grafico*/
	Dataset dataset;
	
	/**Etichetta per l'asse x*/
	String labelX;
	
	/**Etichetta per l'asse y*/
	String labelY;
	
	/**
	 * Questo metodo è il costruttore della classe ChartData che si occupa di istanziare i membri della classe
	 * @param data Dataset per il grafico
	 * @param labelX Etichetta per l'asse x
	 * @param labelY Etichetta per l'asse y
	 */
	ChartData(Dataset data, String labelX, String labelY) {
		this.dataset = data;
		this.labelX = labelX;
		this.labelY = labelY;
	}
	
	/**
	 * Questo metodo è il costruttore della classe ChartData che si occupa di istanziare esclusivamente il dataset per quei grafici che non 
	 * utilizzano etichette
	 * @param data Dataset utile per l'inizializzazione
	 */
	ChartData(Dataset data) {
		this.dataset = data;
	}
	
	/**
	 * Questo metodo restituisce il dataset per il grafico
	 * @return dataset
	 */
	public Dataset getDataset() {
		return dataset;
	}
	
	/**
	 * Questo metodo restituisce l'etichetta rappresentante l'asse x
	 * @return labelX
	 */
	public String getLabelX() {
		return labelX;
	}
	
	/**
	 * Questo metodo restituisce l'etichetta rappresentante l'asse y 
	 * @return labelY
	 */
	public String getLabelY() {
		return labelY;
	}
}
