package analysis;

import java.util.List;

import mining.ClusterSet;

import data.Data;
import data.Attribute;
import data.DiscreteAttribute;
import data.ContinuousAttribute;

import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 * <p>Title: KmeansServer (Estensione)</p>
 * <p>Description: Il progetto <b>KmeansServer (Estensione)</b> realizza un Server in grado di acquisire le richieste effettuate da parte del 
 * Client e di inviare le rispettive risposte. Inoltre il Server colleziona le classi per l'esecuzione dell'algoritmo kmeans (scoperta di 
 * cluster, (de)serializzazione)).</p>
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: Dipartimento di Informatica, Università degli studi di Bari</p>
 * <p>Class description: ScatterGraph<br>
 * Definizione della classe ScatterGraph che si occupa di modellare i dati relativi ad un grafico ad assi cartesiani. Il metodo setGraph,
 * definito nell'interfaccia ChartI e ridefinito in questa classe, si occupa di istanziare l'oggetto della classe ChartData, relativo ai dati
 * utili per un grafico ad assi che si basa sugli attributi scelti dal Client e letti dal Server grazie al sistema di stream Input/Output.</p>
 * @author Gianfranco Brescia
 * @version 2.0
 */
@SuppressWarnings("rawtypes")
public class ScatterGraph implements ChartI {

	/**Oggetto istanza della classe Data*/
	private Data table;
	
	/**Lista completa per i valori relativi all'asse x*/
	private List xValues;
	
	/**Lista completa per i valori relativi all'asse y*/
	private List yValues;
	
	/**Etichetta relativa ad un asse*/
	private String labels;

	/**
	 * Questo metodo è il costruttore della classe ScatterGraph che si occupa di istanziare la classe, inizializzando l'oggetto table, istanza 
	 * della classe Data, grazie al parametro passato. Il costruttore è chiamato solo nel caso in cui il Client decide di effettuare un'attività 
	 * di scoperta dalla base di dati.
	 * @param table Riferimento ad un oggetto istanza della classe Data
	 */
	ScatterGraph(Data table) {
		this.table = table;
		this.labels = "";
	}

	/**
	 * Questo metodo è il costruttore della classe ScatterGraph a zero argomenti che verrà richiamato nel caso in cui il Client decida di 
	 * effettuare una richiesta di lettura da file relativa ad attività precedenti di scoperta
	 */
	ScatterGraph() {
		this.labels = "";
	}

	/**
	 * Questo metodo, di supporto per la classe ScatterGraph, si occupa di creare un oggetto istanza della classe XYSeries. Tale oggetto è 
	 * la componente principale del dataset {@link org.jfree.data.xy.XYSeriesCollection} e descrive un'unica componente del dataset. Questo
	 * metodo principalmente considera i parametri xValues e yValues, che rappresentano i dati relativi ad un asse per un solo Cluster, e 
	 * aggiunge ciascuno di questi ad una {@link org.jfree.data.xy.XYSeries} assegnandogli un nome. Tale nome verrà poi visualizzato nella 
	 * legenda del grafico lato Client.
	 * @param xValues Valori relativi all'asse x per un Cluster
	 * @param yValues Valori relativi all'asse y per un Cluster
	 * @param name Nome da assegnare alla {@link org.jfree.data.xy.XYSeries}
	 * @return clustSer La {@link org.jfree.data.xy.XYSeries} contenente i valori relativi al Cluster
	 */
	private XYSeries createSeries(ClusterData xValues, ClusterData yValues, String name) {
		XYSeries clustSer = new XYSeries(name);
		for(int i= 0; i<xValues.getListSize(); i++)
			clustSer.add(xValues.getCValue(i), yValues.getCValue(i));
		return clustSer;
	}
	
