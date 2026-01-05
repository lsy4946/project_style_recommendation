package faceweb.demo.Service;

import faceweb.demo.Entity.ReservationEntity;
import faceweb.demo.Entity.UserEntity;
import faceweb.demo.Repository.ReservationRepository;
import faceweb.demo.Repository.StyleRepository;
import faceweb.demo.Repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final DateTimeFormatter formatter;
    private final StyleRepository styleRepository;

    public ReservationService(ReservationRepository reservationRepository, UserRepository userRepository, StyleRepository styleRepository) {
        this.reservationRepository = reservationRepository;
        this.userRepository = userRepository;
        this.styleRepository = styleRepository;
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    }

    public List<ReservationEntity> findAll(){
        return reservationRepository.findAll();
    }

    public void saveReservation(LocalDate date, LocalTime time, long styleId, UserEntity user, int professional_id){
        ReservationEntity reservationEntity = new ReservationEntity();
        reservationEntity.setUser(user);
        reservationEntity.setProfessional(userRepository.findByIdNum(professional_id));
        reservationEntity.setReservedTime(LocalDateTime.of(date, time).format(formatter));
        reservationEntity.setTimeStamp(LocalDateTime.now().format(formatter));
        reservationEntity.setStatus(ReservationEntity.STATUS.NOT_RECEIVED);
        if(styleId==0)
            reservationEntity.setTarget_style(null);
        else
            reservationEntity.setTarget_style(styleRepository.findByStyleid(styleId));
        reservationRepository.save(reservationEntity);
    }
}
