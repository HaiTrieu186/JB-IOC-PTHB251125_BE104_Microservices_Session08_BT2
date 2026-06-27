package re.edu.appointmentservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import re.edu.appointmentservice.entity.Appointment;

@Repository
public interface IAppointmentRepository extends JpaRepository<Appointment, Long> {
}
