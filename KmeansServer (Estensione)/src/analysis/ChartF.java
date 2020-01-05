package analysis;

import java.util.ArrayList;
import java.util.List;

import data.Data;
import data.Attribute;
import data.Tuple;
import data.DiscreteAttribute;

import mining.ClusterSet;

/**
 * <p>Title: KmeansServer (Estensione)</p>
 * <p>Description: Il progetto <b>KmeansServer (Estensione)</b> realizza un Server in grado di acquisire le richieste effettuate da parte del 
 * Client e di inviare le rispettive risposte. Inoltre il Server colleziona le classi per l'esecuzione dell'algoritmo kmeans (scoperta di 
 * cluster, (de)serializzazione)).</p>
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: Dipartimento di Informatica, Università degli studi di Bari</p>
 * <p>Class description: ChartF<br>
 * Definizione della classe ChartF che si occupa di fornire un insieme di funzionalità utili per la creazione dei grafici. Essa infatti contiene 
 * un insieme di metodi statici che verranno richiamati, quando necessario, dalle classi PieGraph e ScatterGraph per ottenere tutti i dati
 * necessari, da restituire al Client che andrà poi a visualizzare il grafico correttamente.</p>
 * @author Gianfranco Brescia
 * @version 2.0
 */
class ChartF {
	/**
	 * Questo metodo analizza la tupla passata come parametro considerando un suo valore all'indice dato dall'attributo specificato come 
	 * parametro. Tale valore viene poi confrontato con quelli presenti nell'attributo restituendo un intero.<br>
	 * Questo metodo è utilizzato nel caso di un attributo discreto.
	 * @param dataTup Tupla da analizzare
	 * @param varAxe Attributo contenente i valori da confrontare con il valore presente nella posizione data dall'attributo varAxe
	 * @return grData
	 */
	private static int checkVal(Tuple dataTup, Attribute varAxe) {
		Object val = dataTup.get(varAxe.getIndex()).getValue();
		int grData = 0;
		for(String item : (DiscreteAttribute)varAxe) {
			if(item.equals(val)) {
				break;
			}else {
				grData++;
			}
		}
		return grData;
	}

	/**
	 * Questo metodo si occupa di creare la lista contenente i valori per un asse in base ai parametri passati (nel caso di un attributo 
	 * discreto). Questo metodo principalmente considera ciascuna tupla (ricavandole utilizzando il metodo getItemSet), passando ognuna di 
	 * queste al metodo checkVal che si occuperà di restituire il valore corretto da inserire all'interno della lista.
	 * @param data Tabella da considerare
	 * @param varAxe Attributo da considerare per l'ottenimento dei valori
	 * @return axeValues Lista contenente i valori per l'asse relativi all'attributo specificato
	 */
	static List<Integer> getAxisDisValues(Data data, Attribute varAxe) {
		int len = data.getNumberOfExamples();
		List<Integer> axeValues = new ArrayList<Integer>();
		for(int i = 0; i < len; i++){
			Tuple dataTup = data.getItemSet(i);
			axeValues.add(checkVal(dataTup, varAxe));
		}
		return axeValues;
	}

	/**
	 * Questo metodo si occupa di creare la lista contenente i valori per un asse in base ai parametri passati (nel caso di un attributo 
	 * discreto). Questo metodo principalmente considera ciascun centroide appartenente ai Cluster del ClusterSet specificato (ricavandoli 
	 * utilizzando il metodo getCentroid())<br>
	 * Essendo il centroide una tupla, tale tupla viene passata come argomento di input al metodo checkVal che si occuperà di restituire il
	 * valore corretto da inserire all'interno della lista.
	 * @param data ClusterSet da considerare
	 * @param varAxe Attributo da considerare per l'ottenimento dei valori
	 * @return axeValues Lista contenente i valori per l'asse relativi all'attributo specificato
	 */
	static List<Integer> getCentDisValues(ClusterSet data, Attribute varAxe) {
		List<Integer> axeValues = new ArrayList<Integer>();
		int len = data.getLength();
		for(int i = 0; i < len; i++) {
			Tuple dataTup = data.get(i).getCentroid();
			axeValues.add(checkVal(dataTup, varAxe));
		}
		return axeValues;
	}

	/**
	 * Questo metodo si occupa di creare la lista contenente i valori per un asse in base ai parametri passati (nel caso di un attributo 
	 * continuo). Questo metodo principalmente considera ciascuna tupla (ricavandole attraverso il metodo getItemSet) e per ciascuna di queste 
	 * considera l'attributo specificato ricavandone il valore che questo assume nella tabella (utilizzando il metodo getValue). I valori 
	 * ottenuti vengono così aggiunti alla lista da restituire.
	 * @param data ClusterSet da considerare
	 * @param varAxe Attributo da considerare per l'ottenimento dei valori
	 * @return contValues Lista contenente i valori per l'asse relativi all'attributo specificato
	 */
	static List<Double> getAxisCntValues(Data data, Attribute varAxe) {
		List<Double> contValues = new ArrayList<Double>();
		int length = data.getNumberOfExamples();
		for(int i=0; i<length; i++) {
			Tuple dataTup = data.getItemSet(i);
			Object val = dataTup.get(varAxe.getIndex()).getValue();
			contValues.add((Double)val);
		}
		return contValues;
	}

	/**
	 * Questo metodo si occupa di creare la lista contenente i valori per un asse in base ai parametri passati (nel caso di un attributo 
	 * continuo). Questo metodo principalmente considera ciascun centroide appartenente ai Cluster del ClusterSet specificato (ricavandoli 
	 * utilizzando il metodo getCentroid()).<br>
	 * Essendo il centroide una tupla, per ciascuna di queste viene considerato l'attributo specificato ricavandone il valore che questo assume
	 * (utilizzando il metodo getValue()). Tale valore viene infine aggiunto alla lista da restituire.
	 * @param data ClusterSet da considerare
	 * @param varAxe Attributo da considerare per l'ottenimento dei valori
	 * @return contValues Lista contenente i valori per l'asse relativi all'attributo specificato
	 */
	static List<Double> getCentContValues(ClusterSet data, Attribute varAxe) {
		List<Double> contValues = new ArrayList<Double>();
		int len = data.getLength();
		for(int i = 0; i < len; i++) {
			Tuple dataTup = data.get(i).getCentroid();
			Object val = dataTup.get(varAxe.getIndex()).getValue();
			contValues.add((Double)val);
		}
		return contValues;
	}

	/**
	 * Questo metodo restituisce una stringa utile per la creazione dell'etichetta per un asse
	 * @param attr Attributo da considerare
	 * @return xValues Stringa rappresentante un etichetta
	 */
	static String getAxeLabels(Attribute attr) {
		int i = 0;
		String xValues = "(" + attr.getName() + ") ";
		for(String item : (DiscreteAttribute)attr) {
			xValues = xValues + i + ": " + item + " ";
			i++;
		}
		return xValues;
	}
}