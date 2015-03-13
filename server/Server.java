package contacts.server;




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
	private ServerSocket socket;

	public Server(String xmlFile, int port) throws TokenException, UnknownHostException, IOException {
		BufferedReader reader = new BufferedReader(new FileReader( xmlFile));
		XMLTokenizer t = new XMLTokenizer(reader);
		Parser p = new Parser(t);
		abNode = p.parseXMLPage();
		this.book = abNode.toAddressbook();
		this.socket = new ServerSocket(port);

	}


	public void takeInput() throws IOException, TokenException {
		while (true) {
			Socket asock = socket.accept(); 

			BufferedReader br1 = new BufferedReader(new InputStreamReader(asock.getInputStream()));

			String receive = br1.readLine();
			if (receive.equals("PUSH\n")){
				getPush(asock, br1);
			} else if(receive.equals("PULL\n")){
				getPull(asock, br1);
			} else if (receive.equals(" ")){

			}

		}
	}


	private void getPull(Socket asock, BufferedReader br1) throws IOException {

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

	private void getPush(Socket asock, BufferedReader br1) throws IOException, TokenException {
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


	/*public static void main(String[] args) {

		Server s;
		try {
			s = new Server("src/contacts/server/server.xml", 1818);
			s.writeRead();
		} catch (IOException | TokenException e) {

			e.printStackTrace();
		} 



	}
	*/
}
