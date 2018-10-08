package entities;

import java.util.UUID;

/**
 * Created by Andrei on 17/01/2017.
 */
public class Destination {
    String ID=new String();
    int index=0;
    public Destination()
    {
        this.ID=UUID.randomUUID().toString();
    }

}
