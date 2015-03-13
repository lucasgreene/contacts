package contacts.client;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
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
	BufferedReader iStream;
	private boolean quit = false;
	private static String host;
	private static int port;
	private Socket socket;
	String xmlFile;
	
	public Client(String xmlFile, String host, int port) throws TokenException, UnknownHostException, IOException {
		BufferedReader reader = new BufferedReader(new FileReader( xmlFile));
		XMLTokenizer t = new XMLTokenizer(reader);
		Parser p = new Parser(t);
		book = p.parseXMLPage().toAddressbook();
		iStream = new BufferedReader(new InputStreamReader(System.in));
		this.socket = new Socket(host, port);
		this.xmlFile = xmlFile;
		
	}

	public void readNWrite(String command) throws IOException {

		BufferedReader br1 = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		BufferedWriter bw1 = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())); 
		
	   // InputStream is = socket.getInputStream();
	   // FileOutputStream fos = new FileOutputStream(xmlFile);
	   // BufferedOutputStream bos = new BufferedOutputStream(fos);
		
		bw1.write(command);
		
		bw1.flush();
		
		socket.shutdownOutput();
		
		int serveout= br1.read(); 
		while (serveout != -1) {
		
		System.out.write((char) serveout);
		serveout= br1.read(); 
		}
		
		System.out.flush();
		br1.close();

	//	socket.shutdownInput();	
	}
	
	public void quit () {
		quit = true;
	}
	
	public void takeInput() throws IOException {
		System.out.println("Enter a command:");
		String message = iStream.readLine();
		InputParser parser = new InputParser(book, iStream, this);
		while (!quit) {
			parser.parse(message);
			System.out.println("Enter a command:");
			message = iStream.readLine();
		}
		iStream.close();
		System.out.println("Goodbye");
	}
	
	public void push() {
		
	}
	
	public void pull() {
		
	}
	
	public void queryPath() {
		
	}
	
	public void queryMutual() {
		
	}
		
	public static void main(String[] args) throws IOException {
		try {
			Client test = new Client("src/contacts/example.xml", "localhost", 5789);
            //test.takeInput();
			
			
		} catch (FileNotFoundException e){
			System.out.println(e.getMessage());
			e.printStackTrace();
		} catch (TokenException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

}
