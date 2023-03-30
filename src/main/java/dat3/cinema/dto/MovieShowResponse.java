package dat3.cinema.dto;

import dat3.cinema.entity.MovieShow;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class MovieShowResponse {
    private int id;
    private LocalDate date;
    private String startTime;
    private double price;
    private String movieTitle;
    private String theaterHall;
    private String posterURL;
    private String plotDK;
    List<ShowSeatDTO> seats = new ArrayList<>();

  public MovieShowResponse(MovieShow movieShow) {
    this.id = movieShow.getId();
    this.date = movieShow.getDate();
    this.startTime = movieShow.getStartTime().toString();
    this.price = movieShow.getShowSeats().get(0).getPrice();
    this.movieTitle = movieShow.getMovie().getTitle();
    this.theaterHall = movieShow.getCinemaHall().getHallName();
    this.posterURL = movieShow.getMovie().getPoster();
    this.plotDK = movieShow.getMovie().getPlotDK();
  }
  public MovieShowResponse(MovieShow movieShow,boolean includeSeats){
    this(movieShow);
    if(includeSeats){
      this.seats = movieShow.getShowSeats().stream().map(ShowSeatDTO::new).collect(Collectors.toList());
    }
  }


}
