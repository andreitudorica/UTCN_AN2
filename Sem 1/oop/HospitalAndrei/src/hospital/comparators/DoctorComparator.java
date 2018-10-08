package hospital.comparators;

import hospital.entities.Doctor;

import java.util.Comparator;

/**
 * Created by Andrei on 13/01/2017.
 */
public class DoctorComparator implements Comparator<Doctor> {
    @Override
    public int compare(Doctor d1, Doctor d2) {
        return Integer.compare(d1.getNumberOfDiseasesCured(), d2.getNumberOfDiseasesCured());
    }

}
