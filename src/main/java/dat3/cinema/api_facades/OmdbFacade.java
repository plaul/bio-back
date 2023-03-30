package dat3.cinema.api_facades;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dat3.cinema.dto.MovieDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

@Component
public class OmdbFacade {


  @Value("${OMDB_KEY}")
  private String API_KEY;
  RestTemplate restTemplate = new RestTemplate();
  public MovieDTO getMovieFromOmdb(String imdbID){

    String plot = "full";
    String url = "http://www.omdbapi.com/?apikey=" + API_KEY + "&i=" + imdbID + "&plot=" + plot;
    //Fetch the movie
    MovieDTO response = restTemplate.getForObject(url, MovieDTO.class);
    return response;
  }
  public String getImdbIDFromTitle(String title) throws JsonProcessingException {
    RestTemplate restTemplate = new RestTemplate();
    String plot = "full";
    String url = "http://www.omdbapi.com/?apikey=" + API_KEY + "&t=" + title;

    //Fetch the movie
    String res = restTemplate.getForObject(url, String.class);
    //Not a very robust way to detect errors, but this server returns 200 both for OK and not OK responses
    if(res.contains("\"Response\":\"False\"")){
      throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Movie not found");
    }
    ObjectMapper om = new ObjectMapper();
    MovieDTO root = om.readValue(res, MovieDTO.class);
    String response = "{\"imdbId\":\"" +root.imdbID+ "\",\"title\":\""+root.title+"\"}";
    return response;
  }
}


/*
//  private static OmdbFacade instance = null;
//
//  private OmdbFacade() {}
//
//  public static OmdbFacade getOmdbFacadInstance() {
//    if (instance == null)
//      instance = new OmdbFacade();
//    return instance;
//  }

 */