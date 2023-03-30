package dat3.cinema.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import dat3.cinema.api_facades.OmdbFacade;
import dat3.cinema.api_facades.TranslateFacade;
import dat3.cinema.dto.MovieDTO;
import dat3.cinema.entity.Movie;
import dat3.cinema.repository.MovieRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class MovieService {

  MovieRepository movieRepository;
  OmdbFacade omdbFacade;
  TranslateFacade translateFacade;

  public MovieService(MovieRepository movieRepository, OmdbFacade omdbFacade, TranslateFacade translateFacade) {
    this.movieRepository = movieRepository;
    this.omdbFacade = omdbFacade;
    this.translateFacade = translateFacade;
  }

  public Movie getMovieByImdbId(String imdbId) {
    return movieRepository.findByImdbID(imdbId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie not found"));
  }

  public Movie getMovieById(int id) {
    return movieRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie not found"));
  }

  public String getImdbIdFromTitle(String title) throws JsonProcessingException {
    return omdbFacade.getImdbIDFromTitle(title);
  }

  public List<MovieDTO> getMovies(Pageable pageable) {
    Page<Movie> movies = movieRepository.findAll(pageable);
    List<MovieDTO> response = movies.stream().map(m ->
                    MovieDTO.builder().actors(m.getActors())
                            .title(m.getTitle())
                            .imdbRating(m.getImdbRating())
                            .id(m.getId())
                            .runtime(m.getRuntime())
                            .build())
            .toList();
    return response;
  }

  public String editMoviePlotDK(String text, int id) {
    Movie movie = movieRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie not found"));
    movie.setPlotDK(text);
    movieRepository.save(movie);
    return "{\"status\":\"OK\"}";
  }

  public Movie addMovie(String imdbId) throws JsonProcessingException {
    MovieDTO response = omdbFacade.getMovieFromOmdb(imdbId);

    String plotDK = translateFacade.translateText(response.plot, 200);

    Movie movie = Movie.builder().title(response.title)
            .year(response.year)
            .rated(response.rated)
            .released(response.released)
            .runtime(response.runtime)
            .genre(response.genre)
            .director(response.director)
            .writer(response.writer)
            .actors(response.actors)
            .metascore(response.metascore)
            .imdbRating(response.imdbRating)
            .imdbVotes(response.imdbVotes)
            .website(response.website)
            .response(response.response)
            .plot(response.plot)
            .plotDK(plotDK)
            .poster(response.poster)
            .imdbID(response.imdbID)
            .build();

    try {
      movie = movieRepository.save(movie);
      return movie;
    } catch (DataIntegrityViolationException e) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getRootCause().getMessage());
    } catch (Exception e) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not add movie");
    }
  }
}
