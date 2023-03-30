package dat3.cinema.api;

import dat3.cinema.dto.ReservationRequest;
import dat3.cinema.dto.ReservationResponse;
import dat3.cinema.service.ReservationService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("api/reservations")
@CrossOrigin
public class ReservationController {

  ReservationService reservationService;

  public ReservationController(ReservationService reservationService) {
    this.reservationService = reservationService;
  }

  @PreAuthorize("hasAuthority('USER')")
  @PostMapping
  public ReservationResponse makeReservation(@RequestBody ReservationRequest body, Principal principal) {
    return reservationService.makeReservation(principal.getName(), body.getShowId(), body.getSeats());
  }
}

