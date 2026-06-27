package re.edu.doctorservice.service;

import re.edu.doctorservice.dto.response.DoctorResponseDTO;
import java.util.List;

public interface IDoctorService {
    List<DoctorResponseDTO> getAllDoctors();
    DoctorResponseDTO getDoctorById(Long id);
}