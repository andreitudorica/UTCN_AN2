package entities;

import java.util.UUID;

/**
 * Created by Andrei on 17/01/2017.
 */
public class Passenger {
    String ID=new String();
    Destination destination;
    Ticket ticket;
    public Passenger(Destination d)
    {
        this.ID = UUID.randomUUID().toString();
        this.destination=d;
    }
    public double getPaidPrice()
    {
        return destination.index * 4.5;
    }
}
