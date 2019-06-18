package globalincidents.controller;

import org.json.JSONArray;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import utils.DBConnection;

@RestController
public class GetItemController extends BaseController{
  int mId = -1;

  @Override
  @GetMapping("/get-item")
  public String execute(@RequestParam MultiValueMap<String,String> params) {
    super.execute(params);
    JSONArray toReturn = DBConnection.ExecuteQuery("select * from incidents where id=" + this.mId);
    return toReturn.toString();
  }

  @Override
  public void getParams(MultiValueMap<String, String> params) {
    if(params.getFirst("id") != null)
      this.mId = Integer.parseInt(params.getFirst("id"));
  }
}
