package globalincidents.controller

import org.json.JSONObject
import org.springframework.util.MultiValueMap
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import utils.DBConnection

@RestController
class UpdateStatusController : BaseController() {
    internal var mId = -1
    internal var mStatus = 0

    @GetMapping("/update-status")
    override fun execute(@RequestParam params: MultiValueMap<String, String>): String {
        super.execute(params)

        if (this.mId == -1) {
            val obj = JSONObject()
            obj.put("message", "Some of the mandatory parameters are missing. Please consult our API documentation")

            return obj.toString()
        }

        var idClause = ""
        if (this.mId == -1)
            idClause = " and id=" + this.mId

        val statusClause = " status=" + this.mStatus

        DBConnection.executeUpdate("update incidents set $statusClause where 1 $idClause")

        val obj = JSONObject()
        obj.put("message", "successfully inserted")

        return obj.toString()
    }

    override fun getParams(params: MultiValueMap<String, String>) {
        if (params.getFirst("id") != null)
            this.mId = Integer.parseInt(params.getFirst("id")!!)

        if (params.getFirst("status") != null)
            this.mStatus = Integer.parseInt(params.getFirst("status")!!)
    }
}
