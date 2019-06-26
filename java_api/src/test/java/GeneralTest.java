import globalincidents.controller.GetIncidentsController;
import junit.framework.TestCase;
import org.json.JSONArray;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

public class GeneralTest extends TestCase {

  public void testGetIncidents(){
    MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
    params.add("limit", "1");

    GetIncidentsController incidents = new GetIncidentsController();
    incidents.getParams(params);
    String response = incidents.run(params);

    JSONArray json = new JSONArray(response);
    assertTrue(json.length() > 0);
  }
}
