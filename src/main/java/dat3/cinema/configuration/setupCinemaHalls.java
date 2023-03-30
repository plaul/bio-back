package dat3.cinema.configuration;

import dat3.cinema.entity.CinemaHall;
import dat3.cinema.repository.CinemaHallRepostitory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Controller;

@Controller
public class setupCinemaHalls implements ApplicationRunner {

  CinemaHallRepostitory cinemaHallRepostitory;

  public setupCinemaHalls(CinemaHallRepostitory cinemaHallRepostitory) {
    this.cinemaHallRepostitory = cinemaHallRepostitory;
  }

  @Override
  public void run(ApplicationArguments args) throws Exception {
    CinemaHall movieHall1 = new CinemaHall("Hall-1",10,8);
    movieHall1.setupSeats();
    cinemaHallRepostitory.save(movieHall1);

    CinemaHall movieHall2 = new CinemaHall("Hall-2",10,10);
    movieHall2.setupSeats();
    cinemaHallRepostitory.save(movieHall2);

    CinemaHall movieHall3 = new CinemaHall("Hall-2",20,15);
    movieHall3.setupSeats();
    cinemaHallRepostitory.save(movieHall3);
  }
}
