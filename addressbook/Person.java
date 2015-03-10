package contacts.addressbook;

import java.util.LinkedList;

public class Person {
	String name;
	String number;
	int ownID;
	LinkedList<Integer> friends;
	Group group;

	public Person(String name, String number, int ownID, LinkedList<Integer> friends, Group group) {
		this.name = name;
		this.number = number;
		this.ownID = ownID;
		this.friends = friends;
		this.group = group;
	}
	public void addFriend(int id){
		friends.addLast(id);
	}
	public void removeFriend(int id){
		friends.remove(id);
	}
}
