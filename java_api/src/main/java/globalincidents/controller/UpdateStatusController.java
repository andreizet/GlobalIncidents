package globalincidents.controller;

import org.json.JSONObject;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import utils.*;

@RestController
public class UpdateStatusController extends BaseController{
  private int mId = (Integer) Constants.API_ID.getDefault();
  private int mStatus = (Integer) Constants.API_STATUS.getDefault();

  @Override
  @GetMapping("/update-status")
  public String execute(@RequestParam MultiValueMap<String,String> params) {
    super.execute(params);

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
    if(params.getFirst("id") != null)
      this.mId = Integer.parseInt(params.getFirst("id"));

    if(params.getFirst("status") != null)
      this.mStatus = Integer.parseInt(params.getFirst("status"));
  }
}