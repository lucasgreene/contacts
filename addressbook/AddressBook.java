package contacts.addressbook;

import java.util.Hashtable;

public class AddressBook {
	Hashtable<String, Group> groups;
	Hashtable<String, Person> nameContacts;
	Hashtable<Integer, Person> IDContacts;
	private int bookSize;
	private Group topGroup;
	
	public AddressBook(){
		this.groups = new Hashtable<String, Group>();
		this.nameContacts = new Hashtable<String, Person>();
		this.IDContacts = new Hashtable<Integer, Person>();
		this.bookSize = 1000;
		this.topGroup = new Group(null,null);
	}
	public Group getTopGroup() {
		return topGroup;
	}
	
	public int createNewId(String name) {
		int firstTry = name.hashCode() % bookSize;
		boolean success = false;
		while (!success) {
			if (IDContacts.containsKey(firstTry)) {
				firstTry = nextHash(firstTry);
			} else {
				success = true;
			}
		}
		return firstTry;
	}
	
	private int nextHash(int oldHash) {
		return (oldHash * 19 + 1) % bookSize;
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
	
	public boolean personExists(String name){
		return nameContacts.get(name) != null;
	}
	
	public int getID(String name){
		Person person = nameContacts.get(name);
		return person.ownID;
	}
	
	public Person getPerson(String name){
		Person person = nameContacts.get(name);
		return person;
	}
	
	public void newGroupAdd(String name, Group parentGroup){
		Group newGroup = new Group(name, parentGroup);
		groups.put(name, newGroup);
	}
	public void groupAdd(Group group){
		groups.put(group.name, group);
	}
	public void personRemove(Person person){
		person.group.removePerson(person);
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
