package hospital.entities;
import hospital.exceptions.InvalidDataException;

import java.util.Random;
import java.util.Set;

/**
 * Created by Andrei on 13/01/2017.
 */
public class Doctor extends Person
{
    private int numberOfDiseasesCured;
    public Doctor(int ID, String name) throws InvalidDataException
    {
        super(ID, name);
        this.numberOfDiseasesCured = 0;
    }
    public void curePatient(Patient patient)
    {
        Set<Disease> diseases=patient.getDiseases();
        int noOfDiseasesToTry=new Random().nextInt(diseases.size()+1);
        for(int i=1;i<=noOfDiseasesToTry;i++)
        {
            if(patient.cureDIsease(diseases.iterator().next()))
                numberOfDiseasesCured++;
        }
    }
    public int getNumberOfDiseasesCured()
    {
        return numberOfDiseasesCured;
    }
}
