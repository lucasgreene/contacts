package contacts.parser;

public class ContactExp implements Groupstuff{
	
	Contactstuff info;
	Groupstuff otherStuff;
	public ContactExp(Contactstuff info, Groupstuff otherStuff) {
		this.info = info;
		this.otherStuff = otherStuff;
	}
	
	@Override
	public String toString() {
		return info.toString()  + "\n" + otherStuff.toString();
	}
}
