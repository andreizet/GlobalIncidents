package globalincidents.controller

import org.json.JSONObject
import org.springframework.util.MultiValueMap
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import utils.DBConnection
import java.util.*
import java.util.concurrent.Executors


@RestController
@RequestMapping("/grouped-countries")
class GetGroupedCountries: BaseController(){
    companion object {
        const val MAX_THREADS_NO = 5
    }

    /**
     * Challenge: Why is this call returning different values everytime time?
     */
    override fun run(@RequestParam params: MultiValueMap<String, String>): String {
        val toReturn = Collections.synchronizedMap(HashMap<String, Int>())
        val results = DBConnection.executeQuery("select id, title from incidents")
        val limit = results.length() / MAX_THREADS_NO

        val executor = Executors.newFixedThreadPool(MAX_THREADS_NO)
        for (i in 0..(MAX_THREADS_NO - 1)) {
            val worker = Runnable {
                for( jsonObjIndex in (i * limit) until (limit * (i + 1))) {

                    val description = results.getJSONObject(jsonObjIndex).getString("title")
                    val country = description.split(",").last()
                    val currentValue = toReturn.getOrDefault(country, 0)
                    toReturn[country] = currentValue + 1
                }
            }
            executor.execute(worker)
        }
        executor.shutdown()
        while (!executor.isTerminated) {
            Thread.sleep(100)
        }

        val json = JSONObject(toReturn)
        return json.toString()
    }

    override fun getParams(params: MultiValueMap<String, String>) {
    }
}