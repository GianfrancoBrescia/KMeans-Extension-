package data;

/**
 * <p>Title: KmeansServer (Estensione)</p>
 * <p>Description: Il progetto <b>KmeansServer (Estensione)</b> realizza un Server in grado di acquisire le richieste effettuate da parte del 
 * Client e di inviare le rispettive risposte. Inoltre il Server colleziona le classi per l'esecuzione dell'algoritmo kmeans (scoperta di 
 * cluster, (de)serializzazione)).</p>
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: Dipartimento di Informatica, Università degli studi di Bari</p>
 * <p>Class description: ContinuousAttribute<br>
 * Definizione della classe concreta ContinuousAttribute che estende la classe Attribute e che modella un attributo continuo (numerico). 
 * Questa classe include i metodi per la "normalizzazione" del dominio dell'attributo nell'intervallo [0,1] al fine da rendere confrontabili 
 * attributi aventi domini diversi</p>
 * @author Gianfranco Brescia
 * @version 2.0
 */
@SuppressWarnings("serial")
public class ContinuousAttribute extends Attribute {
	/**Massimo estremo dell'intervallo di valori (dominio) che l'attributo può realmente assumere*/
	private double max;
	
	/**Minimo estremo dell'intervallo di valori (dominio) che l'attributo può realmente assumere*/
	private double min;
	
	/**
	 * Questo metodo è il costruttore della classe ContinuousAttribute che invoca il costruttore della classe madre (Attribute) e inizializza i membri min e max
	 * @param name Nome dell'attributo
	 * @param index Identificativo numerico dell'attributo (primo, secondo, ... attributo della tupla)
	 * @param min Valore minimo dell'attributo
	 * @param max Valore massimo dell'attributo
	 */
	public ContinuousAttribute(String name, int index, double min, double max) {
		super(name, index);
		this.min = min;
		this.max = max;
	}
	
	/**
	 * Questo metodo restituisce il valore minimo dell'intervallo
	 * @return min
	 */
	public double getMin(){
		return min;
	}
	
	/**
	 * Questo metodo restituisce il valore massimo dell'intervallo
	 * @return max
	 */
	public double getMax(){
		return max;
	}
	
	/**
	 * Questo metodo calcola e restituisce il valore normalizzato di v. La normalizzazione ha come codominio l'intervallo [0,1].
	 * @param v Valore dell'attributo da normalizzare 
	 * @return valore normalizzato
	 */
	double getScaledValue(double v) {
		return (v-getMin())/(getMax()-getMin());
	}
}