package com.company;

import java.util.concurrent.ThreadLocalRandom;

public class Seat {
    public final String Id;
    public Seat() {

        int a= ThreadLocalRandom.current().nextInt(0, 10000 + 1);
        Integer b= new Integer(a);
        this.Id=b.toString();
        System.out.println("New seat created: "+this.Id);
    }
}
