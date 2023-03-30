package dat3.cinema.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import dat3.cinema.dto.MovieDTO;
import dat3.cinema.entity.Movie;
import dat3.cinema.service.MovieService;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("api/movie")
@CrossOrigin
public class MovieController {

  MovieService movieService;

  public MovieController(MovieService movieService) {
    this.movieService = movieService;
  }

  @GetMapping("/imdbid/{imdbId}")
  public Movie getMovie(@PathVariable String imdbId) {
    return movieService.getMovieByImdbId(imdbId);
  }

  @GetMapping()
  public List<MovieDTO>  getAllMovies(Pageable pageable){
    return movieService.getMovies(pageable);
  }

  @PreAuthorize("hasAuthority('ADMIN')")
  @PatchMapping("/plotdk/{id}")
  public String editMoviePlotDK(@RequestBody String text, @PathVariable int id){
    return movieService.editMoviePlotDK(text, id);
  }
  @PreAuthorize("hasAuthority('ADMIN')")
  @GetMapping(value = "/imdbfromtitle/{title}", produces = "application/json")
  public String getImdbFromTitle(@PathVariable String title) throws JsonProcessingException {
    return movieService.getImdbIdFromTitle(title);
  }
  @GetMapping("/{id}")
  public Movie getMovie(@PathVariable int id) {
    return movieService.getMovieById(id);
  }

  @PreAuthorize("hasAuthority('ADMIN')")
  @PostMapping("/{imdbId}")
  public Movie addMovie(@PathVariable String imdbId) throws JsonProcessingException {
    return movieService.addMovie(imdbId);
  }



}
