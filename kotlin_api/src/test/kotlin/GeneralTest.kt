import globalincidents.controller.GetIncidentsController
import junit.framework.TestCase
import org.json.JSONArray
import org.springframework.util.LinkedMultiValueMap

class GeneralTest : TestCase() {

    fun testInt() {
        val params = LinkedMultiValueMap<String, String>()
        params.add("limit", "1")

        val incidents = GetIncidentsController()
        var response: String = incidents.getResults(params)

        val json = JSONArray(response)
        TestCase.assertTrue(json.length() > 0)
    }
}
