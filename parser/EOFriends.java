package contacts.parser;

import contacts.addressbook.Person;

public class EOFriends implements Friendstuff{
	
	@Override 
	public String toString() {
		return "</friendlist>" ;
	}
	
	@Override
	public void add(Person p) {
		
	}

}
