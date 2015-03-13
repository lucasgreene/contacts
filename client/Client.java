package contacts.client;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

import contacts.addressbook.AddressBook;
import contacts.addressbook.Group;
import contacts.parser.AddressbookNode;
import contacts.parser.Parser;
import contacts.parser.TokenException;
import contacts.parser.XMLTokenizer;


public class Client {
	
	AddressBook book;
	AddressbookNode abNode;
	BufferedReader iStream;
	private boolean quit = false;
	public Client(String xmlFile) throws FileNotFoundException, TokenException {
		BufferedReader reader = new BufferedReader(new FileReader( xmlFile));
		XMLTokenizer t = new XMLTokenizer(reader);
		Parser p = new Parser(t);
		abNode = p.parseXMLPage();
		book = abNode.toAddressbook();
		iStream = new BufferedReader(new InputStreamReader(System.in));
		
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
			Client test = new Client("src/contacts/example.xml");
			//test.takeInput();
			AddressBook book = test.book;
			Client test2 = new Client("src/contacts/test.xml");
			AddressBook book2 = test.book;
			
			System.out.println(book2.toXML().equals(book.toXML()));
		} catch (FileNotFoundException e){
			System.out.println(e.getMessage());
			e.printStackTrace();
		} catch (TokenException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

}
