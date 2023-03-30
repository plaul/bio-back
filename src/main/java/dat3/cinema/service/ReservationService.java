package dat3.cinema.service;

import dat3.cinema.dto.ReservationResponse;
import dat3.cinema.entity.*;
import dat3.cinema.repository.MovieShowRepository;
import dat3.cinema.repository.ReservationRepository;
import dat3.cinema.repository.ShowSeatRepository;
import dat3.security.repository.UserWithRolesRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ReservationService {

  ReservationRepository reservationRepository;
  ShowSeatRepository showSeatRepository;
  MovieShowRepository movieShowRepository;
  UserWithRolesRepository userWithRolesRepository;


  public ReservationService(ReservationRepository reservationRepository, ShowSeatRepository showSeatRepository, MovieShowRepository movieShowRepository, UserWithRolesRepository userWithRolesRepository) {
    this.reservationRepository = reservationRepository;
    this.showSeatRepository = showSeatRepository;
    this.movieShowRepository = movieShowRepository;
    this.userWithRolesRepository = userWithRolesRepository;
  }

  public ReservationResponse makeReservation(String userName, int showId, List<Integer> seats){
    MovieCustomer user = (MovieCustomer) userWithRolesRepository.findById(userName).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    MovieShow show = movieShowRepository.findById(showId).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Show not found"));
    Reservation reservation = new Reservation();
    show.addReservation(reservation);
    user.addReservation(reservation);
    for(int id: seats){
      ShowSeat seat = showSeatRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Seat Not Found"));
      if(seat.getReservation() !=null){
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "One or more requested seats are already taken: "+seat.getId());
      }
      reservation.addSeat(seat);
    }
    reservationRepository.save(reservation);
    return new ReservationResponse(reservation);
  }

}