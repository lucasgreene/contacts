package contacts.addressbook;

import java.util.LinkedList;

public class Person {
	String name;
	String number;
	int ownID;
	LinkedList<Integer> friends;

	public Person(String name, String number, int ownID, LinkedList<Integer> friends) {
		this.name = name;
		this.number = number;
		this.ownID = ownID;
		this.friends = friends;
	}
	public void addFriend(int id){
		friends.addLast(id);
	}
}
