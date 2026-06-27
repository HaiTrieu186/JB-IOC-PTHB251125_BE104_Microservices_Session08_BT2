package re.edu.patientservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import re.edu.patientservice.dto.request.PatientRequestDTO;
import re.edu.patientservice.dto.response.ApiResponse;
import re.edu.patientservice.dto.response.PatientResponseDTO;
import re.edu.patientservice.service.IPatientService;

@RestController
@RequestMapping("/api/v1/patients")
@RequiredArgsConstructor
public class PatientController {

    private final IPatientService patientService;

    // API thêm bệnh nhân mới
    @PostMapping
    public ResponseEntity<ApiResponse<PatientResponseDTO>> createPatient(
            @RequestBody PatientRequestDTO requestDTO) {
        PatientResponseDTO data = patientService.createPatient(requestDTO);

        ApiResponse<PatientResponseDTO> response = ApiResponse.success("Thêm mới bệnh nhân thành công", data);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // API lấy thông tin 1 bệnh nhân
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PatientResponseDTO>> getPatientById(@PathVariable Long id) {
        PatientResponseDTO data = patientService.getPatientById(id);

        ApiResponse<PatientResponseDTO> response = ApiResponse.success("Lấy thông tin bệnh nhân thành công", data);

        return ResponseEntity.ok(response);
    }
}