package analysis;

import java.util.ArrayList;
import java.util.List;

import mining.Cluster;

/**
 * <p>Title: KmeansServer (Estensione)</p>
 * <p>Description: Il progetto <b>KmeansServer (Estensione)</b> realizza un Server in grado di acquisire le richieste effettuate da parte del 
 * Client e di inviare le rispettive risposte. Inoltre il Server colleziona le classi per l'esecuzione dell'algoritmo kmeans (scoperta di 
 * cluster, (de)serializzazione)).</p>
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: Dipartimento di Informatica, Università degli studi di Bari</p>
 * <p>Class description: ClusterData<br>
 * Definizione della classe ClusterData che si occupa di suddividere tutti i valori per i due assi in gruppi. Ogni gruppo di valori coincide ai
 * valori per gli assi del grafico, per ciascun Cluster.<br>
 * Questa classe è di supporto alla classe ScatterGraph per la creazione dei dati relativi al grafico.</p>
 * @author Gianfranco Brescia
 * @version 2.0
 */
class ClusterData {
	/**Valori da raggruppare*/
	List<Number> values;
	
	/**Lista dei valori relativi ad un solo Cluster*/
	List<Number> clustIndexes;
	
	/**Cluster da considerare per il raggruppamento*/
	private Cluster mainClust;

	/**
	 * Questo metodo è il costruttore della classe ClusterData che si occupa di inizializzare i membri della classe e di richiamare il metodo
	 * createClustList() che si occuperà opportunamente di raggruppare i dati da restituire
	 * @param values Lista dei valori da raggruppare
	 * @param mainClust Cluster da considerare per il raggruppamento
	 */
	ClusterData(List<Number> values, Cluster mainClust) {
		this.values = values;
		clustIndexes = new ArrayList<Number>();
		this.mainClust = mainClust;
		createClustList();
	}

	/**
	 * Questo metodo si occupa di effettuare il raggruppamento. Principalmente il metodo considera il Cluster (contenente le righe della tabella 
	 * data ad esso appartenenti) e per ogni valore in esso contenuto preleva il corrispondente valore dalla lista completa dei valori da 
	 * raggruppare e lo inserisce nella lista clustIndexes. Infatti la lista dei valori da raggruparre avrà la stessa dimensione della tabella 
	 * data e, ciascun elemento nella lista, corrisponde a una riga della tabella. Quindi leggendo ciascuno dei valori appartenenti ad un Cluster 
	 * si ottiene l'indice da cui effettuare la lettura nella lista values. Il valore così ottenuto viene inserito all'interno della lista 
	 * clustIndexes.<br>
	 * Il grafico, utilizzando questi dati, può rappresentare indistintamente ogni cluster.
	 */
	private void createClustList() {
		int clusterSize = this.mainClust.getClusterSize();
		for(int i = 0; i < clusterSize; i++) {
			int val = mainClust.getClusterValue(i);
			this.clustIndexes.add(values.get(val));
		}		
	}	

	/**
	 * Questo metodo restituisce la dimensione della lista dei valori raggruppati
	 * @return Dimensione della lista
	 */
	int getListSize() {
		return clustIndexes.size();
	}

	/**
	 * Questo metodo restituisce il valore in posizione ind della lista dei valori raggruppati
	 * @param ind Indice del valore da leggere
	 * @return Valore letto
	 */
	Number getCValue(int ind) {
		return clustIndexes.get(ind);
	}
}