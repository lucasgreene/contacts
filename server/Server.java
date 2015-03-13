package contacts.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.LinkedList;


import contacts.addressbook.AddressBook;
import contacts.addressbook.Person;
import contacts.parser.AddressbookNode;
import contacts.parser.Parser;
import contacts.parser.TokenException;
import contacts.parser.XMLTokenizer;
import graph.*;

public class Server {

	AddressBook book;
	String xmlFile;
	AddressbookNode abNode;
	BufferedReader iStream;
	private ServerSocket socket;
	private LinkedList<GraphNode> people = new LinkedList<GraphNode>();
	private Graph friendGraph = new Graph();

	public Server(String xmlFile, int port) throws TokenException, UnknownHostException, IOException {
		this.xmlFile = xmlFile;
		BufferedReader reader = new BufferedReader(new FileReader( xmlFile));
		XMLTokenizer t = new XMLTokenizer(reader);
		Parser p = new Parser(t);
		abNode = p.parseXMLPage();
		this.book = abNode.toAddressbook();
		this.socket = new ServerSocket(port);
		createNodes();
		friendGraph.createGraph(people);
	}
	
	private void createNodes() {
		for (Person p : book.getPeople()) {
			people.addFirst(new GraphNode(p.ownID, p.getFriends()));
		}
	}
	
	private void updateGraph() {
		people = new LinkedList<GraphNode>();
		friendGraph = new Graph();
		createNodes();
		friendGraph.createGraph(people);
	}
	
	public void updateAddressbook() throws IOException {
		String update = book.toXML();
		BufferedWriter bw = new BufferedWriter(new FileWriter(xmlFile));
		bw.write(update);
		bw.close();
	}

	public void takeInput() throws IOException, TokenException {
		System.out.println("Server is running...");
		while (true) {
			Socket asock = socket.accept(); 
			System.out.println("Connected to client socket...");

			BufferedReader br1 = new BufferedReader(new InputStreamReader(asock.getInputStream()));

			String receive = br1.readLine();
			if (receive.equals("PUSH")){
				getPush(asock, br1);
			} else if(receive.equals("PULL")){
				getPull(asock, br1);
			} else if (receive.equals("QUERY PATH")){
				getPath(asock, br1);
			} else if(receive.equals("QUERY MUTUAL")){
				getMutual(asock, br1);
			} else {
				System.out.println("Incorrect input from client");
				asock.shutdownInput();
				BufferedWriter bw1 = new BufferedWriter(new OutputStreamWriter(asock.getOutputStream()));
				bw1.write("ERROR: SERVER COULND'T PARSE INPUT");
			}
			

		}
	}
	
	private void getMutual(Socket asock, BufferedReader br1) throws IOException {
		try {
			String name1 = br1.readLine();
			String name2 = br1.readLine();
			IGraphNode n1 = friendGraph.getNode(book.getPersonbyName(name1).ownID);
			IGraphNode n2 = friendGraph.getNode(book.getPersonbyName(name2).ownID);
			java.util.Set<IGraphNode> set = GraphAlgorithms.mutualNodes(n1, n2);
			StringBuilder toReturn = new StringBuilder();
			if (!set.isEmpty()) {
				toReturn.append("Mutual Nodes: \n");
				for (IGraphNode i : set) {
					toReturn.append(book.getPersonbyID(i.getOwnID()).name);
					toReturn.append(" | ");
				}
				toReturn.append("End of Nodes");
			} else {
				toReturn.append("No mutual friends");
			}
			
			BufferedWriter bw1 = new BufferedWriter(new OutputStreamWriter(asock.getOutputStream()));
			String path = toReturn.toString();
			bw1.write(path);
			bw1.flush();
			asock.shutdownOutput();
		} catch (IOException | NullPointerException e) {
			BufferedWriter bw1 = new BufferedWriter(new OutputStreamWriter(asock.getOutputStream()));
			bw1.write("Server Error: incorrect input");
			bw1.flush();
			asock.shutdownOutput();
			
		}
		
	}
	
