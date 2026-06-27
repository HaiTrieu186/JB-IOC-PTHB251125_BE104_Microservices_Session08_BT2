package re.edu.appointmentservice.service.impl;


import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import re.edu.appointmentservice.dto.request.AppointmentRequestDTO;
import re.edu.appointmentservice.dto.response.ApiResponse;
import re.edu.appointmentservice.dto.response.AppointmentResponseDTO;
import re.edu.appointmentservice.dto.response.DoctorDTO;
import re.edu.appointmentservice.dto.response.PatientDTO;
import re.edu.appointmentservice.entity.Appointment;
import re.edu.appointmentservice.exception.ResourceNotFoundException;
import re.edu.appointmentservice.exception.ServiceUnavailableException;
import re.edu.appointmentservice.repository.IAppointmentRepository;
import re.edu.appointmentservice.service.IAppointmentService;

@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements IAppointmentService {
    private final IAppointmentRepository appointmentRepository;
    private final RestTemplate restTemplate;

    @Override
    @CircuitBreaker(name = "doctorServiceCB", fallbackMethod = "getDoctorFallback")
    public AppointmentResponseDTO createAppointment(AppointmentRequestDTO dto) {
        // 1. GỌI SANG PATIENT-SERVICE
        String patientUrl = "http://patient-service/api/v1/patients/" + dto.getPatientId();
        try {
            ResponseEntity<ApiResponse<PatientDTO>> patientResponse = restTemplate.exchange(
                    patientUrl,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<ApiResponse<PatientDTO>>() {}
            );
            //  PatientDTO patient = patientResponse.getBody().getData();
        } catch (HttpClientErrorException.NotFound e) {
            throw new ResourceNotFoundException("Không tìm thấy Bệnh nhân với ID: " + dto.getPatientId());
        }

        // 2. GỌI SANG DOCTOR-SERVICE
        String doctorUrl = "http://doctor-service/api/v1/doctors/" + dto.getDoctorId();
        try {
            ResponseEntity<ApiResponse<DoctorDTO>> doctorResponse = restTemplate.exchange(
                    doctorUrl,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<ApiResponse<DoctorDTO>>() {}
            );
            // DoctorDTO doctor = doctorResponse.getBody().getData();
        } catch (HttpClientErrorException.NotFound e) {
            throw new ResourceNotFoundException("Không tìm thấy Bác sĩ với ID: " + dto.getDoctorId());
        }

        // 3. TẠO LỊCH KHÁM NẾU CẢ 2 ĐỀU TỒN TẠI
        Appointment appointment = Appointment.builder()
                .patientId(dto.getPatientId())
                .doctorId(dto.getDoctorId())
                .appointmentDate(dto.getAppointmentDate())
                .reason(dto.getReason())
                .status("PENDING")
                .build();

        Appointment savedAppointment = appointmentRepository.save(appointment);

        return AppointmentResponseDTO.builder()
                .id(savedAppointment.getId())
                .patientId(savedAppointment.getPatientId())
                .doctorId(savedAppointment.getDoctorId())
                .appointmentDate(savedAppointment.getAppointmentDate())
                .reason(savedAppointment.getReason())
                .status(savedAppointment.getStatus())
                .build();
    }

    public AppointmentResponseDTO getDoctorFallback(AppointmentRequestDTO dto, Exception e){
        System.err.println("-> [Circuit Breaker] Gọi sang Doctor-Service thất bại. Lỗi gốc: " + e.getMessage());

        //Ném  lỗi
        throw new ServiceUnavailableException("Hiện tại không thể kiểm tra thông tin bác sĩ, vui lòng thử lại sau vài giây");
    }
}
