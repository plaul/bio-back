package dat3.cinema.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import dat3.cinema.entity.ShowSeat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ShowSeatDTO {
  int seatId;
  double price;
  int seatNum;
  int rowNum;
  Boolean isFree;

  public ShowSeatDTO(ShowSeat s) {
    this.seatId = s.getId();
    this.price = s.getPrice();
    this.seatNum = s.getSeat().getSeatNumber();
    this.rowNum = s.getSeat().getRowNumber();
    this.isFree = s.getReservation() == null;
  }

  public static ShowSeatDTO getShowSeatDtoWithoutFree(ShowSeat s) {
    ShowSeatDTO dto = new ShowSeatDTO(s);
    dto.setIsFree(null);
    return  dto;
  }
}
