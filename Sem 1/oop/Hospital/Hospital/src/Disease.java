
public class Disease {
	String diseaseName;
	Severity diseaseSeverity;
	
	public Disease(String Name){
		this.diseaseName=Name;
		this.diseaseSeverity= new Severity();
	}
	
	public String toString(){
		return "Disease name is " + this.diseaseName + " and has a " + this.diseaseSeverity.getSeverity() + " severity.";
	}
	
	public boolean cure(){
		int chance=(int)(Math.random()*100);
		if (this.diseaseSeverity.getSeverity().equals("LOW") && (chance<75)) return true;
		if (this.diseaseSeverity.getSeverity().equals("MEDIUM") && (chance<50)) return true;
		if (this.diseaseSeverity.getSeverity().equals("HIGH") && (chance<25)) return true;
		return false;
	}

}
