package contacts.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.LinkedList;

import contacts.addressbook.AddressBook;
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
			boolean friendsExist = true;
			LinkedList<Integer> friendsList = new LinkedList<Integer>();
			for(String friend : friendArr){
				if (book.personExists(friend)){
					friendsList.add(book.getID(friend));
				}
			}
			if(friendsList.size() == friendArr.length){
				Person newPerson = new Person(name, number, book.createNewId(name), friendsList);
				while(groupChosen == false){
					for(Group group : book.getTopGroup().childGroups);
					System.out.println("")
				}
				book.personAdd(person, group);
			} else {
				System.out.println("Invalid friends, please try again");
			}
			
			
			
		}
		
	}

}
