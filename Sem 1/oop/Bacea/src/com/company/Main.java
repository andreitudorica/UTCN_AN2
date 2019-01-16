package com.company;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
	// write your code here
        //create station
        Station ST= new Station();
        //create destinations
        Destination d1=new Destination("d1");
        Destination d2=new Destination("d2");
        Destination d3=new Destination("d3");
        Destination d4=new Destination("d4");
        Destination d5=new Destination("d5");
        Destination d6=new Destination("d6");
        //create routes
        //add destinations to routes
        Route r1 = new Route();
        r1.addDestination(d1);
        r1.addDestination(d2);
        r1.addDestination(d5);
        Route r2 = new Route();
        r2.addDestination(d3);
        r2.addDestination(d4);
        r2.addDestination(d5);
        Route r3 = new Route();
        r3.addDestination(d2);
        r3.addDestination(d5);
        r3.addDestination(d6);
        //create shuttles (and seats)
        Shuttle s1=new Shuttle("shuttle1",10,r1);
        s1.setStatus(Status.DEPARTED);
        Shuttle s2=new Shuttle("shuttle2",25,r2);
        s2.setStatus(Status.ARRIVED);
        Shuttle s3=new Shuttle("shuttle3",70,r3);
        //add shuttles to station
        ST.addShuttle(s1);
        ST.addShuttle(s2);
        ST.addShuttle(s3);
        //create passengers
        Passanger p1=new Passanger("p1",ST);
        Passanger p2=new Passanger("p2",ST);
        Passanger p3=new Passanger("p3",ST);
        Passanger p4=new Passanger("p4",ST);
        Passanger p5=new Passanger("p5",ST);
        Passanger p6=new Passanger("p6",ST);

        //buy tickets for passengers
        p1.buyTicket(d1);
        p2.buyTicket(d2);
        p3.buyTicket(d3);
        p4.buyTicket(d4);
        p5.buyTicket(d5);
        p6.buyTicket(d6);
        ArrayList<Passanger> PList=new ArrayList<Passanger>();


        PList.add(p1);
        PList.add(p2);
        PList.add(p3);
        PList.add(p4);
        PList.add(p5);
        PList.add(p5);




        System.out.println("Exercise 1");

        for(Shuttle s:ST.getShuttles())
        {
            System.out.println("Name: "+s.getName()+" Status: "+s.getStatus().toString()+" Profit: "+s.getProfit());
        }
        for(Passanger t:PList)
        {
            if(t.getTicket().getShuttle().getName()=="shuttle2")
                System.out.println(t.getId()+" "+t.getTicket().getDest().getId());
        }
    }
}
