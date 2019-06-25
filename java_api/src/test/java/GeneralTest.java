import globalincidents.controller.GetIncidentsController;
import junit.framework.TestCase;
import org.json.JSONArray;
import org.springframework.util.CollectionUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class GeneralTest extends TestCase {

  public void testInt(){
    MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
    params.add("limit", "1");

    GetIncidentsController incidents = new GetIncidentsController();
    String response = incidents.getResults(params);

    JSONArray json = new JSONArray(response);
    assertTrue(json.length() > 0);
  }
}
