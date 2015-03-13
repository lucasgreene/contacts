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
		
		if (parentGroup != null) {
			parentGroup.addChildGroup(this);
		}

		this.childGroups = new LinkedList<Group>();
		this.contacts = new LinkedList<Person>();
	}

	public void addPerson(Person person) {
		contacts.addLast(person);
		for (Group i = parentGroup; i != null; i = i.parentGroup)
			i.contacts.addLast(person);
	}

	public void removePerson(Person person) {
		contacts.remove(person);
		for (Group i = parentGroup; i != null; i = i.parentGroup)
			i.contacts.remove(person);
	}

	public LinkedList<Group> getChildGroups() {
		return childGroups;
	}

	public Group getChildGroup(int i) {
		return childGroups.get(i);
	}

	public void addChildGroup(Group g) {
		
		if (!childGroups.contains(g)) {
			childGroups.addFirst(g);
		}
		
	}

	@Override
	public String toString() {
		StringBuilder string = new StringBuilder();

		for (Person person : contacts) {
			string.append(person.name + "\n");
		}

		return string.toString();
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Group)) {
			return false;
		} else {
			Group that = (Group) o;
			return (this.childGroups.equals(that.childGroups)
					&& this.contacts.equals(that.contacts)
					&& this.name.equals(that.name) && 
					this.parentGroup == that.parentGroup);
		}
	}

}
