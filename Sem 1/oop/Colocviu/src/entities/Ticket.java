package entities;

import java.util.UUID;

/**
 * Created by Andrei on 17/01/2017.
 */
public class Ticket {
    String ID=new String();
    Route route;
    Destination destination;
    Seat seat;
    Shuttle shuttle;
    double price=4.5;
    public Ticket(Route r,Destination dest, Seat s)
    {
        this.destination=dest;
        this.route=r;
        this.seat=s;
        ID= UUID.randomUUID().toString();
        this.price=this.price*destination.index;
    }

}
