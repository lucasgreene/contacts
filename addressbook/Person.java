package contacts.addressbook;

import java.util.LinkedList;

public class Person {
	public String name;
	String number;
	public int ownID;
	LinkedList<Integer> friends;
	private Group group;

	public Person(String name, String number, int ownID,
			LinkedList<Integer> friends, Group group) {
		this.name = name;
		this.number = number;
		this.ownID = ownID;
		this.friends = friends;
		this.group = group;
		group.addPerson(this);
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


	public Group getGroup() {
		return group;
	}
	
	public LinkedList<Integer> getFriends() {
		return friends;
	}
	
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Person)) {
			return false;
		} else {
			Person that = (Person) o;
			return (this.name.equals(that.name)
					&& this.number.equals(that.number)
					&& this.ownID == that.ownID
					&& this.friends.equals(that.friends) && this.group
						.equals(that.getGroup()));
		}
	}
}
