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
	private AddressbookNode abNode;
	public Client(String xmlFile, String host, int port) throws TokenException, UnknownHostException, IOException {
		BufferedReader reader = new BufferedReader(new FileReader( xmlFile));
		XMLTokenizer t = new XMLTokenizer(reader);
		Parser p = new Parser(t);
		book = p.parseXMLPage().toAddressbook();
		iStream = new BufferedReader(new InputStreamReader(System.in));
		this.socket = new Socket(host, port);

	}

	/*public void readNWrite(String command) throws IOException {

		BufferedReader br1 = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		BufferedWriter bw1 = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())); 

	    InputStream is = socket.getInputStream();
	    FileOutputStream fos = new FileOutputStream(xmlFile);
	    BufferedOutputStream bos = new BufferedOutputStream(fos);

		


	    int in = System.in.read();

		while ( in != -1) {

			bw1.write(in);
			in = System.in.read();
		}

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
	 */
	public void quit () {
		quit = true;
	}
	
	public void takeInput() throws IOException, TokenException {

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

	public void push() throws IOException {
		String xml = book.toXML();
		BufferedWriter bw1 = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

		String header = "PUSH";

		bw1.write(header, 0, header.length());
		bw1.write(xml, 0, xml.length());

		bw1.flush();
		bw1.close();

	}

	public void pull() throws IOException, TokenException {
		BufferedWriter bw1 = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		BufferedReader br1 = new BufferedReader(new InputStreamReader(socket.getInputStream()));

		String header = "PULL";

		bw1.write(header, 0, header.length());
		bw1.flush();
		bw1.close();
		String receive = br1.readLine();
		if (receive.equals("ERROR")){
			System.out.println("Error pulling xml from server");
		} else {
			BufferedReader reader = new BufferedReader(new FileReader( receive));
			XMLTokenizer t = new XMLTokenizer(reader);
			Parser p = new Parser(t);
			abNode = p.parseXMLPage();
			this.book = abNode.toAddressbook();
		}


	}

	public void queryPath() {

	}

	public void queryMutual() {

	}

	public static void main(String[] args) throws IOException {
		try {

			Client test = new Client("src/contacts/client/client.xml", "localhost", 1818);
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
