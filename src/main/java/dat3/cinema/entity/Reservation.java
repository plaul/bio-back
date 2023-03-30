package dat3.cinema.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class Reservation {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  int id;

  @OneToMany(mappedBy = "reservation")
  List<ShowSeat> reservedSeats = new ArrayList<>();

  @ManyToOne
  @JoinColumn(name = "movie_show_id")
  private MovieShow movieShow;

  @ManyToOne
  private MovieCustomer movieCustomer;

  public void addSeat(ShowSeat seat){
    reservedSeats.add(seat);
    seat.setReservation(this);
  }

  @CreationTimestamp
  LocalDateTime dateReserved;

}
