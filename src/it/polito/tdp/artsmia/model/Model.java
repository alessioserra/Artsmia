package it.polito.tdp.artsmia.model;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.artsmia.db.ArtsmiaDAO;

public class Model {

	//Dichiaro tutte le variabili senza creare oggetti
	List<ArtObject> oggetti;
	Graph<ArtObject, DefaultWeightedEdge> grafo;
	Map<Integer, ArtObject> idMap;
	
	public void creaGrafo() {
		
		//Ottengo oggetti dal database
		ArtsmiaDAO dao = new ArtsmiaDAO();
		idMap = new HashMap<>();
		oggetti=dao.listObjects(idMap);
		
		//Creo grafo
		grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		
		//Aggiungo vertici al grafo
		Graphs.addAllVertices(grafo, oggetti);
		
		//Devo creare ARCHI
		List<Accoppiamenti> accoppiamenti = dao.creoArchi();
		
		for (Accoppiamenti a : accoppiamenti) { //Aggiungo arco con peso
			Graphs.addEdge(grafo, idMap.get(a.getId1()), idMap.get(a.getId2()), a.getPeso());
		}
		
		System.out.println("Grafo Creato!\n#NODI: "+grafo.vertexSet().size()+"\n#ARCHI: "+grafo.edgeSet().size());
	}
	
	
}
