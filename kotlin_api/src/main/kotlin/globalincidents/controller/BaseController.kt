package globalincidents.controller

import org.slf4j.LoggerFactory
import org.springframework.util.MultiValueMap
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
abstract class BaseController {
    internal var logger = LoggerFactory.getLogger(BaseController::class.java)

    abstract fun getParams(params: MultiValueMap<String, String>)
    abstract fun run(params: MultiValueMap<String, String>): String

    @GetMapping
    fun execute(@RequestParam params: MultiValueMap<String, String>): String {
        val startTime = System.currentTimeMillis()

        this.getParams(params)
        val response = this.run(params)

        val stopTime = System.currentTimeMillis()
        logger.info("Time: " + (stopTime - startTime) + "ms")

        return response
    }

}
