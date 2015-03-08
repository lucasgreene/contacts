package contacts.parser;

public class InnerGroup implements Groupstuff {

	String name;
	Groupstuff stuff1;
	Groupstuff stuff2;

	public InnerGroup(String name, Groupstuff stuff1, Groupstuff stuff2) {
		this.name = name;
		this.stuff1 = stuff1;
		this.stuff2 = stuff2;
	}

	@Override
	public String toString() {
		return name + "\n" + stuff1.toString() + "\n" + stuff2.toString();
	}

}
