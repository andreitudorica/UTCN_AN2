package entities;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Andrei on 13/01/2017.
 * Ship:
 Can have a variable number of unique linked compartments
 Has a name (String)
 Can contain two types of compartments: passenger or cargo
 A ship cannot have both passenger and cargo compartments!
 Can compute its profit based on the total profit of all its compartments summed together

 */
public class Ship {
    public String name;
    ArrayList<Compartment> compartments=new ArrayList<Compartment>();
    int noOfCompartments=0;
    public Ship(String name)
    {
        this.name=name;
    }

    public void addCompartment(Compartment c)
    {
        compartments.add(c);

        this.noOfCompartments++;
    }

    public int getProfit()
    {
        int s=0;
        for(int i=0;i<this.noOfCompartments;i++)
            s+=compartments.get(i).getCompartmentProfit();
        return s;
    }
    public void printComaptmentsByProfit()
    {
        boolean p=true;
        while(p)
        {
            p=false;
            for(int i=0;i<noOfCompartments-1;i++)
            {
                if(compartments.get(i).getCompartmentProfit()>compartments.get(i+1).getCompartmentProfit())
                {
                    p=true;
                    Collections.swap(compartments,i,i+1);
                }
            }
        }
        System.out.println("The comaprtments on ship " + this.name + "are:");
        for(int i=0;i<noOfCompartments;i++)
            System.out.println("ID:" + compartments.get(i).ID + "   Profit" + compartments.get(i).getCompartmentProfit());
    }
    public int getNumberOfCompartments()
    {
        return this.compartments.size();
    }
}
