package com.company;

import sun.security.provider.SHA;


import java.util.ArrayList;
import java.util.List;

public class Station {
    private List<Shuttle> shuttles;
    public List<Shuttle> getShuttles() {
        return shuttles;
    }


    public Station() {
        this.shuttles=new ArrayList<Shuttle>();
    }


    public void addShuttle(Shuttle shuttle){
        this.shuttles.add(shuttle);
    }


}
