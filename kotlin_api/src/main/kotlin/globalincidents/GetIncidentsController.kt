package globalincidents

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class GetIncidentsController {

    @GetMapping("/get-incidents")
    fun greeting(@RequestParam(value = "name", defaultValue = "World") name: String): String {
        val incidents = GetIncidents("asd")
        return incidents.GetResults()
    }
}