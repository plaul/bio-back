package dat3.cinema.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class CinemaHall {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  int id;

  //@Column(nullable = false, length = 40)
  String hallName;

  @Column(name = "_rows")
  int rows=0;
  int seatsPrRow=0;

  @OneToMany(fetch = FetchType.EAGER, mappedBy = "cinemaHall",cascade = {CascadeType.MERGE, CascadeType.PERSIST})
  List<Seat> seats = new ArrayList<>();

  @OneToMany(mappedBy = "cinemaHall")
  List<MovieShow> movieShows = new ArrayList<>();

  public void addMovieShow(MovieShow show){
    movieShows.add(show);
    show.setCinemaHall(this);
  }

  public CinemaHall(String hallName, int rows, int seatsPrRow) {
    this.hallName = hallName;
    this.rows = rows;
    this.seatsPrRow = seatsPrRow;
  }

  public void setupSeats(){
    for(int row= 1; row <= rows;row++)
      for(int seat = 1; seat <= seatsPrRow; seat++){
        seats.add(new Seat(row,seat,this));
      }
  }
}