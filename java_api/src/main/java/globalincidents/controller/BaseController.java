package globalincidents.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestParam;

public abstract class BaseController {
  Logger logger = LoggerFactory.getLogger(BaseController.class);

  public abstract void getParams(MultiValueMap<String,String> params);
  public abstract String getResults(MultiValueMap<String,String> params);

  public String execute(@RequestParam MultiValueMap<String,String> params) {
    long startTime = System.currentTimeMillis();

    this.getParams(params);
    String response = this.getResults(params);

    long stopTime = System.currentTimeMillis();

    logger.info("Time: " + (stopTime - startTime) + "ms");

    return response;
  }

}
