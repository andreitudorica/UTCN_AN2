package entities;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Andrei on 17/01/2017.
 */
public class Shuttle {
    public String Name=new String();
    public ArrayList<Seat> seats = new ArrayList<Seat>();
    public ArrayList<Ticket> tickets = new ArrayList<Ticket>();
    public ArrayList<Passenger> passengers= new ArrayList<Passenger>();
    public int noOfSeats;
    public int occupiedSeats;
    ShuttleState state;
    public Route route;
    public Shuttle(String name,int seatsNo)
    {
        this.Name=name;
        this.noOfSeats=seatsNo;
        this.state=ShuttleState.DEPARTED;
        for(int i=1;i<=seatsNo;i++)
            seats.add(new Seat());
    }

    public void populateShuttle()
    {
        Random rand = new Random();
        this.occupiedSeats= rand.nextInt(this.noOfSeats);
        for(int i=1;i<=occupiedSeats;i++) {
            Passenger p = new Passenger(route.destinations.get(rand.nextInt(route.destinations.size())));
            passengers.add(p);
            Ticket t= new Ticket(route,p.destination,seats.get(i));
            tickets.add(t);
            p.ticket=t;
        }
    }

    public double getProfit()
    {
        if(this.state == ShuttleState.DOCKED)
            return 0;
        if(this.state == ShuttleState.ARRIVED)
            return 0.5*this.noOfSeats;
        double profit=0;
        for(int i=0;i<tickets.size();i++)
            profit+=tickets.get(i).price;
        return profit-(noOfSeats-occupiedSeats)*0.2;
    }
}
