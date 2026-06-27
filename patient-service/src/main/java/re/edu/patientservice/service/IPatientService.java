package re.edu.patientservice.service;

import re.edu.patientservice.dto.request.PatientRequestDTO;
import re.edu.patientservice.dto.response.PatientResponseDTO;

public interface IPatientService {
    PatientResponseDTO createPatient(PatientRequestDTO dto);
    PatientResponseDTO getPatientById(Long id);
}