package globalincidents.controller;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import utils.DBConnection;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
public class GetGroupedDescriptions extends BaseController {
  static final int MAX_THREADS_NO = 5;

  @Override
  @GetMapping("/grouped-descriptions")
  public String execute(@RequestParam MultiValueMap<String,String> params) {
    return super.execute(params);
  }

  /**
   * Challenge: Why is this call returning different values everytime time?
   */
  @Override
  public String getResults(@RequestParam MultiValueMap<String,String> params) {
    Map<String, Integer> toReturn = Collections.synchronizedMap(new HashMap<String, Integer>());

    JSONArray results = DBConnection.ExecuteQuery("select id, description from incidents");
    int limit = results.length() / MAX_THREADS_NO;

    ExecutorService executor = Executors.newFixedThreadPool(MAX_THREADS_NO);
    for (int i = 0; i < MAX_THREADS_NO; i++) {
      int index = i;
      Thread worker = new Thread(() -> {
          for( int jsonObjIndex = index * limit; jsonObjIndex < limit * (index + 1); jsonObjIndex++ ) {
            String description = results.getJSONObject(jsonObjIndex).getString("description");
            
            int currentValue = toReturn.getOrDefault(description, 0);
            toReturn.put(description, currentValue + 1);
          }
      });
      executor.execute(worker);
    }
    executor.shutdown();
    while (!executor.isTerminated()) {
      try {
        Thread.sleep(100);
      } catch(Exception e){
      }
    }

    JSONObject json = new JSONObject(toReturn);
    return json.toString();
  }

  @Override
  public void getParams(MultiValueMap<String, String> params) {
  }

}
