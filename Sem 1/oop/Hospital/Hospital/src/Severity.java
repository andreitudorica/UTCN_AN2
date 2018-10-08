
public class Severity {
	String severityLevel;
	
	public Severity(){
		int i=(int)(Math.random()*3 - 0.5);
		switch(i){
		case 0: this.severityLevel="LOW"; break;
		case 1: this.severityLevel="MEDIUM"; break;
		case 2: this.severityLevel="HIGH"; break;
		default: this.severityLevel="ERROR!"; break;
		}
	}
	
	public String getSeverity(){
		return this.severityLevel;
	}

}
