package contacts.parser;

public class OuterGroup implements Pagestuff{
	
	String name;
	Groupstuff innerStuff;
	Pagestuff outerStuff;
	public OuterGroup(String name,Groupstuff innerStuff, Pagestuff outerStuff) {
		this.name = name;
		this.innerStuff = innerStuff;
		this.outerStuff = outerStuff;
	}
	
}
