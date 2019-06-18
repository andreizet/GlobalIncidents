package globalincidents.controller;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import utils.DBConnection;

@RestController
public class InsertIncident extends BaseController{
  String mTitle = null;
  String mDescription = null;
  double mLat = -1;
  double mLng = -1;
  int mPriority = 0;

  @Override
  @GetMapping("/insert-incident")
  public String execute(@RequestParam MultiValueMap<String,String> params) {
    super.execute(params);
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
    if(params.getFirst("title") != null)
      this.mTitle = params.getFirst("title");

    if(params.getFirst("description") != null)
      this.mDescription = params.getFirst("description");

    if(params.getFirst("lat") != null)
      this.mLat = Double.parseDouble(params.getFirst("lat"));

    if(params.getFirst("lng") != null)
      this.mLng = Double.parseDouble(params.getFirst("lng"));

    if(params.getFirst("priority") != null)
      this.mPriority = Integer.parseInt(params.getFirst("priority"));
  }
}
