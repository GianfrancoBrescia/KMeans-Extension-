package data;

/**
 * <p>Title: KmeansServer (Estensione)</p>
 * <p>Description: Il progetto <b>KmeansServer (Estensione)</b> realizza un Server in grado di acquisire le richieste effettuate da parte del 
 * Client e di inviare le rispettive risposte. Inoltre il Server colleziona le classi per l'esecuzione dell'algoritmo kmeans (scoperta di 
 * cluster, (de)serializzazione)).</p>
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: Dipartimento di Informatica, Universit� degli studi di Bari</p>
 * <p>Class description: DiscreteItem<br>
 * Definizione della classe DiscreteItem che estende la classe Item e che rappresenta una coppia &ltAttributo discreto-Valore discreto&gt</p>
 * @author Gianfranco Brescia
 * @version 2.0
 */
@SuppressWarnings("serial")
public class DiscreteItem extends Item {
	/**
	 * Questo metodo � il costruttore della classe DiscreteItem che invoca il costruttore della classe madre (Item)
	 * @param attribute Attributo coinvolto nell'item
	 * @param value Valore assegnato all'attributo
	 */
	DiscreteItem(DiscreteAttribute attribute, String value) {
		super(attribute, value);
	}
	
	/**
	 * Questo metodo restituisce 0 se il valore assegnato all'attributo value � uguale ad a, 1 altrimenti
	 * @param a Oggetto da confrontare con value 
	 * @return valore decimale a doppia precisione
	 */
	double distance(Object a) {
		if( getValue().equals(a.toString()) ) return 0.0;
		else return 1.0;
	}
}
