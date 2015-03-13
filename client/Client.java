package contacts.client;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.net.Socket;
import java.net.UnknownHostException;

import contacts.addressbook.AddressBook;
import contacts.addressbook.Group;
import contacts.parser.AddressbookNode;
import contacts.parser.Parser;
import contacts.parser.TokenException;
import contacts.parser.XMLTokenizer;


public class Client {

	AddressBook book;
	String xmlFile;
	BufferedReader iStream;
	private boolean quit = false;
	private static String host;
	private static int port;
	private AddressbookNode abNode;
	public Client(String xmlFile, String host, int port) throws TokenException, UnknownHostException, IOException {
		BufferedReader reader = new BufferedReader(new FileReader( xmlFile));
		XMLTokenizer t = new XMLTokenizer(reader);
		Parser p = new Parser(t);
		book = p.parseXMLPage().toAddressbook();
		iStream = new BufferedReader(new InputStreamReader(System.in));
		this.port = port;
		this.host = host;
		this.xmlFile = xmlFile;
	}

	
	public void quit () {
		quit = true;
	}
	
	public void updateAddressbook() throws IOException {
		String update = book.toXML();
		BufferedWriter bw = new BufferedWriter(new FileWriter(xmlFile));
		bw.write(update);
		bw.close();
	}
	
	public void takeInput() throws IOException, TokenException {

		InputParser parser = new InputParser(book, iStream, this);
		while (!quit) {
			System.out.println("Enter a command:");
			String message = iStream.readLine();
			parser.parse(message);
		}
		iStream.close();
		System.out.println("Goodbye");
	}

	public void push() throws IOException {
		Socket socket = new Socket(host, port);
		String xml = book.toXML();
		BufferedWriter bw1 = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

		String header = "PUSH\n";

		bw1.write(header, 0, header.length());
		bw1.write(xml, 0, xml.length());

		bw1.flush();
		socket.shutdownOutput();
		BufferedReader message = new BufferedReader (new InputStreamReader(socket.getInputStream()));
		System.out.println(message.readLine());
		socket.shutdownInput();
		socket.close();

	}

	public void pull() throws IOException, TokenException {
		Socket socket = new Socket(host,port);
		BufferedWriter bw1 = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		BufferedReader br1 = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		String header = "PULL";
		bw1.write(header, 0, header.length());
		bw1.flush();
		socket.shutdownOutput();
		String receive = br1.readLine();
		if (receive.equals("ERROR")){
			System.out.println("Error pulling xml from server");
		} else {
			StringReader reader = new StringReader(receive);
			XMLTokenizer t = new XMLTokenizer(reader);
			Parser p = new Parser(t);
			abNode = p.parseXMLPage();
			this.book = abNode.toAddressbook();
			System.out.println("Updated Address Book");
		}
		socket.shutdownInput();
		socket.close();
		


	}

	public void queryPath() throws UnknownHostException, IOException {
		System.out.println("Enter a name");
		String name1 = iStream.readLine();
		System.out.println("Enter another name");
		String name2 = iStream.readLine();
		Socket socket = new Socket(host,port);
		BufferedWriter bw1 = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		BufferedReader br1 = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		String header = "QUERY PATH\n";
		bw1.write(header);
		bw1.write(name1 + "\n");
		bw1.write(name2);
		bw1.flush();
		socket.shutdownOutput();
		StringBuilder sb = new StringBuilder();
		String message = br1.readLine();
		while (message != null) {
			System.out.println(message);
			sb.append(message);
			message = br1.readLine();
		}
		socket.shutdownInput();
		socket.close();
	}

	public void queryMutual() throws IOException {
		System.out.println("Enter a name");
		String name1 = iStream.readLine();
		System.out.println("Enter another name");
		String name2 = iStream.readLine();
		Socket socket = new Socket(host,port);
		BufferedWriter bw1 = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		BufferedReader br1 = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		String header = "QUERY MUTUAL\n";
		bw1.write(header);
		bw1.write(name1 + "\n");
		bw1.write(name2);
		bw1.flush();
		socket.shutdownOutput();
		StringBuilder sb = new StringBuilder();
		String message = br1.readLine();
		while (message != null) {
			System.out.println(message);
			sb.append(message);
			message = br1.readLine();
		}
		socket.shutdownInput();
		socket.close();

	}

	public static void main(String[] args) throws IOException {
		try {

			Client test = new Client("src/contacts/client/client.xml", "localhost", 1818);
			test.takeInput();
		} catch (FileNotFoundException e){
			System.out.println(e.getMessage());
			e.printStackTrace();
		} catch (TokenException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

}
