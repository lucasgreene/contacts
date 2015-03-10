package contacts.parser;

import contacts.addressbook.AddressBook;
import contacts.addressbook.Group;

public class EOGroup implements Groupstuff{

	
	@Override
	public String toString() {
		return "</Group>";
	}

	@Override
	public void add(AddressBook toReturn, Group OG) {
		
	}
}