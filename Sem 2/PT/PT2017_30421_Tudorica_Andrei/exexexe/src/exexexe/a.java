package exexexe;

import java.util.ArrayList;

public class a implements Cloneable {
	int x,y;
	ArrayList arr;
	public a(int n,int x,int y)
	{
		this.x=x;
		this.y=y;
		this.arr=new ArrayList<Integer>();
		for(int i=0;i<n;i++)
		{
			arr.add(new Integer(i));
		}
	}
	
	public String toString()
	{
		return "x:"+this.x+" y:"+this.y+" arr: "+arr.toString();
	}
	
	public static void main(String[] args) throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		a a1=new a(10,1,2);
		a a2;
		a2=(a) a1.clone();
		System.out.println(a1.toString());
		System.out.println(a2.toString());
		a2.arr.set(5, new Integer(100));
		System.out.println(a1.toString());
		System.out.println(a2.toString());
	}
}
