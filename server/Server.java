package contacts.server;


import Server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import contacts.addressbook.AddressBook;
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
	private static String host;
	private static int port;
	private ServerSocket socket;

	public Server(String xmlFile, int port) throws TokenException, UnknownHostException, IOException {
		BufferedReader reader = new BufferedReader(new FileReader( xmlFile));
		XMLTokenizer t = new XMLTokenizer(reader);
		Parser p = new Parser(t);
		abNode = p.parseXMLPage();
		this.book = abNode.toAddressbook();
		this.iStream = new BufferedReader(new InputStreamReader(System.in));
		this.socket = new ServerSocket(PORT);

	}
<<<<<<< HEAD
	
	
=======
>>>>>>> 93cb569e063fd95cc132c03678197d2387238a2b

	public void takeInput() throws IOException {
		while (true) {
			Socket asock = socket.accept(); 

			BufferedReader br1 = new BufferedReader(new InputStreamReader(asock.getInputStream()));
			BufferedWriter bw1 = new BufferedWriter(new OutputStreamWriter(asock.getOutputStream()));

			String receive = br1.readLine();
			if (receive.equals("PUSH\n")){
				getPush(asock, br1);
			} else if(receive.equals("PULL\n")){
				getPull(asock, br1);
			} else if (receive.equals(" ")){

			}

		}
	}


	private void getPull(Socket asock, BufferedReader br1) {

		BufferedWriter bw1 = new BufferedWriter(new OutputStreamWriter(asock.getOutputStream()));
		try {
			String xml = book.toXML();

			bw1.write(xml, 0, xml.length());

			bw1.flush();
			bw1.close();

		} catch (NullPointerException e) {
			String header = "ERROR";
			bw1.write(header, 0, header.length());

			bw1.flush();
			bw1.close();
		}

	}

	private void getPush(Socket asock, BufferedReader br1) {
		BufferedWriter bw1 = new BufferedWriter(new OutputStreamWriter(asock.getOutputStream()));

		try {
			String receive = br1.readLine();
			BufferedReader reader = new BufferedReader(new FileReader( receive));
			XMLTokenizer t = new XMLTokenizer(reader);
			Parser p = new Parser(t);
			abNode = p.parseXMLPage();
			this.book = abNode.toAddressbook();
			String message = "OK";

			bw1.write(message, 0, message.length());
		} catch (IOException e) {
			String message = "ERROR";
			bw1.write(message, 0, message.length());
		} 
	}

	public void writeRead() throws IOException {

		while (true) {
			Socket asock = socket.accept(); 

			BufferedReader br1 = new BufferedReader(new InputStreamReader(asock.getInputStream()));
			BufferedWriter bw1 = new BufferedWriter(new OutputStreamWriter(asock.getOutputStream())); 

			int in = asock.getInputStream().read();
			while (in != -1) {

				bw1.write(in);
				in = asock.getInputStream().read();
			}

			bw1.flush();



			int serveout= br1.read(); 
			while (serveout != -1) {

				asock.getOutputStream().write((char) serveout);
				serveout = br1.read(); 
			}

			asock.getOutputStream().flush();
			br1.close();
		}

		//	socket.shutdownInput();	
	}







	public static void main(String[] args) {

		Server s;
		try {
			s = new Server(PORT);
			s.writeRead();
		} catch (IOException e) {

			e.printStackTrace();
		} 



	}
}
