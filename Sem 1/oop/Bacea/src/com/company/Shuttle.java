package com.company;


import java.util.ArrayList;
import java.util.List;

public class Shuttle {

    private String name;
    private Status status;
    private int totalSeatsCount;
    private int seatsCount;
    private List<Seat> seats;
    private Route route;

    public double getProfit() {
        if(this.status==Status.DOCKED)
            return 0;
        if(this.status==Status.DEPARTED)
            return profit-this.seatsCount*0.2;
        return 0.05*totalSeatsCount;
    }

    private double profit;

    public int getSeatsCount() {
        return seatsCount;
    }

    public void setSeatsCount(int seatsCount) {
        this.seatsCount = seatsCount;
    }

    public Route getRoute() {
        return route;
    }

    public Shuttle(String name,int sc,Route r) {
        this.name = name;
        this.totalSeatsCount=this.seatsCount=sc;
        this.route=r;
        this.seats = new ArrayList<Seat>();
        this.status=Status.DOCKED;
    }


    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addSeat(Seat s,double price)
    {
        this.seats.add(s);
        this.profit+=price;
        System.out.println("A ticket was bought on shuttle "+this.name+" for "+price+" euro (seat:" + s.Id+")");
    }

}
