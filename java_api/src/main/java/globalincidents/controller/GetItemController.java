package globalincidents.controller;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import utils.ApiUtils;
import utils.Constants;
import utils.DBConnection;

@RequestMapping(value = "/get-item")
@RestController
public class GetItemController extends BaseController{
  private int mId = (Integer) Constants.API_ID.getDefault();

  @Override
  public String run(@RequestParam MultiValueMap<String,String> params) {
    if(this.mId == -1)
    {
      JSONObject obj = new JSONObject();
      obj.put("message", "Some of the mandatory parameters are missing. Please consult our API documentation");

      return obj.toString();
    }

    JSONArray toReturn = DBConnection.ExecuteQuery("select * from incidents where id=" + this.mId);
    return toReturn.toString();
  }

  @Override
  public void getParams(MultiValueMap<String, String> params) {
    this.mId = Integer.parseInt((String)ApiUtils.getParamString(Constants.API_ID, params));
  }
}