package contacts.parser;

import contacts.addressbook.AddressBook;
import contacts.addressbook.Group;

public class OuterGroup implements Pagestuff {

	String name;
	Groupstuff innerStuff;
	Pagestuff outerStuff;

	public OuterGroup(String name, Groupstuff innerStuff, Pagestuff outerStuff) {
		this.name = name;
		this.innerStuff = innerStuff;
		this.outerStuff = outerStuff;
	}
	
	@Override
	public void add(AddressBook toReturn) {
		Group OG = new Group(name,null);
		innerStuff.add(toReturn, OG);
		toReturn.groupAdd(OG);
		outerStuff.add(toReturn);
		
	}

	@Override
	public String toString() {
		return name + "\n" + innerStuff.toString() + "\n"
				+ outerStuff.toString();
	}
}