	/**
	 * Questo metodo si occupa di richiamare il metodo appropriato in base al tipo di attributo passato come parametro.<br>
	 * Principalmente questo metodo si occupa di creare l'etichetta per il grafico richiamando il metodo getAxeLabels, definito nella classe
	 * ChartF, e di passare il risultato al membro labels. In seguito viene richiamato il metodo getCentList(DiscreteAttribute, ClusterSet) 
	 * oppure il metodo getCentList(ContinuousAttribute, ClusterSet) in base al tipo di attributo specificato come parametro.<br>
	 * La presenza di questo metodo è dovuta al fatto che se il Client decidesse di effettuare un'operazione di lettura da file a quel punto 
	 * la tabella table non sarebbe disponibile, quindi gli unici dati per creare la lista dei valori per l'asse si troverebbero nel ClusterSet 
	 * specificato come parametro. Tale operazione sarà effettuata dai metodi che vengono richiamati all'interno di questo metodo.
	 * @param attr Attributo per cui effettuare le operazioni
	 * @param data ClusterSet da analizzare
	 * @return retList Lista contenente i valori per l'asse in base all'attributo e al ClusterSet specificati
	 */
	private List getCentList(Attribute attr, ClusterSet data) {
		List retList;
		if(attr instanceof DiscreteAttribute){
			this.labels = ChartF.getAxeLabels(attr);
			retList = getCentList((DiscreteAttribute)attr, data);
		} else{
			this.labels = attr.getName();
			retList = getCentList((ContinuousAttribute)attr, data);
		}
		return retList;	
	}
	
	/**
	 * Questo metodo si occupa semplicemente di richiamare il metodo getCentDisValues, definito nella classe ChartF, per ottenere la lista 
	 * dei valori per l'asse relativa all'attributo specificato
	 * @param attr Attributo discreto di cui effettuare l'analisi
	 * @param data ClusterSet da considerare per l'analisi
	 * @return Lista dei valori per l'asse relativa ai parametri specificati
	 */
	private List<Integer> getCentList(DiscreteAttribute attr, ClusterSet data) {
		return ChartF.getCentDisValues(data, attr);
	}
	
	/**
	 * Questo metodo si occupa semplicemente di richiamare il metodo getCentContValues, definito nella classe ChartF, per ottenere la lista 
	 * dei valori per l'asse relativa all'attributo specificato
	 * @param attr Attributo continuo di cui effettuare l'analisi
	 * @param data ClusterSet da considerare per l'analisi
	 * @return Lista dei valori per l'asse relativa ai parametri specificati
	 */
	private List<Double> getCentList(ContinuousAttribute attr, ClusterSet data) {
		return ChartF.getCentContValues(data, attr);
	}
	
	/**
	 * Questo metodo si occupa di richiamare il metodo appropriato in base al tipo di attributo passato come parametro.<br>
	 * Principalmente questo metodo si occupa di creare l'etichetta per il grafico richiamando il metodo getAxeLabels, definito nella classe
	 * ChartF, e di passare il risultato al membro labels. In seguito viene richiamato il metodo getDisList(DiscreteAttribute) oppure il metodo
	 * getContList(ContinuousAttribute) in base al tipo di attributo specificato come parametro.
	 * @param attr Attributo per cui effettuare le operazioni
	 * @return retList Lista contenente i valori per l'asse in base all'attributo specificato
	 */
	private List getList(Attribute attr){
		List retList;
		if(attr instanceof DiscreteAttribute){
			this.labels = ChartF.getAxeLabels(attr);
			retList = getDisList((DiscreteAttribute)attr);
		} else {
			this.labels = attr.getName();
			retList = getContList((ContinuousAttribute)attr);
		}
		return retList;
	}
	
	/**
	 * Questo metodo si occupa semplicemente di richiamare il metodo getAxisDisValues, definito nella classe ChartF, per ottenere la lista 
	 * dei valori per l'asse relativa all'attributo specificato
	 * @param attr Attributo discreto di cui effettuare l'analisi
	 * @return Lista dei valori per l'asse
	 */
	private List<Integer> getDisList(DiscreteAttribute attr){
		return ChartF.getAxisDisValues(this.table, attr);
	}
	
