package contacts.addressbook;

import java.util.Hashtable;

public class AddressBook {
	Hashtable<String, Group> groups;
	Hashtable<String, Person> nameContacts;
	Hashtable<Integer, Person> IDContacts;
	
	public AddressBook(){
		this.groups = new Hashtable<String, Group>();
		this.nameContacts = new Hashtable<String, Person>();
		this.IDContacts = new Hashtable<Integer, Person>();
	}
	public void initPAdd(Person person){
		nameContacts.put(person.name, person);
		IDContacts.put(person.ownID, person);
	}
	public void initGAdd(Group group){
		groups.put(group.name, group);
	}
	public void personAdd(Person person, Group group){
		nameContacts.put(person.name, person);
		IDContacts.put(person.ownID, person);
		for(int id : person.friends){
			Person friend = IDContacts.get(id);
			friend.addFriend(id);
		}
		group.addPerson(person);
	}
}
