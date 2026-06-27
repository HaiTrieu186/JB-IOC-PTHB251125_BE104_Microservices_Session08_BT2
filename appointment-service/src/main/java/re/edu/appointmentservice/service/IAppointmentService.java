package re.edu.appointmentservice.service;

import re.edu.appointmentservice.dto.request.AppointmentRequestDTO;
import re.edu.appointmentservice.dto.response.AppointmentResponseDTO;

public interface IAppointmentService {
    AppointmentResponseDTO createAppointment(AppointmentRequestDTO dto);
}
