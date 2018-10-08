package entities;

import java.util.ArrayList;

/**
 * Created by Andrei on 17/01/2017.
 */
public class Route {
    ArrayList<Destination> destinations=new ArrayList<Destination>();
    public Route()
    {
    }
    public void addDestination(Destination d)
    {
        this.destinations.add(d);
        d.index=destinations.indexOf(d)+1;
    }
}
