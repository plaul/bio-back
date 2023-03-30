package dat3.cinema.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import dat3.cinema.entity.Reservation;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class ReservationResponse {
  int reservationId;
  String userName;
  @JsonFormat(pattern = "dd-MM-yyyy",shape = JsonFormat.Shape.STRING)
  LocalDate date;
  @JsonFormat(pattern = "HH:mm",shape = JsonFormat.Shape.STRING)
  LocalTime startTime;
  String movieTitle;
  String movieHall;
  List<ShowSeatDTO> seats;

  public ReservationResponse(Reservation res) {
    this.reservationId = res.getId();
    this.userName = res.getMovieCustomer().getUsername();
    this.date = res.getMovieShow().getDate();
    this.startTime = res.getMovieShow().getStartTime();
    this.movieTitle = res.getMovieShow().getMovie().getTitle();
    this.movieHall = res.getMovieShow().getCinemaHall().getHallName();
    this.seats = res.getReservedSeats().stream().map(ShowSeatDTO::getShowSeatDtoWithoutFree).collect(Collectors.toList());
  }
}
