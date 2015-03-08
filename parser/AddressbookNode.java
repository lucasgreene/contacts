package contacts.parser;

import contacts.addressbook.AddressBook;

public class AddressbookNode implements Xmlpage{

	Pagestuff info;
	public AddressbookNode(Pagestuff info) {
		this.info = info;
	}
	
	public AddressBook toAddressbook() {
		return new AddressBook(); 
	}
	
	@Override
	public String toString() {
		return "Addresbook: \n" + info.toString();
	}
}
