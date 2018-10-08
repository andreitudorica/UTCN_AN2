package entities;

import comparators.ShuttleComparator;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * Created by Andrei on 17/01/2017.
 */
public class Station {
    public ArrayList<Shuttle> shuttles=new ArrayList<Shuttle>();
    public Station(){

    }
    public void addShuttle(Shuttle s)
    {
        this.shuttles.add(s);
    }
    public void Summary()
    {
        System.out.println("all the shuttles in the station:");
        for(int i=0;i<shuttles.size();i++)
            System.out.println(shuttles.get(i).Name + " " + shuttles.get(i).state.toString() + " " + shuttles.get(i).getProfit());
    }

    public void SortedSummary()
    {
        System.out.println("SORTED shuttles in the station:");
        ArrayList<Shuttle> sorted= new ArrayList<Shuttle>();
        sorted.addAll(shuttles);
        Collections.sort(sorted, new ShuttleComparator());
        for(int i=0;i<sorted.size();i++)
            System.out.println(sorted.get(i).Name + " " + sorted.get(i).state.toString() + " " + sorted.get(i).getProfit());
    }

    public void makeShuttle(Shuttle s, ShuttleState ss)
    {
        s.state=ss;
        System.out.println("shuttle  " + s.Name + " is now " + ss.toString());
    }
    public void ShowShuttle(Shuttle s)
    {
        System.out.println("The requested shuttle:");
        System.out.println(s.Name + " " + s.state.toString() + " " + s.getProfit());
    }
    public void showPassengers(Shuttle s)
    {
        System.out.println("the destinations of every passenger on shuttle " + s.Name + ":");
        for(int i=0;i<s.passengers.size();i++)
            System.out.println("    passenger:  " + s.passengers.get(i).ID + "    goes to  " + s.passengers.get(i).destination.ID );
    }
}
