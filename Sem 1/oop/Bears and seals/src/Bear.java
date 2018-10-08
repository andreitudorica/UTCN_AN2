import java.util.Random;


public class Bear
{
	private int x,y,wait;
	Random rand=new Random();
	public Bear(){
		this.x=0;
		this.y=0;
	}
	
	public int getBearX(){
		return x;
	}
	
	public int getBearY(){
		return y;
	}
	
	public void setBearXY(int x,int y){
		this.x=x;
		this.y=y;
	}
	public int BearWait(){
		wait=rand.nextInt(4)+2;
		return wait;
	}
	
}

