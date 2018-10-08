
public class Person {
	int PersonID;
	String PersonName;
	int ID_LENGTH=String.valueOf(PersonID).length();
	int ID_NAME_LENGTH=PersonName.length();
	
	public Person(int ID, String name){
		this.PersonID=ID;
		this.PersonName=name;
	}
	
	public String getName(){
		return this.PersonName;
	}
	
	public int getID(){
		return this.PersonID;
	}
	
	public String toString(){
		return "Name = " + PersonName + " with ID = " + PersonID;
	}

}
