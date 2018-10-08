package hospital.entities;
import hospital.exceptions.InvalidDataException;

/**
 * Created by Andrei on 13/01/2017.
 */
public class Person {
    public int ID;
    public String Name;
    public Person(int id,String name) throws InvalidDataException
    {
        SetID(id);
        SetName(name);
    }

    private void SetID(int id) throws InvalidDataException
    {
        if(id<1000000 || id>9999999)
            throw new InvalidDataException("The id is too short.");
        this.ID=id;
    }
    private void SetName(String name) throws InvalidDataException
    {
        if(name == null || name.length()<6)
            throw new InvalidDataException("Name is too short");
        this.Name=name;
    }
}
