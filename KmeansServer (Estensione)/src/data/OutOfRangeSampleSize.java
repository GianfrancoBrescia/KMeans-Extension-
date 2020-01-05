package data;

import java.io.Serializable;

/**
 * <p>Title: KmeansServer (Estensione)</p>
 * <p>Description: Il progetto <b>KmeansServer (Estensione)</b> realizza un Server in grado di acquisire le richieste effettuate da parte del 
 * Client e di inviare le rispettive risposte. Inoltre il Server colleziona le classi per l'esecuzione dell'algoritmo kmeans (scoperta di 
 * cluster, (de)serializzazione)).</p>
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: Dipartimento di Informatica, Universit� degli studi di Bari</p>
 * <p>Class description: OutOfRangeSampleSize<br>
 * Definizione della classe OutOfRangeSampleSize che modella un'eccezione controllata da considerare qualora il numero k di cluster inserito 
 * da tastiera � maggiore rispetto al numero di centroidi generabili dall'insieme di transazioni</p>
 * @author Gianfranco Brescia
 * @version 2.0
 */
@SuppressWarnings("serial")
public class OutOfRangeSampleSize extends Exception implements Serializable {
	/**
	 * Questo metodo � il costruttore della classe OutOfRangeSampleSize che richiama il costruttore della classe madre (Exception) per creare
	 * l'eccezione corrispondente con il messaggio message
	 * @param message Messaggio da visualizzare nel caso in cui l'eccezione sia sollevata
	 */
	public OutOfRangeSampleSize(String message) {
		super(message);
	}
}
