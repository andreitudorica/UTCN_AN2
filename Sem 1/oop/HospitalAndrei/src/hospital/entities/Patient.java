package hospital.entities;

import hospital.exceptions.InvalidDataException;

import java.util.HashSet;
import java.util.Set;
/**
 * Created by Andrei on 13/01/2017.
 */
public class Patient extends Person {
    private Set<Disease> diseases;
    public Patient(int id,String name) throws InvalidDataException
    {
        super(id,name);
        this.diseases=new HashSet<Disease>();
    }
    public void addDisease(Disease disease)
    {
        diseases.add(disease);
    }
    public Set<Disease> getDiseases()
    {
        return diseases;
    }
    public boolean cureDIsease(Disease disease)
    {
        if(diseases.contains(disease))
            if(disease.cure())
            {
                diseases.remove(disease);
                return true;
            }
        return false;
    }
}
