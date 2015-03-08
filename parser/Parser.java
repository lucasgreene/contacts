package contacts.parser;

public class Parser {

	XMLTokenizer t;
	Token current;

	public Parser(XMLTokenizer t) {
		this.t = t;
		t.advance();
	}
	
	private void advance() {
		t.advance();
		current = t.current();
	}

	public AddressbookNode parseXMLPage() throws TokenException {
		current = t.current();
		if (current.kind == 5) {
			advance();
			AddressbookNode book = new AddressbookNode(parsePagestuff());
			return book;
		} else {
			throw new TokenException();
		}

	}

	public Pagestuff parsePagestuff() throws TokenException {
		Pagestuff toReturn;
		if (current.kind == 23) {
			toReturn = new OuterGroup(current.attribute, parseGroupstuff(), parsePagestuff());
			advance();
		} else if (current.kind == 6) {
			toReturn =  new EOAddressbook();
			advance();
		} else {
			throw new TokenException();
		}
		return toReturn;
	}
	
	public Groupstuff parseGroupstuff() {
		Groupstuff toReturn;
		if (current.kind == 5) {
			toReturn = new InnerGroup(current.attribute, parseGroupstuff(), parseGroupstuff());
		} else if (current.kind == 8) {
			toReturn = new 
		}
	}
}
