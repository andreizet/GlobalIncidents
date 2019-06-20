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
class GetItemController : BaseController() {
    internal var mId: Any? = Constants.API_ID.getDefault()

    @GetMapping("/get-item")
    override fun execute(@RequestParam params: MultiValueMap<String, String>): String {
        super.execute(params)

        if(this.mId == -1)
        {
            val obj = JSONObject()
            obj.put("message", "Some of the mandatory parameters are missing. Please consult our API documentation")

            return obj.toString()
        }

        val toReturn = DBConnection.executeQuery("select * from incidents where id=" + this.mId)
        return toReturn.toString()
    }

    override fun getParams(params: MultiValueMap<String, String>) {
        this.mId = ApiUtils.getParam(Constants.API_ID, params)
    }
}