package globalincidents.controller

import org.springframework.util.MultiValueMap
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import utils.DBConnection

@RestController
class GetItemController : BaseController() {
    internal var mId = -1

    @GetMapping("/get-item")
    override fun execute(@RequestParam params: MultiValueMap<String, String>): String {
        super.execute(params)
        val toReturn = DBConnection.executeQuery("select * from incidents where id=" + this.mId)
        return toReturn.toString()
    }

    override fun getParams(params: MultiValueMap<String, String>) {
        if (params.getFirst("id") != null)
            this.mId = Integer.parseInt(params.getFirst("id")!!)
    }
}
