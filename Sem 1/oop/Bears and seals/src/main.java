import java.util.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


public class main
{
	public static void main(String[] args)
	{
		Board b=new Board();
		Scanner enter=new Scanner(System.in);
		b.build();
		do
		{
			b.print();
			b.MoveSeals();
			if(b.getTotalSteps()==300) break;
			enter.nextLine();
		}while(b.GameOver()==false);
		System.err.println("----------------GAME OVER!!!----------------");
	}
}

