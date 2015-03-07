package contacts.parser;

public class FriendNode implements Friendstuff{
	
	private String id;
	private Friendstuff nextFriend;
	public FriendNode(String id, Friendstuff nextFriend) {
		this.id = id;
		this.nextFriend = nextFriend;
	}
}
