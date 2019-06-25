package globalincidents.controller

import org.slf4j.LoggerFactory
import org.springframework.util.MultiValueMap
import org.springframework.web.bind.annotation.RequestParam

abstract class BaseController {
    internal var logger = LoggerFactory.getLogger(BaseController::class.java)

    abstract fun getParams(params: MultiValueMap<String, String>)
    abstract fun getResults(params: MultiValueMap<String, String>): String

    open fun execute(@RequestParam params: MultiValueMap<String, String>): String {
        val startTime = System.currentTimeMillis()

        this.getParams(params)
        val response = this.getResults(params)

        val stopTime = System.currentTimeMillis()
        logger.info("Time: " + (stopTime - startTime) + "ms")

        return response
    }

}
