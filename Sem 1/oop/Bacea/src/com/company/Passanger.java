package com.company;

public class Passanger {
    private final String id;

    private Ticket ticket;

    private Station station;

    public String getId() {
        return id;
    }

    public Passanger(String id, Station st){
        this.id=id;
        this.station=st;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void buyTicket(Destination dest){

        this.ticket=new Ticket(dest, this.station);

    }
}
