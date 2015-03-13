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
	
	public IGraphNode getNode(int id) {
		return graph.get(id);
	}
	
	@Override
	public Collection<IGraphNode> getNodes() {
		return graph.values();
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (IGraphNode i : graph.values()) {
			sb.append("OWNID: " + i.getOwnID() + "\n");
			for (IGraphNode j : i.getChildren()) {
				sb.append("ChildID: " + j.getOwnID() + "\n");
			}
		}
		
		return sb.toString();
	}
	
	public static void main(String[] args) {
		// Create new nodes
		LinkedList<Integer> l1 = new LinkedList<Integer>();
		l1.addFirst(1);
		l1.addFirst(2);
		GraphNode n1 = new GraphNode(0,l1);
		LinkedList<Integer> l2 = new LinkedList<Integer>();
		GraphNode n2 = new GraphNode(3,l2);
		LinkedList<Integer> l3 = new LinkedList<Integer>();
		l3.addFirst(0);
		GraphNode n3 = new GraphNode(1,l3);
		LinkedList<Integer> l4 = new LinkedList<Integer>();
		l4.addFirst(0);
		GraphNode n4 = new GraphNode(2,l4);
		Graph g = new Graph();
		LinkedList<GraphNode> ln = new LinkedList<GraphNode>();
		ln.addFirst(n1);
		ln.addFirst(n2);
		ln.addFirst(n3);
		ln.addFirst(n4);
		g.createGraph(ln);
		IGraphNode test = g.getNode(0);
		Collection<IGraphNode> childs = test.getChildren();
		System.out.println(test.getOwnID() == 0);
		for (IGraphNode i : childs) {
			System.out.println(i.getOwnID() == 1 || i.getOwnID() == 2);
		}
		
	}

}
