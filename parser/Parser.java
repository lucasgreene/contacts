package contacts.parser;

public class Parser {
	
	XMLTokenizer t;
	
	public Parser(XMLTokenizer t) {
		this.t = t;
		t.advance();
	}
	
	public AddressbookNode parseXMLPage() {
		Token current = t.current();
		if (current.kind == 5) {
			t.advance();
			current = t.current();
			AddressbookNode book = new AddressbookNode(parsePagestuff());
			return book;
		} else {
			throw new TokenException();
		}
		
	}

}
