package dat3.cinema.repository;

import dat3.cinema.entity.CinemaHall;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CinemaHallRepostitory extends JpaRepository<CinemaHall,Integer> {
}
