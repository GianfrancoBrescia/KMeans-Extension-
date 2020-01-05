package database;

/**
 * <p>Title: KmeansServer (Estensione)</p>
 * <p>Description: Il progetto <b>KmeansServer (Estensione)</b> realizza un Server in grado di acquisire le richieste effettuate da parte del 
 * Client e di inviare le rispettive risposte. Inoltre il Server colleziona le classi per l'esecuzione dell'algoritmo kmeans (scoperta di 
 * cluster, (de)serializzazione)).</p>
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: Dipartimento di Informatica, Universit� degli studi di Bari</p>
 * <p>Class description: NoValueException<br>
 * Definizione della classe NoValueException che estende Exception per modellare l'assenza di un valore all'interno di un resulset</p>
 * @author Gianfranco Brescia
 * @version 2.0
 */
@SuppressWarnings("serial")
public class NoValueException extends Exception {
	/**
	 * Questo metodo � il costruttore della classe NoValueException che richiama il costruttore della classe madre (Exception) per creare 
	 * l'oggetto eccezione corrispondente
	 * @param message Messaggio da mostrare in caso di eccezione 
	 */
	NoValueException(String message) {
		super(message);
	}
}
