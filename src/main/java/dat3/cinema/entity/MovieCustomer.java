package dat3.cinema.entity;

import dat3.security.entity.UserWithRoles;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor

@Entity
public class MovieCustomer  extends UserWithRoles {

  @OneToMany(mappedBy = "movieCustomer")
  List<Reservation> reservations = new ArrayList<>();

  public MovieCustomer(String user, String password, String email) {
    super(user, password, email);
  }

  public void addReservation(Reservation reservation){
    reservations.add(reservation);
    reservation.setMovieCustomer(this);
  }

}
