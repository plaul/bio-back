package dat3.cinema.repository;

import dat3.cinema.entity.MovieCustomer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieCustomerRepository extends JpaRepository<MovieCustomer, Integer> {
}
