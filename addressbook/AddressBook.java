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
	public void personAdd(Person person, Group group){
		if (nameContacts.get(person.name) == null){
			group.addPerson(person);
		}
		nameContacts.put(person.name, person);
		IDContacts.put(person.ownID, person);
		for(int id : person.friends){
			Person friend = IDContacts.get(id);
			friend.addFriend(person.ownID);
		}
	}
	public void groupAdd(String name, Group parentGroup){
		Group newGroup = new Group(name, parentGroup);
		groups.put(name, newGroup);
	}
	public void personRemove(Person person, Group group){
		group.removePerson(person);
		nameContacts.remove(person.name);
		IDContacts.remove(person.ownID);
		for(int id : person.friends){
			Person friend = IDContacts.get(id);
			friend.removeFriend(person.ownID);
		}
	}
	/*
	 * error handling for if person doesn't exist?
	 */
}
