package dat3.cinema.api_facades;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

class ChoiceDTO {

  @JsonProperty("text")
  public String text;
  @JsonProperty("index")
  public Integer index;
  @JsonProperty("logprobs")
  public Object logprobs;
  @JsonProperty("finish_reason")
  public String finishReason;

}

public class TranslateDTO {

  @JsonProperty("id")
  public String id;
  @JsonProperty("object")
  public String object;
  @JsonProperty("created")
  public Integer created;
  @JsonProperty("model")
  public String model;
  @JsonProperty("choices")
  public List<ChoiceDTO> choices;
  //@JsonIgnore
  @JsonProperty("usage")
  public Usage usage;
}

class Usage {
  @JsonProperty("prompt_tokens")
  public Integer promptTokens;
  @JsonProperty("completion_tokens")
  public Integer completionTokens;
  @JsonProperty("total_tokens")
  public Integer totalTokens;
}
