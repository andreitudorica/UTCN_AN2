package entities;

import java.util.UUID;

/**
 * Created by Andrei on 17/01/2017.
 */
public class Seat {
    String ID = new String();
    public Seat()
    {
        this.ID= UUID.randomUUID().toString();
    }
}
