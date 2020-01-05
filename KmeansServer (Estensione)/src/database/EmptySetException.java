package database;

/**
 * <p>Title: KmeansServer (Estensione)</p>
 * <p>Description: Il progetto <b>KmeansServer (Estensione)</b> realizza un Server in grado di acquisire le richieste effettuate da parte del 
 * Client e di inviare le rispettive risposte. Inoltre il Server colleziona le classi per l'esecuzione dell'algoritmo kmeans (scoperta di 
 * cluster, (de)serializzazione)).</p>
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: Dipartimento di Informatica, Universit� degli studi di Bari</p>
 * <p>Class description: EmptySetException<br>
 * Definizione della classe EmptySetException che estende Exception per modellare la restituzione di un resultset vuoto</p>
 * @author Gianfranco Brescia
 * @version 2.0
 */
@SuppressWarnings("serial")
public class EmptySetException extends Exception {
	/**
	 * Questo metodo � il costruttore della classe EmptySetException che richiama il costruttore della classe madre (Exception) per creare 
	 * l'oggetto eccezione corrispondente
	 * @param message Messaggio da mostrare in caso di eccezione
	 */
	EmptySetException(String message) {
		super(message);
	}
}
