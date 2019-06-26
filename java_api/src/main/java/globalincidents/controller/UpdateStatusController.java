package globalincidents.controller;

import org.json.JSONObject;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import utils.*;

@RequestMapping(value = "/update-status")
@RestController
public class UpdateStatusController extends BaseController{
  private int mId = (Integer) Constants.API_ID.getDefault();
  private int mStatus = (Integer) Constants.API_STATUS.getDefault();

  @Override
  public String run(@RequestParam MultiValueMap<String,String> params) {
    if(this.mId == -1)
    {
      JSONObject obj = new JSONObject();
      obj.put("message", "Some of the mandatory parameters are missing. Please consult our API documentation");

      return obj.toString();
    }

    String idClause = "";
    idClause = " and id=" + this.mId;

    String statusClause = " status=" + this.mStatus;

    DBConnection.ExecuteUpdate("update incidents set " + statusClause + " where 1 " + idClause);

    JSONObject obj = new JSONObject();
    obj.put("message", "successfully inserted");

    return obj.toString();
  }

  @Override
  public void getParams(MultiValueMap<String, String> params) {
    this.mId = Integer.parseInt((String)ApiUtils.getParamString(Constants.API_ID, params));
    this.mStatus = Integer.parseInt((String)ApiUtils.getParamString(Constants.API_STATUS, params));
  }
}