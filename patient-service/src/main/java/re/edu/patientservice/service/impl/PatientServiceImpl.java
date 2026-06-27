package re.edu.patientservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import re.edu.patientservice.dto.request.PatientRequestDTO;
import re.edu.patientservice.dto.response.PatientResponseDTO;
import re.edu.patientservice.entity.Patient;
import re.edu.patientservice.exception.ResourceNotFoundException;
import re.edu.patientservice.repository.IPatientRepository;
import re.edu.patientservice.service.IPatientService;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements IPatientService {
    private final IPatientRepository patientRepository;

    @Override
    public PatientResponseDTO createPatient(PatientRequestDTO dto) {
        // Map DTO sang Entity
        Patient patient = new Patient();
        patient.setFullName(dto.getFullName());
        patient.setDateOfBirth(dto.getDateOfBirth());
        patient.setGender(dto.getGender());
        patient.setPhoneNumber(dto.getPhoneNumber());
        patient.setAddress(dto.getAddress());
        patient.setMedicalHistory(dto.getMedicalHistory());

        // Lưu vào DB
        Patient savedPatient = patientRepository.save(patient);

        // Map Entity sang DTO trả về
        return PatientResponseDTO.builder()
                .id(savedPatient.getId())
                .fullName(savedPatient.getFullName())
                .dateOfBirth(savedPatient.getDateOfBirth())
                .gender(savedPatient.getGender())
                .phoneNumber(savedPatient.getPhoneNumber())
                .address(savedPatient.getAddress())
                .medicalHistory(savedPatient.getMedicalHistory())
                .build();
    }

    @Override
    public PatientResponseDTO getPatientById(Long id) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy bệnh nhân với id: " + id));

        return PatientResponseDTO.builder()
                .id(patient.getId())
                .fullName(patient.getFullName())
                .dateOfBirth(patient.getDateOfBirth())
                .gender(patient.getGender())
                .phoneNumber(patient.getPhoneNumber())
                .address(patient.getAddress())
                .medicalHistory(patient.getMedicalHistory())
                .build();
    }
}
