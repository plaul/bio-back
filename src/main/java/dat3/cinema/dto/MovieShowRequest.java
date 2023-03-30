package dat3.cinema.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MovieShowRequest {
    LocalDate startDate;
    int numberOfDays;
    double price;
    String firstShow; //String formated like HH:MM
    int movieID;
    int cinemaHallId;
    int showsPrDay;
}
