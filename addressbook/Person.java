package contacts.addressbook;

import java.util.LinkedList;

public class Person {
	String name;
	String number;
	int ownID;
	LinkedList<Integer> friends;
	public Group group;

	public Person(String name, String number, int ownID,
			LinkedList<Integer> friends, Group group) {
		this.name = name;
		this.number = number;
		this.ownID = ownID;
		this.friends = friends;
		this.group = group;
	}

	public void addFriend(int id) {
		if (!friends.contains(id)) {
			friends.addLast(id);
		}	
	}

	public void removeFriend(int id) {
		friends.remove(id);
	}
	
	@Override
	public String toString() {
		return this.name;
	}
}
