package globalincidents.controller;

import org.json.JSONObject;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import utils.ApiUtils;
import utils.Constants;
import utils.DBConnection;

@RestController
public class InsertIncident extends BaseController{
  private String mTitle = (String) Constants.API_TITLE.getDefault();
  private String mDescription = (String) Constants.API_DESCRIPTION.getDefault();
  private double mLat =(Double) Constants.API_LAT.getDefault();
  private double mLng = (Double) Constants.API_LNG.getDefault();
  private int mPriority = (Integer) Constants.API_PRIORITY.getDefault();

  @Override
  @GetMapping("/insert-incident")
  public String execute(@RequestParam MultiValueMap<String,String> params) {
    return super.execute(params);
  }

  @Override
  public String getResults(@RequestParam MultiValueMap<String,String> params) {
    if(this.mTitle == null || this.mDescription == null || mLat == -1 || mLng == -1)
    {
      JSONObject obj = new JSONObject();
      obj.put("message", "Some of the mandatory parameters are missing. Please consult our API documentation");

      return obj.toString();
    }

    DBConnection.ExecuteUpdate("insert ignore into incidents (title, description, lat, lng, priority) "
        + "values "
        + "(\"" + this.mTitle + "\", \"" + this.mDescription + "\", " + this.mLat+ ", "
        + this.mLng + ", " + this.mPriority + ")");

    JSONObject obj = new JSONObject();
    obj.put("message", "successfully inserted");

    return obj.toString();
  }

  @Override
  public void getParams(MultiValueMap<String, String> params) {
    this.mTitle = (String) ApiUtils.getParamString(Constants.API_TITLE, params);
    this.mDescription = (String) ApiUtils.getParamString(Constants.API_DESCRIPTION, params);
    this.mLat = Double.parseDouble((String)ApiUtils.getParamString(Constants.API_LAT, params));
    this.mLng = Double.parseDouble((String)ApiUtils.getParamString(Constants.API_LNG, params));
    this.mPriority = Integer.parseInt((String)ApiUtils.getParamString(Constants.API_PRIORITY, params));
  }
}
