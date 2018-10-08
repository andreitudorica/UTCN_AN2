package entities;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Andrei on 13/01/2017.
 * ShipBay:
 Can have a variable number of unique ships in it at a certain point in time
 Has the capabilities of receiving, departing a ship and checking whether or not a ship is in the bay.

 */
public class Shipbay {
    public ArrayList<Ship> ships=new ArrayList<Ship>();
    public Shipbay()
    {}
    public void receiveShip(Ship s)
    {
        ships.add(s);
    }
    public void departShip(Ship s)
    {
        ships.remove(s);
    }
    public boolean checkShip(Ship s)
    {

        if(ships.lastIndexOf(s)!= -1)
            return true;
        return false;
    }
    public void printShipsByProfit()
    {
        boolean p=true;
        while(p)
        {
            p=false;
            for(int i=0;i<ships.size()-1;i++)
            {
                if(ships.get(i).getProfit()>ships.get(i+1).getProfit())
                {
                    p=true;
                    Collections.swap(ships,i,i+1);
                }
            }
        }
        System.out.println("The ships in the bay sorted by price are: ");
        for(int i=0;i<ships.size();i++)
        {
            System.out.println("ID:" + ships.get(i).name + "    Number of compartments:" + ships.get(i).getNumberOfCompartments()+ "   Profit:" + ships.get(i).getProfit());
            ships.get(i).printComaptmentsByProfit();
        }
    }
}
