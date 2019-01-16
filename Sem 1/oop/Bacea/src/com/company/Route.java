package com.company;

import java.util.*;

public class Route {
    private List<Destination> destinations;

    public Route() {
        destinations=new ArrayList<Destination>();
    }

    public void addDestination(Destination dest)
    {
        this.destinations.add(dest);
    }
    public List<Destination> getDstinations() {
        return destinations;
    }

}
