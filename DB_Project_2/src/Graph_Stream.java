import java.io.IOException;
import java.util.*;

import org.graphstream.graph.*;
import org.graphstream.graph.implementations.*;
import org.graphstream.stream.file.*;


public class Graph_Stream {
	public void Simple()
	{
		//2. Create Graph
		Graph graph = new SingleGraph("Project 2");
		
		//graph.setStrict(false);
		//graph.setAutoCreate( true );
		
		graph.addNode("A");
		graph.addNode("B");
		graph.addNode("C");
		graph.addEdge("AB", "A", "B");
		graph.addEdge("BC", "B", "C");
		graph.addEdge("CA", "C", "A");
		
		graph.display();
		
		Node A = graph.getNode("A");
		A.getId(); 
		A.getDegree();
		
		A.hasEdgeToward("A");
		A.getEdgeToward("A");

		Edge AB = graph.getEdge("AB");
		AB.getId();
		AB.getOpposite(A);
		
		//iterate on the nodes
		for(Node n : graph) { //.getEachNode()
			System.out.println(n.getId());
		}
		
		//iterate on the edges
		for(Edge e : graph.getEachEdge()) {
			System.out.println(e.getId());
		}
		
		//obtain a read-only set of nodes (reasonably fast)
		Collection<Node> nodes = graph.getNodeSet(); //getEdgeSet()
		
		//following loop iterates on all the nodes of the graph
		for (int i = 0; i < graph.getNodeCount(); i++) {
			Node node = graph.getNode(i);
		}
		
		//The following code constructs the adjacency matrix of a graph
		int n = graph.getNodeCount();
		byte adjacencyMatrix[][] = new byte[n][n];
		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++)
				adjacencyMatrix[i][j] = (byte) (graph.getNode(i).hasEdgeBetween(j) ? 1 : 0);
		

	}
	
	public void Pajek()
	{	
		Graph graph = new SingleGraph("Pajek");
		
		FileSource dgs = new FileSourcePajek();
		dgs.addSink(graph);
		try {
			//dgs.begin(getClass().getResourceAsStream("datasets/karate_edge_list.net")); //read one event at a time
			dgs.readAll("datasets/karate_edge_list.net");
			graph.display();
		} catch( IOException e) {
		} finally {
			dgs.removeSink(graph);
		}
	}
}
