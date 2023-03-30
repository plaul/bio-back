package dat3.cinema.service;

import dat3.cinema.dto.MovieShowRequest;
import dat3.cinema.dto.MovieShowResponse;
import dat3.cinema.entity.CinemaHall;
import dat3.cinema.entity.Movie;
import dat3.cinema.entity.MovieShow;
import dat3.cinema.repository.CinemaHallRepostitory;
import dat3.cinema.repository.MovieRepository;
import dat3.cinema.repository.MovieShowRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class MovieShowService {

  CinemaHallRepostitory cinemaHallRepostitory;
  MovieRepository movieRepository;
  MovieShowRepository movieShowRepository;

  public MovieShowService(CinemaHallRepostitory cinemaHallRepostitory, MovieRepository movieRepository, MovieShowRepository movieShowRepository) {
    this.cinemaHallRepostitory = cinemaHallRepostitory;
    this.movieRepository = movieRepository;
    this.movieShowRepository = movieShowRepository;
  }

  private int TIME_BETWEEN_SHOWS = 30;  //Time to get people out, in and clean up between shows

  public List<MovieShowResponse> getShowingsForDate(LocalDate date){
    List<MovieShow> showings = movieShowRepository.findByDateOrderByStartTime(date);
    List<MovieShowResponse> response = showings.stream().map(MovieShowResponse::new).toList();
    return response;
  }

  public MovieShowResponse getMoviewShow(int movieId) {
    MovieShow showing = movieShowRepository.findById(movieId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "MovieShow not found"));
    return new MovieShowResponse(showing,true);
  }

  public String makeMovieShows(MovieShowRequest body){

    Movie movie = movieRepository.findById(body.getMovieID()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie not found"));
    CinemaHall cinemaHall1= cinemaHallRepostitory.findById(body.getCinemaHallId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "CinemaHall not found"));
    int runTime = extractMinutesAsInt(movie.getRuntime());
    int minutesToAdd = runTime+TIME_BETWEEN_SHOWS; //length of movie in minutes + time to get people in and out of the cinema
    String status ="Added shows for '"+movie.getTitle()+"' in cinema hall: "+cinemaHall1.getHallName()+" from "+body.getStartDate()+" to "+body.getStartDate().plusDays(body.getNumberOfDays())+" with a price of "+body.getPrice()+" kr.";
    for(int i=0;i<body.getNumberOfDays();i++) {
      //System.out.println(body.getStartDate().plusDays(i));
      LocalDate date = body.getStartDate().plusDays(i);
      LocalTime showTime = getLocalTime(body.getFirstShow());
      status += "\\n" +date +": ";
      for (int j = 0; j < body.getShowsPrDay(); j++) {
        int addTime = (j==0) ? 0 : minutesToAdd;
        showTime = showTime.plusMinutes(addTime);
        int minutesToRoundUp = (30 - (showTime.getMinute() % 30)) % 30;
        showTime = showTime.plusMinutes(minutesToRoundUp);
        System.out.println(showTime);
        status += showTime +", ";
        MovieShow movieShow = MovieShow.builder()
                .movie(movie)
                .cinemaHall(cinemaHall1)
                .date(date)
                .startTime(showTime)
                .build();
        movieShow.setupShowSeats(body.getPrice());
        movieShowRepository.save((movieShow));
      }
    }
   return "{ \"status\": \""+status+"\" }";
  }
  private static LocalTime getLocalTime(String firstShow) {
    var firstShowParts = firstShow.split(":");
    int hours = Integer.parseInt(firstShowParts[0]);
    int minutes = Integer.parseInt(firstShowParts[1]);
    LocalTime showTime = LocalTime.of(hours,minutes);
    return showTime;
  }

  private int extractMinutesAsInt(String runtime){
    Pattern pattern = Pattern.compile("\\d+");
    Matcher matcher = pattern.matcher(runtime);
    if (matcher.find()) {
      String numberStr = matcher.group();
      return Integer.parseInt(numberStr);
    } else {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"No runtime found in input string");
    }
  }



  public static void main(String[] args) {
    int days=3;
    int showsPrDay=4;
    int minutesToAdd = 140; //length of movie in minutes + time to get people in and out of the cinema
    String firstShow ="14:00";
    LocalDate startDate = LocalDate.of(2023,3,25);

    for(int i=0;i<days;i++) {
      LocalDate date = startDate.plusDays(i);
      LocalTime showTime = getLocalTime(firstShow);
      for (int j = 0; j < showsPrDay; j++) {
        int addTime = (j==0) ? 0 : minutesToAdd;
        showTime = showTime.plusMinutes(addTime);
        int minutesToRoundUp = (30 - (showTime.getMinute() % 30)) % 30;
        showTime = showTime.plusMinutes(minutesToRoundUp);
        System.out.println(showTime);
      }
    }
  }

}
