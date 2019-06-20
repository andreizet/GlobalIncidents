package globalincidents.controller

import org.springframework.util.MultiValueMap
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import utils.ApiUtils
import utils.Constants
import utils.DBConnection

@RestController
class GetIncidentsController: BaseController(){
    internal var mLimit: Any? = Constants.API_LIMIT.getDefault()
    internal var mFilter: Any? = Constants.API_FILTER.getDefault()
    internal var mMinLat: Any? = Constants.API_MIN_LAT.getDefault()
    internal var mMaxLat: Any? = Constants.API_MAX_LAT.getDefault()
    internal var mMinLng: Any? = Constants.API_MIN_LNG.getDefault()
    internal var mMaxLng: Any? = Constants.API_MAX_LNG.getDefault()

    @GetMapping("/get-incidents")
    override fun execute(@RequestParam params: MultiValueMap<String, String>): String {
        super.execute(params)

        val limitClause = " limit " + this.mLimit

        var filterClause = ""
        if (this.mFilter != "")
            filterClause = " and title like '%" + this.mFilter + "%' or description like '%" + this.mFilter + "%'"

        var minLatClause = ""
        if (this.mMinLat != -1)
            minLatClause = " and lat >= " + this.mMinLat

        var maxLatClause = ""
        if (this.mMaxLat != -1)
            maxLatClause = " and lat <= " + this.mMaxLat

        var minLngClause = ""
        if (this.mMinLng != -1)
            minLngClause = " and lng >= " + this.mMinLng

        var maxLngClause = ""
        if (this.mMaxLng != -1)
            maxLngClause = " and lng <= " + this.mMaxLng

        val incidents = DBConnection.executeQuery("select * from incidents where 1 $filterClause $minLatClause " +
                " $maxLatClause $minLngClause $maxLngClause $limitClause")
        return incidents.toString()
    }

    override fun getParams(params: MultiValueMap<String, String>) {
        this.mLimit = ApiUtils.getParam(Constants.API_LIMIT, params)
        this.mFilter = ApiUtils.getParam(Constants.API_FILTER, params)
        this.mMinLat = ApiUtils.getParam(Constants.API_MIN_LAT, params)
        this.mMaxLat = ApiUtils.getParam(Constants.API_MAX_LAT, params)
        this.mMinLat = ApiUtils.getParam(Constants.API_MIN_LNG, params)
        this.mMaxLng = ApiUtils.getParam(Constants.API_MAX_LNG, params)
    }
}