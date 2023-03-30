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
public class ShowSeat {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  private double price;

  @ManyToOne
  @JoinColumn(name = "seat_id")
  private Seat seat;

  @ManyToOne
  @JoinColumn(name = "movie_show_id")
  private MovieShow movieShow;

  @ManyToOne
  @JoinColumn(name = "reservation_id")
  private Reservation reservation;

  public ShowSeat(double price, Seat seat, MovieShow movieShow) {
    this.price = price;
    this.seat = seat;
    this.movieShow = movieShow;
  }
}
