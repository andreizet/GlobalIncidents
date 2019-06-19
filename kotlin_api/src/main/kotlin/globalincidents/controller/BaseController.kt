package globalincidents.controller

import org.springframework.util.MultiValueMap
import org.springframework.web.bind.annotation.RequestParam

abstract class BaseController {

    abstract fun getParams(params: MultiValueMap<String, String>)

    open fun execute(@RequestParam params: MultiValueMap<String, String>): String {
        this.getParams(params)
        return ""
    }

}
