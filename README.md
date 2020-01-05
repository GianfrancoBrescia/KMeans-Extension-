# DESCRIZIONE DEL PROGETTO KMeans-Extension-
A differenza del progetto **KMeans**<sup>1</sup>, nell’estensione che ho realizzato **Client** e **Server** sono stati modificati. In 
particolare, ho aggiunto nel **Client** la possibilità di visualizzare il grafico relativo ai cluster scoperti attraverso l’implementazione 
del metodo `chartAction`: una volta effettuata la scoperta di cluster, compare una finestra di dialogo (realizzata dalla classe 
AttributeDialog) in cui è possibile scegliere fra due tipi di grafici (a torta o ad assi cartesiani) realizzati mediante il metodo 
`createChart` definito nella classe StreamChart. <br>
Per il **Server**, invece, ho realizzato un’interfaccia grafica suddivisa in due pannelli: uno contiene tutte le operazioni effettuate 
step by step dai vari Client connessi al Server, mentre l’altro contiene un riepilogo delle operazioni svolte da un determinato Client che
può essere selezionato nel menu a tendina (JComboBox). Questa interfaccia grafica è resa possibile mediante l’implementazione del metodo 
`initApp` definito nella classe ServerApp.
<br><br><br>
<sup>1</sup>[Clicca qui per visualizzare il progetto KMeans senza estensioni](https://github.com/GianfrancoBrescia/KMeans)
