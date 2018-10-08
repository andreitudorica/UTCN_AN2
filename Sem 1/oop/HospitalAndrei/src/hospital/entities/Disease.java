package hospital.entities;

import java.util.Random;

/**
 * Created by Andrei on 13/01/2017.
 */
public class Disease {
    String Name;
    Severity severity;
    public Disease(String name, Severity sev)
    {
        this.Name=name;
        this.severity=sev;
    }

    public boolean cure()
    {
        int chance = new Random().nextInt(100);
        if(this.severity.equals(Severity.LOW) && chance>=25)
            return true;
        else if(this.severity.equals(Severity.MEDIUM) && chance>=50)
            return true;
        else if(this.severity.equals(Severity.HIGH) && chance>=75)
            return true;
        return false;
    }


}
