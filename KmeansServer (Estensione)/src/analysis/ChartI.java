package analysis;

import data.Attribute;
import mining.ClusterSet;

/**
 * <p>Title: KmeansServer (Estensione)</p>
 * <p>Description: Il progetto <b>KmeansServer (Estensione)</b> realizza un Server in grado di acquisire le richieste effettuate da parte del 
 * Client e di inviare le rispettive risposte. Inoltre il Server colleziona le classi per l'esecuzione dell'algoritmo kmeans (scoperta di 
 * cluster, (de)serializzazione)).</p>
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: Dipartimento di Informatica, Università degli studi di Bari</p>
 * <p>Class description: ChartI<br>
 * Definizione della classe ChartI che modella l'interfaccia rappresentante un determinato tipo di dato per la creazione di un grafico. Il metodo
 * definito nell'interfaccia si occuperà semplicemente di fornire tali dati relativi ad un determinato tipo di grafico, ma la creazione 
 * effettiva dello stesso avverrà sul Client. Sul Server, quindi, avremo solo la creazione dei dati relativi al grafico. Tali dati saranno 
 * inviati al Client che sarà responsabile di visualizzare il grafico.</p>
 * @author Gianfranco Brescia
 * @version 2.0
 */
public interface ChartI {
	/**
	 * Questo metodo si occupa di creare un oggetto istanza della classe ChartData, che conterrà tutti i dati necessari per la definizione di 
	 * un grafico. Questo metodo, infatti, è responsabile di creare quell'oggetto i cui membri verranno poi inviati al Client.
	 * @param data ClusterSet da considerare per la creazione dei dati relativi al grafico
	 * @param varX Attributo per l'asse X
	 * @param varY Attributo per l'asse Y
	 * @param action Stringa che indica se il Client ha richiesto un'operazione di scoperta dei cluster dalla base di dati, oppure se ha 
	 * richiesto un'operazione di lettura da file
	 * @return Oggetto istanza della classe ChartData che contiene tutti i dati necessari per la creazione di un grafico
	 */
	ChartData setGraph(ClusterSet data, Attribute varX, Attribute varY, String action);
}