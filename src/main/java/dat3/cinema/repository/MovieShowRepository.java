package dat3.cinema.repository;

import dat3.cinema.entity.MovieShow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface MovieShowRepository extends JpaRepository<MovieShow,Integer> {
  List<MovieShow> findByDateOrderByStartTime(LocalDate date);
}
