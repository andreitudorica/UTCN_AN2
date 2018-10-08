package hospital.entities;

import hospital.comparators.DoctorComparator;
import hospital.comparators.PatientComparator;

import java.util.*;

/**
 * Created by Andrei on 13/01/2017.
 */
public class Hospital {
    Set<Patient> patients;
    Set<Doctor> doctors;

    public Hospital ()
    {
        patients = new HashSet<Patient>();
        doctors = new HashSet<Doctor>();
    }

    public void addPatient(Patient patient){
        patients.add(patient);
    }
    public void addDoctor(Doctor doctor)
    {
        doctors.add(doctor);
    }
    public void heal()
    {
        for(Doctor d : doctors)
            for(Patient p : patients)
                d.curePatient(p);
    }

    public void showPatientsSortedByDisease()
    {
        List<Patient> sortedPatients = new ArrayList<Patient>();
        sortedPatients.addAll(patients);

        Collections.sort(sortedPatients, new PatientComparator());
        for (Patient p : sortedPatients) {
            System.out.println(p.Name + " " + p.getDiseases().size());
        }

    }
    public void showDoctorsSortedByNumberofDiseasesCured() {
        List<Doctor> sortedDoctors = new ArrayList<Doctor>();
        sortedDoctors.addAll(doctors);

        Collections.sort(sortedDoctors, new DoctorComparator());
        for (Doctor p : sortedDoctors) {
            System.out.println(p.Name + " " + p.getNumberOfDiseasesCured());
        }
    }

}
