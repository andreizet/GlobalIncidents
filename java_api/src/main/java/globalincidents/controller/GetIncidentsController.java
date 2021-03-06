package globalincidents.controller;

import org.json.JSONArray;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import utils.ApiUtils;
import utils.Constants;
import utils.DBConnection;

@RequestMapping(value = "/get-incidents")
@RestController
public class GetIncidentsController extends BaseController {
  private Integer mLimit = (Integer)Constants.API_LIMIT.getDefault();
  private String mFilter = (String)Constants.API_FILTER.getDefault();
  private double mMinLat = (Double) Constants.API_MIN_LAT.getDefault();
  private double mMaxLat = (Double) Constants.API_MAX_LAT.getDefault();
  private double mMinLng = (Double) Constants.API_MIN_LNG.getDefault();
  private double mMaxLng = (Double) Constants.API_MAX_LNG.getDefault();

  @Override
  public String run(@RequestParam MultiValueMap<String,String> params) {
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
    this.mLimit = Integer.parseInt((String)ApiUtils.getParamString(Constants.API_LIMIT, params));
    this.mFilter = (String)ApiUtils.getParamString(Constants.API_FILTER, params);
    this.mMinLat = (Double)ApiUtils.getParamString(Constants.API_MIN_LAT, params);
    this.mMaxLat = (Double)ApiUtils.getParamString(Constants.API_MAX_LAT, params);
    this.mMinLng = (Double)ApiUtils.getParamString(Constants.API_MIN_LNG, params);
    this.mMaxLng = (Double)ApiUtils.getParamString(Constants.API_MAX_LNG, params);
  }

}