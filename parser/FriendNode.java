package contacts.parser;

import contacts.addressbook.Person;

public class FriendNode implements Friendstuff{
	
	private int id;
	private Friendstuff nextFriend;
	public FriendNode(int id, Friendstuff nextFriend) {
		this.id = id;
		this.nextFriend = nextFriend;
	}
	
	@Override
	public String toString() {
		return "FriendID: " + id + nextFriend.toString();
	}
	
	@Override
	public void add(Person p) {
		p.addFriend(id);
		nextFriend.add(p);
	}
}
