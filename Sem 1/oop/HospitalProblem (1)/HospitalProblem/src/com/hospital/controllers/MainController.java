package com.hospital.controllers;

import com.hospital.entities.Hospital;
import com.hospital.exceptions.InvalidDataException;
import com.hospital.factories.HospitalFactory;

public class MainController {

	private static final int NUMBER_OF_DOCTORS = 7;
	private static final int NUMBER_OF_PATIENTS = 50;

	public static void main(String[] args) throws InvalidDataException {
		Hospital hospital = new Hospital();
		HospitalFactory hospitalFactory = new HospitalFactory();

		for (int i = 0; i < NUMBER_OF_DOCTORS; i++) {
			hospital.addDoctor(hospitalFactory.createDoctor());
		}

		for (int i = 0; i < NUMBER_OF_PATIENTS; i++) {
			hospital.addPatient(hospitalFactory.createPatient());
		}

		hospital.showStatistics();

		hospital.startHealing();

		hospital.showDoctorsSortedByNumberofDiseasesCured();
		hospital.showPatientsSortedByNumberOfDiseases();

		hospital.showStatistics();

	}
}
