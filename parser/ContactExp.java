package contacts.parser;

import contacts.addressbook.AddressBook;
import contacts.addressbook.Group;

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

	@Override
	public void add(AddressBook toReturn, Group IG) {
		info.add(toReturn, IG);
		otherStuff.add(toReturn, IG);
		otherStuff.add(toReturn, IG); 
				
	}
}
