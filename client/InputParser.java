package contacts.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.LinkedList;

import contacts.addressbook.AddressBook;
import contacts.addressbook.Group;
import contacts.addressbook.Person;
import contacts.parser.TokenException;

public class InputParser {

	AddressBook book;
	BufferedReader iStream;
	Client client;

	public InputParser(AddressBook book, BufferedReader iStream, Client client) {
		this.book = book;
		this.iStream = iStream;
		this.client = client;
	}

	public void parse(String s) throws IOException, TokenException {

		boolean correctInput = false;
		while (!correctInput) {
			correctInput = true;
			if (s.equals("add")) {
				parseAdd();
				client.updateAddressbook();
			} else if (s.equals("remove")) {
				parseRemove();
				client.updateAddressbook();
			} else if (s.equals("group")) {
				parseGroup();
			} else if (s.equals("push")) {
				client.push();
			} else if (s.equals("pull")) {
				client.pull();
				client.updateAddressbook();
			} else if (s.equals("query path")) {
				client.queryPath();
			} else if (s.equals("query mutual")) {
				client.queryMutual();
			} else if (s.equals("quit")) {
				client.quit();
			} else {
				System.out
						.println("Please enter one of: add; remove;"
								+ " group; push; pull; query path;"
								+ " query mutual; quit");
				correctInput = false;
				s = iStream.readLine();
			}
		}

	}
	

	private void parseAdd() throws IOException {
		String name = null;
		String number = null;
		LinkedList<Integer> friendInts = null;

		boolean correctInput = false;
		while (!correctInput) {
			correctInput = true;
			System.out.println("Name: ");
			name = iStream.readLine();
			System.out.println("Number: ");
			number = iStream.readLine();
			System.out.println("Friends: ");
			String friends = iStream.readLine();
			String[] friendArr = friends.split(", ");
			friendInts = new LinkedList<Integer>();
			for (String friend : friendArr) {
				if (book.nameExists(friend)) {
					friendInts.addFirst(book.getID(friend));
					correctInput = correctInput && true;
				} else {
				correctInput = correctInput && false;
				}
			}	
			if (!correctInput) {
				System.out.println("Friends don't match");
			}
		}
		if (book.nameExists(name)) {
			book.updatePerson(name, number, friendInts);
			System.out.println("Updating contact...");
		} else {
			int ID = book.createNewId(name);
			Group g = getGroup();
			Person newPerson = new Person(name, number, ID, friendInts, g);
			book.addToFriends(friendInts, ID);
			book.personAdd(newPerson, g);
			if (!book.groupExists(g.name)) {
				book.groupAdd(g);
			}
			System.out.println("Added " + name + " in group " + g.name);
		}
	}
	
	public void parseGroup() throws IOException {
		
		boolean correctInput = false;
		while (!correctInput) {
			correctInput = true;
			System.out.println("Enter a group name");
			String s = iStream.readLine();
			try {
				Group g = book.getGroup(s);
				for (Person p : g.getContacts()) {
					System.out.println(p.name);
				}
			} catch (NullPointerException e) {
				System.out.println("Invalid Group");
				correctInput = false;
			}
		}
	}
	
	public void parseRemove() throws IOException {
		String name = null;

		boolean correctInput = false;
		while (!correctInput) {
			correctInput = true;
			System.out.println("Name: ");
			name = iStream.readLine();
			if (book.nameExists(name)){
				Person person = book.getPersonbyName(name);
				book.personRemove(person);
				System.out.println(name + " has been removed.");
			} else {
				correctInput = false;
				System.out.println("Person not found, try again");
			}
		}
	}

	private Group getGroup() throws IOException {

		Group topGroup = book.getTopGroup();
		LinkedList<Group> childGroups;
		boolean isTopGroup = true;
		boolean selectedGroup = false;
		while (!selectedGroup) {
			childGroups = topGroup.getChildGroups();
			System.out.println("Select a number");
			String toPrint = "";
			int length = childGroups.size();
			int ind = 0;
			for (Group g : childGroups) {
				toPrint += g.name + ": " + Integer.toString(ind) + " ";
				ind++;
			}
			toPrint += "New Group: " + Integer.toString(length);
			if (!isTopGroup) {
				toPrint += " This Group: " + Integer.toString(length + 1);
			}
			System.out.println(toPrint);
			String s = iStream.readLine();
			boolean correctInt = false;
			while (!correctInt) {
				try {
					int i = Integer.parseInt(s);
					if (i == length) {
						System.out.println("Enter a name:");
						s = iStream.readLine();
						Group g = new Group(s, topGroup);
						return g;
					} else if (i < length && i >= 0) {
						topGroup = topGroup.getChildGroup(i);
						correctInt = true;
					} else if (i == length + 1 && !isTopGroup) {
						return topGroup;
					} else {
						System.out.println("Enter a number between 0 and "
								+ Integer.toString(length));
								s = iStream.readLine();
					}
				} catch (NumberFormatException e) {
					System.out.println("Please input a number");
					s = iStream.readLine();
				}
			}
			isTopGroup = false;
		}
		return null;

	}
}
