package globalincidents.controller

import org.json.JSONObject
import org.springframework.util.MultiValueMap
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import utils.*

@RestController
@RequestMapping("/update-status")
class UpdateStatusController : BaseController() {
    internal var mId: Any? = Constants.API_ID.getDefault()
    internal var mStatus: Any? = Constants.API_STATUS.getDefault()

    override fun run(@RequestParam params: MultiValueMap<String, String>): String {
        if (this.mId == -1) {
            val obj = JSONObject()
            obj.put("message", "Some of the mandatory parameters are missing. Please consult our API documentation")

            return obj.toString()
        }

        val idClause = " and id=" + this.mId
        val statusClause = " status=" + this.mStatus

        DBConnection.executeUpdate("update incidents set $statusClause where 1 $idClause")

        val obj = JSONObject()
        obj.put("message", "successfully inserted")

        return obj.toString()
    }

    override fun getParams(params: MultiValueMap<String, String>) {
        this.mId = ApiUtils.getParam(Constants.API_ID, params)
        this.mStatus = ApiUtils.getParam(Constants.API_STATUS, params)
    }
}
