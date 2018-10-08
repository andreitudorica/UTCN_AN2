package entities;


import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

/**
 * Created by Andrei on 13/01/2017.
 * Compartment:
 * Has a final ID (UUID - String) generated at construction time
 * Can be a passenger or cargo type
 * Both passenger and cargo compartments will contain a collection of Carriable objects
 * Can compute its profit based on the total profit of all items (or people) it is carrying
 * Passenger compartments have a fixed price of 100 and thus their profit are calculated based on how many passenger they have inside  (price * number of carriable objects in it)
 * Passenger compartments can hold a maximum of 100 passengers and this restriction has to be imposed when adding a passenger
 * Cargo compartments contain a CargoItem. The profit is calculated by multiplying the number of carriable items inside the compartment times the CargoItems’s profit. (each cargo compartment has only one CargoItem but of course, many carriables)
 * Passenger ticket price is a constant
 * Max number of passengers is also a constant
 */
public class Compartment {
    public String ID = UUID.randomUUID().toString();
    public String type;
    public int noOfCarriables = 0;
    public int profit;
    public CargoItem item;
    public ArrayList<Carriable> carriables=new ArrayList<Carriable>();

    public Compartment(String type,int n) {
        if (!type.equals( "CARGO") ||!type.equals("PASSENGER")) ;
        else
            this.type = type;
        this.noOfCarriables=n;
    }

    public void addCarriable(Carriable c)
    {
        carriables.add(c);
    }

    public int getCompartmentProfit() {
        int s=0;
        for(int i=0;i<noOfCarriables;i++)
            s+=(carriables.get(i)).price;
        return s;
    }
}
