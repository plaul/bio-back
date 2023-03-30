package dat3.cinema.repository;

import dat3.cinema.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MovieRepository extends JpaRepository<Movie, Integer> {
  Optional<Movie> findByImdbID(String imdbId);
}
