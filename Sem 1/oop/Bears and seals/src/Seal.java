
public class Seal {

	
	private int x,y,breath;
	private boolean dead=false;
	public Seal(){
		this.x=0;
		this.y=0;
	}
	
	public int getSealX(){
		return x;
	}
	
	public int getSealY(){
		return y;
	}
	
	public void setSealXY(int x,int y){
		this.x=x;
		this.y=y;
	}
	
	public boolean Dead(){
		return dead;
	}
	public void setDead(){
		dead=true;
	}
	public int getBreath(){
		return breath;
	}
	public void incBreath(){
		 breath++;
	}
	
	public void setBreath(){
		breath=0;
	}
}
