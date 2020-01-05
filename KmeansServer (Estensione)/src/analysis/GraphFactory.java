package analysis;

import data.Data;

/**
 * <p>Title: KmeansServer (Estensione)</p>
 * <p>Description: Il progetto <b>KmeansServer (Estensione)</b> realizza un Server in grado di acquisire le richieste effettuate da parte del 
 * Client e di inviare le rispettive risposte. Inoltre il Server colleziona le classi per l'esecuzione dell'algoritmo kmeans (scoperta di 
 * cluster, (de)serializzazione)).</p>
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: Dipartimento di Informatica, Università degli studi di Bari</p>
 * <p>Class description: GraphFactory<br>
 * Definizione della classe GraphFactory (classe factory) che si occupa di istanziare la classe correlata</p>
 * @author Gianfranco Brescia
 * @version 2.0
 */
public class GraphFactory {
	/**
	 * Questo metodo si occupa di istanziare una classe che implementa l'intefaccia ChartI.<br>
	 * Nel caso specifico il parametro opt risulta essere utile in quanto, la richiesta di una lettura da file da parte di un Client non 
	 * inizializza l'oggetto data (che risulterà essere null). Per questo motivo è necessario che il metodo setGraph non lo utilizzi.
	 * @param tipo Tipo di grafico da istanziare
	 * @param opt Operazione richiesta (lettura da file o scoperta da database)
	 * @param data Oggetto istanza della classe Data
	 * @return mainChart Oggetto istanza di una classe che implementa l'interfaccia ChartI
	 */
	public static ChartI creaGrafico(String tipo, String opt, Data data){
		ChartI mainChart = null;
		if( tipo.equals("scatter") ) {
			if( opt.equals("file") )
				mainChart = new ScatterGraph();
			else
				mainChart = new ScatterGraph(data);
		} else 
			mainChart = new PieGraph();
		return mainChart;
	}
}