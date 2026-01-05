package faceweb.demo.Controller;

import faceweb.demo.Details.CustomUserDetails;
import faceweb.demo.Entity.ProfessionalProfileEntity;
import faceweb.demo.Entity.ReservationEntity;
import faceweb.demo.Entity.StyleEntity;
import faceweb.demo.Entity.UserEntity;
import faceweb.demo.Service.ProfileService;
import faceweb.demo.Service.ReservationService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ReservationController {

    private final ReservationService reservationService;
    private final ProfileService profileService;

    public ReservationController(ReservationService reservationService, ProfileService profileService) {
        this.reservationService = reservationService;
        this.profileService = profileService;
    }

    @GetMapping("/user/reserve")
    public String reservationPage(Model model){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        UserEntity currentUser = new UserEntity();
        if (principal instanceof CustomUserDetails currentUserDetails){
            currentUser = currentUserDetails.getUserEntity();

            model.addAttribute("username", currentUser.getUsername());
            model.addAttribute("userRole", currentUser.getRole());
        }
        List<ProfessionalProfileEntity> profileList = new ArrayList<>();
        model.addAttribute("profileList", profileList);

        return "/user/reservation";
    }

    @GetMapping("/user/reserve/{profile_id}")
    public String finalReservationPage(Model model, @PathVariable("profile_id") long profile_id){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        UserEntity currentUser = new UserEntity();
        if (principal instanceof CustomUserDetails currentUserDetails){
            currentUser = currentUserDetails.getUserEntity();

            model.addAttribute("username", currentUser.getUsername());
            model.addAttribute("userRole", currentUser.getRole());

            List<StyleEntity> styleList = currentUser.getStyleList();
            model.addAttribute("styleList", styleList);
        }
        ProfessionalProfileEntity profile = profileService.findByID(profile_id);
        model.addAttribute("profile", profile);

        return "/user/reservation-info";
    }

    @PostMapping("/user/reserve/{professional_id}")
    public String reservationProcess(@PathVariable("professional_id") int professional_id,
                                     @RequestParam("date") @DateTimeFormat(iso=DateTimeFormat.ISO.DATE) LocalDate date,
                                     @RequestParam("time") @DateTimeFormat(iso=DateTimeFormat.ISO.TIME) LocalTime time,
                                     long styleId){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        UserEntity currentUser = new UserEntity();
        if (principal instanceof CustomUserDetails currentUserDetails){
            currentUser = currentUserDetails.getUserEntity();

            List<StyleEntity> styleList = currentUser.getStyleList();
        }

        reservationService.saveReservation(date, time, styleId, currentUser, professional_id);


        return "redirect:/user/reserve";
    }

    @GetMapping("/mypage/reservations/to-me")
    public String professionalReservationPage(Model model){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        UserEntity currentUser = new UserEntity();
        if (principal instanceof CustomUserDetails currentUserDetails){
            currentUser = currentUserDetails.getUserEntity();

            model.addAttribute("username", currentUser.getUsername());
            model.addAttribute("userRole", currentUser.getRole());

            List<ReservationEntity> reservationList = currentUser.getReservationList_professional();
            model.addAttribute("reservationList", reservationList);
        }

        return "/user/mypage/mypage-professional-reservation";
    }
}