	/**
	 * Questo metodo si occupa semplicemente di richiamare il metodo getAxisCntValues, definito nella classe ChartF, per ottenere la lista 
	 * dei valori per l'asse relativa all'attributo specificato
	 * @param attr Attributo continuo di cui effettuare l'analisi
	 * @return Lista dei valori per l'asse
	 */
	private List<Double> getContList(ContinuousAttribute attr){
		return ChartF.getAxisCntValues(this.table, attr);
	}

	/**
	 * Questo metodo si occupa di creare l'insieme di dati (e quindi di restituire l'oggetto istanza della classe ChartData) relativi ad un 
	 * grafico ad assi cartesiani.<br>
	 * Questo metodo verifica innanzitutto che operazione viene richiesta, quindi, se il parametro action riguarda un'operazione di lettura da 
	 * file oppure di scoperta dei cluster da database.<br>
	 * Nel primo caso, non essendo disponibile la tabella table, è necessario richiamare il metodo getCentList e successivamente creare una 
	 * {@link org.jfree.data.xy.XYSeries} per ogni elemento del ClusterSet contenente i valori per l'asse x e per l'asse y, presenti nelle 
	 * liste ottenute dalle chiamate al metodo getCentList. Infine viene creato l'oggetto istanza della classe ChartData che verrà restituito.<br>
	 * Nel secondo caso, invece, essendo disponibile la tabella table, verrà richiamato il metodo getList ottenendo così prima la lista completa 
	 * dell'asse x e poi la lista completa dell'asse y creando così le etichette. La lista creata, viene utilizzata da un oggetto istanza della 
	 * classe ClusterData che si occuperà di organizzarla in base al cluster. Il cluster passato a tale oggetto sarà letto in base al parametro 
	 * data passato a questo metodo. Infine viene creata una {@link org.jfree.data.xy.XYSeries}, utilizzando il metodo createSeries, relativa ad 
	 * un unico cluster.<br>
	 * Tale processo viene ripetuto per ogni cluster presente all'interno del clusterset e ogni {@link org.jfree.data.xy.XYSeries} creata viene 
	 * aggiunta al dataset di tipo {@link org.jfree.data.xy.XYSeriesCollection} che verrà utilizzato per istanziare l'oggetto di classe ChartData
	 * da restituire.<br>
	 * Gli attributi specificati come parametri sono passati dal Client che li specifica attraverso una finestra di dialogo modale.
	 * @param data ClusterSet da considerare
	 * @param varX Attributo da considerare per l'asseX
	 * @param varY Attributo da considerare per l'asseY
	 * @param action Richiesta che il Client ha effettuato (lettura da file o scoperta da database)
	 */
	@SuppressWarnings("unchecked")
	public ChartData setGraph(ClusterSet data, Attribute varX, Attribute varY, String action) {
		ClusterData xCData, yCData;
		ChartData mainData = null;
		String yLabel = "", xLabel = "";
		XYSeriesCollection dataset = new XYSeriesCollection();
		if( action.equals("file") ) {
			XYSeries xySer = null;
			xValues = getCentList(varX, data);
			xLabel = labels;
			yValues = getCentList(varY, data);
			yLabel = labels;
			
			for(int j=0; j<data.getLength(); j++) {
				xySer = new XYSeries("Cluster" + j);
				xySer.add((Number)xValues.get(j), (Number)yValues.get(j));
				dataset.addSeries(xySer);
			}
			mainData = new ChartData(dataset, xLabel, yLabel);
		} else{
			xValues = getList(varX);
			xLabel = labels;
			yValues = getList(varY);
			yLabel = labels;
			for(int i=0; i<data.getLength(); i++) {
				xCData = new ClusterData(xValues, data.get(i));
				yCData = new ClusterData(yValues, data.get(i));
				dataset.addSeries(this.createSeries(xCData, yCData, "Cluster: " + i + " - Centroid: " + i + "    "));
			}
			mainData = new ChartData(dataset, xLabel, yLabel);
		}
		return mainData;
	}
}