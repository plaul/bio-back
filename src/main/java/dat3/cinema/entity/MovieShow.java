package dat3.cinema.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;


  @Entity
  @Getter
  @Setter
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  public class MovieShow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    LocalDate date;
    LocalTime startTime;
    LocalTime endTime;

    @ManyToOne
    @JoinColumn(name = "movie_id")
    private Movie movie;

    @ManyToOne
    @JoinColumn(name = "movie_hall_id")
    private CinemaHall cinemaHall;


    @OneToMany(mappedBy = "movieShow", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    List<ShowSeat> showSeats = new ArrayList<>();

    public void addShowSeat(ShowSeat seat){
      showSeats.add(seat);
      seat.setMovieShow(this);
    }
    public void setupShowSeats(double price){
      if(this.showSeats==null){
        this.showSeats = new ArrayList<>();
      }
      for(Seat seat : cinemaHall.seats){
        showSeats.add(new ShowSeat(price,seat,this));
      }
    }

    @OneToMany(mappedBy = "movieShow")
    List<Reservation> reservations = new ArrayList<>();

    public void addReservation(Reservation res){
      reservations.add(res);
      res.setMovieShow(this);
    }



    public MovieShow(LocalDate date, LocalTime startTime, LocalTime endTime) {
      this.date = date;
      this.startTime = startTime;
      this.endTime = endTime;
    }
  }