package contacts.parser;

public class Parser {

	XMLTokenizer t;
	Token current;

	public Parser(XMLTokenizer t) {
		this.t = t;
		t.advance();
		current = t.current();
	}

	private void advance() {
		t.advance();
		current = t.current();
	}

	public AddressbookNode parseXMLPage() throws TokenException {
		if (current.kind == 5) {
			System.out.println(current.toString());
			advance();
			AddressbookNode book = new AddressbookNode(parsePagestuff());
			return book;
		} else {
			throw new TokenException();
		}

	}

	public Pagestuff parsePagestuff() throws TokenException {
		Pagestuff toReturn;
		System.out.println(current.toString());
		Token name = current;
		if (current.kind == 23) {
			advance();
			toReturn = new OuterGroup(name.attribute, parseGroupstuff(),
					parsePagestuff());
		} else if (current.kind == 6) {
			advance();
			toReturn = new EOAddressbook();
		} else {
			throw new TokenException();
		}
		return toReturn;
	}

	public Groupstuff parseGroupstuff() throws TokenException {
		Groupstuff toReturn;
		System.out.println(current.toString());
		Token name = current;
		if (current.kind == 23) {
			advance();
			toReturn = new InnerGroup(name.attribute, parseGroupstuff(),
					parseGroupstuff());
		} else if (current.kind == 8) {
			advance();
			toReturn = new ContactExp(parseContactstuff(), parseGroupstuff());
		} else if (current.kind == 7) {
			advance();
			toReturn = new EOGroup();
		} else {
			System.out.println("this");
			throw new TokenException();
		}
		return toReturn;
	}

	public Contactstuff parseContactstuff() throws TokenException {
		System.out.println(current.toString());
		Contactstuff toReturn;
		int openName = current.kind;
		advance();
		int nameText = current.kind;
		Token name = current;
		advance();
		int closeName = current.kind;
		advance();
		int openNum = current.kind;
		advance();
		int numText = current.kind;
		Token num = current;
		advance();
		int closeNum = current.kind;
		advance();
		int openID = current.kind;
		advance();
		int idText = current.kind;
		Token id = current;
		advance();
		int closeID = current.kind;
		advance();
		int openFriends = current.kind;
		advance();
		if (openName == 10 && nameText == 24 && closeName == 11
				&& openNum == 12 && numText == 24 && closeNum == 13
				&& openID == 14 && idText == 24 && closeID == 15
				&& openFriends == 16) {
			toReturn = new ContactNode(name.attribute, num.attribute,
					id.attribute, parseFriendstuff());
		} else {
			throw new TokenException();
		}
		return toReturn;
	}

	public Friendstuff parseFriendstuff() throws TokenException {
		System.out.println(current.toString());
		Friendstuff toReturn;
		if (current.kind == 18) {
			advance();
			Token text = current;
			advance();
			Token endID = current;
			advance();
			if (text.kind == 24 && endID.kind == 19) {
				toReturn = new FriendNode(text.attribute, parseFriendstuff());
			} else {
				throw new TokenException();
			}
		} else if (current.kind == 17) {
			advance();
			if (current.kind == 9) {
				advance();
				toReturn = new EOFriends();
			} else {
				throw new TokenException();
			}
		} else {
			throw new TokenException();
		}
		return toReturn;

	}
}
