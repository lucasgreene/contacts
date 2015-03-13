package contacts.server;




import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
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
import contacts.client.InputParser;
import contacts.parser.AddressbookNode;
import contacts.parser.Parser;
import contacts.parser.TokenException;
import contacts.parser.XMLTokenizer;

public class Server {

	AddressBook book;
	AddressbookNode abNode;
	BufferedReader iStream;
	private boolean quit = false;
	private ServerSocket socket;
	private LinkedList<GraphNode> people;
	private Graph friendGraph;

	public Server(String xmlFile, int port) throws TokenException, UnknownHostException, IOException {
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
			people.add(new GraphNode(p.ownID, p.getFriends()));
		}
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
			} else if (receive.equals(" ")){

			}
			

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
			s.takeInput();
			System.out.println("happy");
		} catch (IOException e) {

			e.printStackTrace();
		} catch (TokenException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 



	}
	
}
