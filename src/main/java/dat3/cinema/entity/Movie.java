package dat3.cinema.entity;

import jakarta.persistence.*;
import lombok.*;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "movies")
public class Movie {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  private String title;
  @Column(name = "release_year" )
  private String year;
  private String rated;
  private String released;
  private String runtime;
  private String genre;
  private String director;
  private String writer;
  private String actors;

  @Column(length = 2000)
  private String plot;
  @Column(length = 2000)
  private String plotDK;
  private String poster;


  private String metascore;
  private String imdbRating;
  private String imdbVotes;

  @Column(unique = true)
  private String imdbID;
  private String website;
  private String response;

}