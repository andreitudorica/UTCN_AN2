package entities;

/**
 * Created by Andrei on 13/01/2017.
 * CargoItem:
 Is a carriable
 Has a name (String) and a profit (int) and both are final → immutable object!

 */
public class CargoItem extends Carriable{
    public String name;
    public CargoItem(String name,int profit)
    {
        this.name=name;
        this.price=profit;
    }
}
