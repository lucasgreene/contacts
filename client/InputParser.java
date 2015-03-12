package contacts.client;


import java.io.BufferedReader;
import java.io.IOException;
import java.util.LinkedList;

import contacts.addressbook.AddressBook;
import contacts.addressbook.Group;
import contacts.addressbook.Person;

public class InputParser {
	
	public static void parse(String s, AddressBook book, BufferedReader iStream) throws IOException {

		if (s.equals("Add")) {
			System.out.println("Name: ");
			String name = iStream.readLine();
			System.out.println("Number: ");
			String number = iStream.readLine();
			System.out.println("Friends: ");
			String friends = iStream.readLine();
			String[] friendArr = friends.split(", ");
			LinkedList<Integer> friendsList = new LinkedList<Integer>();
			for(String friend : friendArr){
				if (book.personExists(friend)){
					friendsList.add(book.getID(friend));
				}
			}
			
			if(friendsList.size() == friendArr.length){
				Group currentGroup = book.getTopGroup();
				LinkedList<Group> children = currentGroup.getChildGroups();
				boolean groupChosen = false;
				while(groupChosen == false){
					int counter = 1;
					for(Group group : children){
						System.out.println("(" + counter + ")" + group.name + " ");
						counter++;
					}
					System.out.println("(" + counter + ")" + " create new group");
					if(currentGroup.name.equals(null)){
						System.out.println("(" + counter + ")" + " current group");
					}
					String input = iStream.readLine();
					int intInput = Integer.parseInt(input);
					if(intInput > 0 && intInput< children.size()){
						currentGroup = children.get(intInput + 1);
					} else if(intInput == (children.size() + 1)){
						System.out.println("Group name: ");
						String groupName = iStream.readLine();
						currentGroup.addGroup(groupName);
						currentGroup = currentGroup.getChildGroups().getLast();
					} else if(intInput == (children.size() + 2) && currentGroup.name != null){
						Person person = new Person(name, number, 
								book.createNewId(name), friendsList, currentGroup);
						currentGroup.addPerson(person);
						groupChosen = true;
					} else {
						System.out.println("Invalid number, please try again");
					}
					
				}
			} else {
				System.out.println("Invalid friends, please try again");
			}
		} else if (s.equals("Remove")){
			System.out.println("name: ");
			String name = iStream.readLine();
			Person person = book.getPerson(name);
			if (person != null){
			book.personRemove(person);
			} else {
				System.out.println("Person does not exist, please try again");
			}
		}
	}
}
	




