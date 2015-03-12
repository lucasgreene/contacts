package contacts.addressbook;

import java.util.LinkedList;

public class Group {
	public String name;
	Group parentGroup;
	LinkedList<Group> childGroups;
	LinkedList<Person> contacts;
	

	public Group(String name, Group parentGroup) {
		this.name = name;
		this.parentGroup = parentGroup;
		this.childGroups = new LinkedList<Group>();
		this.contacts = new LinkedList<Person>();
	}
	public void addPerson(Person person){
		contacts.addLast(person);
		for(Group i = parentGroup; i != null ; i = i.parentGroup)
		i.contacts.addLast(person);
	}
	public void addGroup(String name){
		Group group = new Group(name, this);
		childGroups.addLast(group);
	}
	public void removePerson(Person person){
		contacts.remove(person);
		for(Group i = parentGroup; i != null ; i = i.parentGroup)
		i.contacts.remove(person);
	}
	public LinkedList<Group> getChildGroups(){
		return childGroups;
	}
	@Override
	public String toString(){
		StringBuilder string = new StringBuilder();

		for (Person person : contacts){
			string.append(person.name + "\n");
		}

		return string.toString();
	}

}
