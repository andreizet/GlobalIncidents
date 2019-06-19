package globalincidents.controller

import org.json.JSONObject
import org.springframework.util.MultiValueMap
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import utils.DBConnection

@RestController
class InsertIncident : BaseController() {
    internal var mTitle: String? = null
    internal var mDescription: String? = null
    internal var mLat = -1.0
    internal var mLng = -1.0
    internal var mPriority = 0

    @GetMapping("/insert-incident")
    override fun execute(@RequestParam params: MultiValueMap<String, String>): String {
        super.execute(params)
        if (this.mTitle == null || this.mDescription == null || mLat == -1.0 || mLng == -1.0)
        {
            val obj = JSONObject()
            obj.put("message", "Some of the mandatory parameters are missing. Please consult our API documentation")

            return obj.toString()
        }

        DBConnection.executeUpdate(
            "insert ignore into incidents (title, description, lat, lng, priority) "
                    + "values "
                    + "(\"" + this.mTitle + "\", \"" + this.mDescription + "\", " + this.mLat + ", "
                    + this.mLng + ", " + this.mPriority + ")"
        )

        val obj = JSONObject()
        obj.put("message", "successfully inserted")

        return obj.toString()
    }

    override fun getParams(params: MultiValueMap<String, String>) {
        if (params.getFirst("title") != null)
            this.mTitle = params.getFirst("title")

        if (params.getFirst("description") != null)
            this.mDescription = params.getFirst("description")

        if (params.getFirst("lat") != null)
            this.mLat = java.lang.Double.parseDouble(params.getFirst("lat")!!)

        if (params.getFirst("lng") != null)
            this.mLng = java.lang.Double.parseDouble(params.getFirst("lng")!!)

        if (params.getFirst("priority") != null)
            this.mPriority = Integer.parseInt(params.getFirst("priority")!!)
    }
}
