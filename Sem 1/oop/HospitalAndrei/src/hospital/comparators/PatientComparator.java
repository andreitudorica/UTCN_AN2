package hospital.comparators;

import hospital.entities.Patient;

import java.util.Comparator;

/**
 * Created by Andrei on 13/01/2017.
 */
public class PatientComparator implements Comparator<Patient> {
    @Override
    public int compare(Patient p1, Patient p2) {
        return Integer
                .compare(p1.getDiseases().size(), p2.getDiseases().size());
    }
}

