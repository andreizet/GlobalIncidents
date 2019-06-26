import globalincidents.controller.GetIncidentsController
import junit.framework.TestCase
import org.json.JSONArray
import org.springframework.util.LinkedMultiValueMap

class GeneralTest : TestCase() {

    fun testGetIncidents() {
        val params = LinkedMultiValueMap<String, String>()
        params.add("limit", "1")

        val incidents = GetIncidentsController()
        incidents.getParams(params)
        var response: String = incidents.run(params)

        val json = JSONArray(response)
        TestCase.assertTrue(json.length() > 0)
    }
}