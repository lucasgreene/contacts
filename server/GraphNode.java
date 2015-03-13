package contacts.server;
import java.util.Collection;
import java.util.LinkedList;

import graph.*;

public class GraphNode implements IGraphNode {
	private LinkedList<Integer> friendInts;
	private int ownID;
	private LinkedList<IGraphNode> friends;
	
	public GraphNode(int ownID, LinkedList<Integer> f){
		this.friendInts = f;
		this.ownID = ownID;
	}
	
	public void addEdge(IGraphNode n) {
		friends.addFirst(n);
	}
	
	public LinkedList<Integer> getFriendInts() {
		return friendInts;
	}
	
	@Override
	public Collection<IGraphNode> getChildren() {
		return friends;
	}

	@Override
	public int getOwnID() {
		return ownID;
	}
	
}
