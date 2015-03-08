package contacts.parser;

public class ContactNode implements Contactstuff{
	
	String name;
	String number;
	String ownID;
	Friendstuff friends;
	public ContactNode(String name, String number, String ownID, Friendstuff friends) {
		this.name = name;
		this.number = number;
		this.ownID = ownID;
		this.friends = friends;
	}
	
	@Override
	public String toString() {
		return name + ", " + number + ", " + ownID + '\n' +
				friends.toString();
	}
	
	public void add() {
		
	}
	
}
