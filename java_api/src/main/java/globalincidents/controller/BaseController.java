package globalincidents.controller;

import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestParam;

public abstract class BaseController {

  public abstract void getParams(MultiValueMap<String,String> params);

  public String execute(@RequestParam MultiValueMap<String,String> params) {
    this.getParams(params);
    return "";
  }

}
