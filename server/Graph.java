package contacts.server;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;

import graph.*; 

public class Graph implements IGraph{
	
	HashMap<Integer, IGraphNode> graph;
	
	public Graph() {
		graph = new HashMap<Integer, IGraphNode>();
	}
	
	public void createGraph(LinkedList<GraphNode> people) {
		for (GraphNode p : people) {
			for (int i : p.getFriendInts()) {
				for (GraphNode toAdd : people) {
					if (toAdd.getOwnID() == i) {
						p.addEdge(toAdd);
					}
				}
			}
			graph.put(p.getOwnID(), p);
		}
	}
	
	@Override
	public Collection<IGraphNode> getNodes() {
		return graph.values();
	}

}
