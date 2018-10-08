package hospital;

import hospital.entities.*;
import hospital.exceptions.InvalidDataException;

import java.util.Random;
import java.util.UUID;


/**
 * Created by Andrei on 13/01/2017.
 */
public class MainControl {
    public static void main(String[] args) throws InvalidDataException {
        Hospital hospital = new Hospital();
        Severity sev;
        int r = new Random().nextInt(10);
        if (r < 5) {
            sev = Severity.LOW;
        } else if (r < 9) {
            sev = Severity.MEDIUM;
        } else {
            sev = Severity.HIGH;
        }
        Patient p = new Patient(1000000, "Ändrei Tudorica");
        int randomNumberOfDiseases = new Random().nextInt(20) + 1;
        for (int i = 0; i < randomNumberOfDiseases; i++)
            p.addDisease(new Disease(UUID.randomUUID().toString().substring(0, 10), sev));
        hospital.addPatient(p);
        p = new Patient(1000001, "Mahalean Mihai");
        randomNumberOfDiseases = new Random().nextInt(20) + 1;
        for (int i = 0; i < randomNumberOfDiseases; i++)
            p.addDisease(new Disease(UUID.randomUUID().toString().substring(0, 10), sev));
        hospital.addPatient(p);
        p = new Patient(1000002, "Birle Matei");
        randomNumberOfDiseases = new Random().nextInt(20) + 1;
        for (int i = 0; i < randomNumberOfDiseases; i++)
            p.addDisease(new Disease(UUID.randomUUID().toString().substring(0, 10), sev));
        hospital.addPatient(p);
        p = new Patient(1000003, "Baraian Andrei");
        randomNumberOfDiseases = new Random().nextInt(20) + 1;
        for (int i = 0; i < randomNumberOfDiseases; i++)
            p.addDisease(new Disease(UUID.randomUUID().toString().substring(0, 10), sev));
        hospital.addPatient(p);
        Doctor d = new Doctor(1000004, "doctordoctor");
        hospital.addDoctor(d);
        hospital.showPatientsSortedByDisease();
        d=new Doctor(1000005,"DOCTORDOCTOR");
        hospital.addDoctor(d);
        hospital.heal();
        hospital.showDoctorsSortedByNumberofDiseasesCured();
    }
}