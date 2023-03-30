package dat3.cinema.api;


import dat3.cinema.dto.MovieShowRequest;
import dat3.cinema.dto.MovieShowResponse;
import dat3.cinema.service.MovieShowService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("api/movieshow")
@CrossOrigin
public class MovieShowController {

  MovieShowService movieShowService;

  public MovieShowController(MovieShowService movieShowService) {
    this.movieShowService = movieShowService;
  }

  @GetMapping("/{date}")
  public List<MovieShowResponse> getMovieShows(@PathVariable LocalDate date){
    return movieShowService.getShowingsForDate(date);
  }

  @GetMapping("/movie-with-seats/{movieId}")
  public MovieShowResponse getMovieShow(@PathVariable int movieId){
    return movieShowService.getMoviewShow(movieId);
  }

  @PostMapping
  public String addMovieShow(@RequestBody MovieShowRequest movieShowRequest){
    return movieShowService.makeMovieShows(movieShowRequest);
  }
}
