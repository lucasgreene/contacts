package contacts.parser;

import contacts.addressbook.AddressBook;
import contacts.addressbook.Group;

public class InnerGroup implements Groupstuff {

	String name;
	Groupstuff innerStuff;
	Groupstuff nextStuff;

	public InnerGroup(String name, Groupstuff innerStuff, Groupstuff nextStuff) {
		this.name = name;
		this.innerStuff = innerStuff;
		this.nextStuff = nextStuff;
	}
	
	@Override
	public void add(AddressBook toReturn, Group OG) {
		Group IG = new Group(name, OG);
		innerStuff.add(toReturn, IG);
		nextStuff.add(toReturn, OG);
		toReturn.groupAdd(IG);
		
	}

	@Override
	public String toString() {
		return name + "\n" + innerStuff.toString() + "\n" + nextStuff.toString();
	}

}
