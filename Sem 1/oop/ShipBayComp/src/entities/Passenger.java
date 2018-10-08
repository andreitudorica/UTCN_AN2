package entities;

/**
 * Created by Andrei on 13/01/2017.
 */
/**
 * Passenger:
 Is a carriable
 Has a name (String) which is final → immutable object!

 * **/
public class Passenger extends Carriable {
    public final String Name;
    public int Value =100;
    public Passenger(String name)
    {
        this.Name=name;
        this.price=100;
    }
}
