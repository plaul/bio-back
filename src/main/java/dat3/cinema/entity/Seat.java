package dat3.cinema.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class Seat {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  int seatNumber;
  @Column(name="_row_number")
  int rowNumber;



  @ManyToOne
  @JoinColumn(name = "movie_hall_id")
  private CinemaHall cinemaHall;

  public Seat(int rowNumber, int seatNumber, CinemaHall cinemaHall) {
    this.seatNumber = seatNumber;
    this.rowNumber = rowNumber;
    this.cinemaHall = cinemaHall;
  }


}