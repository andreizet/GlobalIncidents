package globalincidents.controller;

import org.json.JSONArray;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import utils.DBConnection;

@RestController
public class GetIncidentsController extends BaseController{
  int mLimit = 10;
  String mFilter = null;
  double mMinLat = -1;
  double mMaxLat = -1;
  double mMinLng = -1;
  double mMaxLng = -1;

  @Override
  @GetMapping("/get-incidents")
  public String execute(@RequestParam MultiValueMap<String,String> params) {
    super.execute(params);

    String limitClause = " limit " + this.mLimit;

    String filterClause = "";
    if(this.mFilter != null)
      filterClause = " and title like '%" + this.mFilter + "%' or description like '%" + this.mFilter + "%'";

    String minLatClause = "";
    if(this.mMinLat != -1)
      minLatClause = " and lat >= " + this.mMinLat;

    String maxLatClause = "";
    if (this.mMaxLat != -1)
      maxLatClause = " and lat <= " + this.mMaxLat;

    String minLngClause = "";
    if (this.mMinLng != -1)
      minLngClause = " and lng >= " + this.mMinLng;

    String maxLngClause = "";
    if(this.mMaxLng != -1)
      maxLngClause = " and lng <= " + this.mMaxLng;

    JSONArray toReturn = DBConnection.ExecuteQuery("select * from incidents where 1 " + filterClause + minLatClause
                         + maxLatClause + minLngClause + maxLngClause + limitClause);
    return toReturn.toString();
  }

  @Override
  public void getParams(MultiValueMap<String, String> params) {
    if(params.getFirst("limit") != null)
      this.mLimit = Integer.parseInt(params.getFirst("limit"));

    if(params.getFirst("filter") != null)
      this.mFilter = params.getFirst("filter");

    if(params.getFirst("min_lat") != null)
      this.mMinLat = Double.parseDouble(params.getFirst("min_lat"));

    if(params.getFirst("max_lat") != null)
      this.mMaxLat = Double.parseDouble(params.getFirst("max_lat"));

    if(params.getFirst("min_lng") != null)
      this.mMinLat = Double.parseDouble(params.getFirst("min_lng"));

    if(params.getFirst("max_lng") != null)
      this.mMaxLng = Double.parseDouble(params.getFirst("max_lng"));
  }

}