	private void getPath(Socket asock, BufferedReader br1) throws IOException {
			
			try {
				String name1 = br1.readLine();
				String name2 = br1.readLine();
				IGraphNode n1 = friendGraph.getNode(book.getPersonbyName(name1).ownID);
				IGraphNode n2 = friendGraph.getNode(book.getPersonbyName(name2).ownID);
				java.util.List<IGraphNode> list = GraphAlgorithms.shortestPath(n1, n2);
				StringBuilder toReturn = new StringBuilder();
				if (list != null) {
					toReturn.append("Shortest Path: \n");
					for (IGraphNode i : list) {
						toReturn.append(book.getPersonbyID(i.getOwnID()).name);
						toReturn.append(" -> ");
					}
					toReturn.append("End of Path");
				} else {
					toReturn.append("No path between friends");
				}
				
				BufferedWriter bw1 = new BufferedWriter(new OutputStreamWriter(asock.getOutputStream()));
				String path = toReturn.toString();
				bw1.write(path);
				bw1.flush();
				asock.shutdownOutput();
			} catch (IOException | NullPointerException e) {
				BufferedWriter bw1 = new BufferedWriter(new OutputStreamWriter(asock.getOutputStream()));
				bw1.write("Server Error: incorrect input");
				bw1.flush();
				asock.shutdownOutput();
				
			}
			
		
		}


	private void getPull(Socket asock, BufferedReader br1) throws IOException {
		asock.shutdownInput();
		BufferedWriter bw1 = new BufferedWriter(new OutputStreamWriter(asock.getOutputStream()));
		try {
			String xml = book.toXML();

			bw1.write(xml, 0, xml.length());

			bw1.flush();
			asock.shutdownOutput();

		} catch (NullPointerException e) {
			String header = "ERROR";
			bw1.write(header, 0, header.length());

			bw1.flush();
			asock.shutdownOutput();
		}

	}

	private void getPush(Socket asock, BufferedReader br1) throws IOException, TokenException {
		BufferedWriter bw1 = new BufferedWriter(new OutputStreamWriter(asock.getOutputStream()));
		asock.shutdownInput();

		try {
			String receive = br1.readLine();
			StringReader reader = new StringReader(receive);
			XMLTokenizer t = new XMLTokenizer(reader);
			Parser p = new Parser(t);
			abNode = p.parseXMLPage();
			this.book = abNode.toAddressbook();
			updateAddressbook();
			updateGraph();
			String message = "OK";
			System.out.println("Updated Address Book");
			bw1.write(message, 0, message.length());
			bw1.flush();
			asock.shutdownOutput();
		} catch (IOException e) {
			String message = "ERROR";
			System.out.println(message);

			bw1.write(message, 0, message.length());
			bw1.flush();
			asock.shutdownOutput();
		} 
	}



	public static void main(String[] args) {

		Server s;
		try {
			s = new Server("src/contacts/server/server.xml", 1818);
			System.out.println(s.friendGraph);
			/* 
			//Graph tests
			Graph test = s.friendGraph;
			IGraphNode n = test.getNode(4);
			System.out.println(n.getOwnID());
			IGraphNode n2 = test.getNode(404);
			System.out.println(n2.getOwnID());
			Collection<IGraphNode> c = n.getChildren();
			Collection<IGraphNode> c2 = n2.getChildren();
			IGraphNode n3 = test.getNode(2);
			Collection<IGraphNode> c3 = n3.getChildren();
			System.out.println("2s childs");
			for (IGraphNode i : c3) {
				System.out.println(i.getOwnID());
			}
			System.out.println("Devils childs");
			for (IGraphNode i : c) {
				System.out.println(i.getOwnID());
			}
			System.out.println("Lukes childs");
			for (IGraphNode j : c2) {
				System.out.println(j.getOwnID());
			}
			IGraphNode n4 = test.getNode(0);
			Collection<IGraphNode> c4 = n4.getChildren();
			System.out.println("0's chlds");
			for (IGraphNode i : c4) {
				System.out.println(i.getOwnID());
			}
			*/
			//System.out.println(s.book.toXML());
			s.takeInput();
		} catch (IOException e) {

			e.printStackTrace();
		} catch (TokenException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 



	}
	
}
