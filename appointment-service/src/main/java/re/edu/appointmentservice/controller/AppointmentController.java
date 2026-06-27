package re.edu.appointmentservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import re.edu.appointmentservice.dto.request.AppointmentRequestDTO;
import re.edu.appointmentservice.dto.response.ApiResponse;
import re.edu.appointmentservice.dto.response.AppointmentResponseDTO;
import re.edu.appointmentservice.service.IAppointmentService;

@RestController
@RequestMapping("/api/v1/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    private final IAppointmentService appointmentService;

    @PostMapping
    public ResponseEntity<ApiResponse<AppointmentResponseDTO>> createAppointment(@RequestBody AppointmentRequestDTO requestDTO) {
        AppointmentResponseDTO data = appointmentService.createAppointment(requestDTO);

        ApiResponse<AppointmentResponseDTO> response = ApiResponse.success("Tạo lịch khám thành công", data);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}