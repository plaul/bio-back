package dat3.cinema.api_facades;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;


@Controller
public class TranslateFacade {

  static final String URI = "https://api.openai.com/v1/completions";

  public static final String NOT_TRANSLATED = "NOT TRANSLATED";

  @Value("${OPENAI_KEY}")
  private String OPENAPI_KEY;

  RestTemplate restTemplate = new RestTemplate();

  public String translateText(String text,int maxTokens) throws JsonProcessingException {
    System.out.println("KEY ----> "+OPENAPI_KEY);
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.set("Authorization", "Bearer " + OPENAPI_KEY);

    Map<String, Object> requestBody = new HashMap<>();
    requestBody.put("model", "text-davinci-003");
    requestBody.put("prompt", "translate this into danish: " + text);
    requestBody.put("temperature", 0.3);
    requestBody.put("max_tokens", maxTokens);
    requestBody.put("top_p", 1.0);
    requestBody.put("frequency_penalty", 0.0);
    requestBody.put("presence_penalty", 0.0);

    HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);
    try {
      ResponseEntity<String> response = restTemplate.exchange("https://api.openai.com/v1/completions", HttpMethod.POST, entity, String.class);
      String responseBody = response.getBody();
      System.out.println(responseBody);

      ObjectMapper om = new ObjectMapper();
      TranslateDTO root = om.readValue(responseBody, TranslateDTO.class);
      String result =  root.choices.get(0).text;
      return result.replaceAll("^[\r\n]+", ""); // remove leading newlines

    } catch (Exception e){
      System.out.println(e.getMessage());
      return NOT_TRANSLATED;
    }


  }

  public static void main(String[] args) throws JsonProcessingException {
    TranslateFacade facade = new TranslateFacade();
    facade.OPENAPI_KEY = "";
    String res = facade.translateText("Hello, how are you?", 20);
    System.out.println(res);
  }

}
