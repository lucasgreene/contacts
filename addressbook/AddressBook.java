package contacts.addressbook;

import java.util.HashMap;
import java.util.LinkedList;

public class AddressBook {
	public HashMap<String, Group> groups;
	HashMap<String, Person> nameContacts;
	HashMap<Integer, Person> IDContacts;
	private int bookSize;
	private Group topGroup;
	
	public AddressBook(){
		this.groups = new HashMap<String, Group>();
		this.nameContacts = new HashMap<String, Person>();
		this.IDContacts = new HashMap <Integer, Person>();
		this.bookSize = 1000;
		this.topGroup = new Group(null,null);
	}
	public Group getTopGroup() {
		return topGroup;
	}
	
	public void setTopGroups(Group g) {
		topGroup.addChildGroup(g);
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
	
	public boolean nameExists(String name){
		return nameContacts.get(name) != null;
	}
	

	
	public boolean idExists(int i) {
		return IDContacts.get(i) != null;
	}
	
	public boolean groupExists(String name) {
		return groups.get(name) != null;
	}
	
	public void updatePerson(String name, String num, LinkedList<Integer> friends) {
		Person p = getPersonbyName(name);
		p.number = num;
		p.friends = friends;
		
	}
	
	public void addToFriends(LinkedList<Integer> fL, int ownID) {
		for (int i : fL) {
			Person f = getPersonbyID(i);
			f.addFriend(ownID);
		}
	}
	
	public int getID(String name){
		Person person = nameContacts.get(name);
		return person.ownID;
	}
	
	public Person getPersonbyName(String name){
		Person person = nameContacts.get(name);
		return person;
	}
	
	public Person getPersonbyID(int id) {
		Person person = IDContacts.get(id);
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
	public String toXML() {
		Group top = getTopGroup();
		for (Group g : top.getChildGroups()) {
			
		}
		
	}*/
	/*
	 * error handling for if person doesn't exist?
	 */
}
