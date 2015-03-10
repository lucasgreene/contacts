package contacts.parser;

import java.util.LinkedList;

import contacts.addressbook.AddressBook;
import contacts.addressbook.Group;
import contacts.addressbook.Person;

public class ContactNode implements Contactstuff {

	String name;
	String number;
	int ownID;
	Friendstuff friends;

	public ContactNode(String name, String number, int ownID,
			Friendstuff friends) {
		this.name = name;
		this.number = number;
		this.ownID = ownID;
		this.friends = friends;
	}

	@Override
	public String toString() {
		return name + ", " + number + ", " + ownID + '\n' + friends.toString();
	}

	@Override
	public void add(AddressBook toReturn, Group IG) {
		Person contact = new Person(name, number, ownID,
				new LinkedList<Integer>(), IG);
		friends.add(contact);
		toReturn.initPAdd(contact);
		

	}

}
