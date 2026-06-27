package re.edu.doctorservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import re.edu.doctorservice.dto.response.ApiResponse;
import re.edu.doctorservice.dto.response.DoctorResponseDTO;
import re.edu.doctorservice.service.IDoctorService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/doctors")
@RequiredArgsConstructor
public class DoctorController {

    private final IDoctorService doctorService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<DoctorResponseDTO>>> getAllDoctors() {
        List<DoctorResponseDTO> data = doctorService.getAllDoctors();

        ApiResponse<List<DoctorResponseDTO>> response = ApiResponse.success("Lấy danh sách bác sĩ thành công", data);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<DoctorResponseDTO>> getDoctorById(@PathVariable Long id) {
        DoctorResponseDTO d = doctorService.getDoctorById(id);

        ApiResponse<DoctorResponseDTO> response = ApiResponse.success("Lấy bác sĩ thành công", d);

        return ResponseEntity.ok(response);
    }
}