
public class Doctor {
	int diseasesCured;
	Person doctor;
	
	public Doctor (int ID, String Name){
		this.diseasesCured=(int)(Math.random()*2000);
		this.doctor= new Person (ID, Name);
	}
	
	public String toString(){
		return "Doctor " + this.doctor.getName() + " with ID " + this.doctor.getID() + " having cured " + this.diseasesCured + " diseases.";
	}
	
	public int getCured(){
		return this.diseasesCured;
	}
	
	
	//to be modified
	public boolean cure(int ID){
		return true;
	}

}
