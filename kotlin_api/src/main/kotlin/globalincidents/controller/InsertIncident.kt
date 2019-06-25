package globalincidents.controller

import org.json.JSONObject
import org.springframework.util.MultiValueMap
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import utils.ApiUtils
import utils.Constants
import utils.DBConnection

@RestController
class InsertIncident : BaseController() {
    internal var mTitle: Any? = Constants.API_TITLE.getDefault()
    internal var mDescription: Any? = Constants.API_DESCRIPTION.getDefault()
    internal var mLat: Any? = Constants.API_LAT.getDefault()
    internal var mLng: Any? = Constants.API_LNG.getDefault()
    internal var mPriority: Any? = Constants.API_PRIORITY.getDefault()

    @GetMapping("/insert-incident")
    override fun execute(@RequestParam params: MultiValueMap<String, String>): String = super.execute(params)

    override fun getResults(@RequestParam params: MultiValueMap<String, String>): String {
        super.execute(params)
        if (this.mTitle == null || this.mDescription == null || mLat == -1 || mLng == -1)
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
        this.mTitle = ApiUtils.getParam(Constants.API_TITLE, params)
        this.mDescription = ApiUtils.getParam(Constants.API_DESCRIPTION, params)
        this.mLat = ApiUtils.getParam(Constants.API_LAT, params)
        this.mLng = ApiUtils.getParam(Constants.API_LNG, params)
        this.mPriority = ApiUtils.getParam(Constants.API_PRIORITY, params)
    }
}
