package globalincidents.controller

import org.springframework.util.MultiValueMap
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import utils.DBConnection

@RestController
class GetIncidentsController: BaseController(){
    internal var mLimit = 10
    internal var mFilter: String? = ""
    internal var mMinLat = -1.0
    internal var mMaxLat = -1.0
    internal var mMinLng = -1.0
    internal var mMaxLng = -1.0

    @GetMapping("/get-incidents")
    override fun execute(@RequestParam params: MultiValueMap<String, String>): String {
        super.execute(params)

        val limitClause = " limit " + this.mLimit

        var filterClause = ""
        if (this.mFilter != null)
            filterClause = " and title like '%" + this.mFilter + "%' or description like '%" + this.mFilter + "%'"

        var minLatClause = ""
        if (this.mMinLat != -1.0)
            minLatClause = " and lat >= " + this.mMinLat

        var maxLatClause = ""
        if (this.mMaxLat != -1.0)
            maxLatClause = " and lat <= " + this.mMaxLat

        var minLngClause = ""
        if (this.mMinLng != -1.0)
            minLngClause = " and lng >= " + this.mMinLng

        var maxLngClause = ""
        if (this.mMaxLng != -1.0)
            maxLngClause = " and lng <= " + this.mMaxLng

        val incidents = DBConnection.executeQuery("select * from incidents where 1 $filterClause $minLatClause " +
                " $maxLatClause $minLngClause $maxLngClause $limitClause")
        return incidents.toString()
    }

    override fun getParams(params: MultiValueMap<String, String>) {
        if (params.getFirst("limit") != null)
            this.mLimit = Integer.parseInt(params.getFirst("limit")!!)

        if (params.getFirst("filter") != "")
            this.mFilter = params.getFirst("filter")

        if (params.getFirst("min_lat") != null)
            this.mMinLat = java.lang.Double.parseDouble(params.getFirst("min_lat")!!)

        if (params.getFirst("max_lat") != null)
            this.mMaxLat = java.lang.Double.parseDouble(params.getFirst("max_lat")!!)

        if (params.getFirst("min_lng") != null)
            this.mMinLat = java.lang.Double.parseDouble(params.getFirst("min_lng")!!)

        if (params.getFirst("max_lng") != null)
            this.mMaxLng = java.lang.Double.parseDouble(params.getFirst("max_lng")!!)
    }
}