package globalincidents.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public abstract class BaseController {
  Logger mLogger = LoggerFactory.getLogger(BaseController.class);

  public abstract void getParams(MultiValueMap<String,String> params);
  public abstract String run(MultiValueMap<String,String> params);

  @GetMapping
  public String execute(@RequestParam MultiValueMap<String,String> params) {
    long startTime = System.currentTimeMillis();

    this.getParams(params);
    String response = this.run(params);

    long stopTime = System.currentTimeMillis();

    mLogger.info("Time: " + (stopTime - startTime) + "ms");

    return response;
  }

}
