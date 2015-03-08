package contacts.client;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

import contacts.addressbook.AddressBook;
import contacts.parser.AddressbookNode;
import contacts.parser.Parser;
import contacts.parser.TokenException;
import contacts.parser.XMLTokenizer;


public class Client {
	
	AddressBook book;
	AddressbookNode abNode;
	BufferedReader iStream;
	public Client(String xmlFile) throws FileNotFoundException, TokenException {
		BufferedReader reader = new BufferedReader(new FileReader( xmlFile));
		XMLTokenizer t = new XMLTokenizer(reader);
		Parser p = new Parser(t);
		abNode = p.parseXMLPage();
		book = abNode.toAddressbook();
		iStream = new BufferedReader(new InputStreamReader(System.in));
		
	}
	
	public void takeInput() throws IOException {
		String message = iStream.readLine();
		while (message != null) {
			InputParser.parse(message, this);
		}
		iStream.close();
	}
		
	public static void main(String[] args) throws IOException {
		try {
			Client test = new Client("src/contacts/example.xml");
			test.takeInput();
			System.out.println(test.abNode.toString());
		} catch (FileNotFoundException e){
			System.out.println(e.getMessage());
			e.printStackTrace();
		} catch (TokenException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

}
