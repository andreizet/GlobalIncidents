package globalincidents.controller;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import utils.DBConnection;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RequestMapping(value = "/grouped-countries")
@RestController
public class GetGroupedCountries extends BaseController {
    static final int MAX_THREADS_NO = 5;

    /**
     * Challenge: Why is this call returning different values everytime time?
     */
    @Override
    public String run(@RequestParam MultiValueMap<String,String> params) {
        Map<String, Integer> toReturn = Collections.synchronizedMap(new HashMap<String, Integer>());

        JSONArray results = DBConnection.ExecuteQuery("select id, title from incidents");
        int limit = results.length() / MAX_THREADS_NO;

        ExecutorService executor = Executors.newFixedThreadPool(MAX_THREADS_NO);
        for (int i = 0; i < MAX_THREADS_NO; i++) {
            int index = i;
            Thread worker = new Thread(() -> {
                for( int jsonObjIndex = index * limit; jsonObjIndex < limit * (index + 1); jsonObjIndex++ ) {
                    String title = results.getJSONObject(jsonObjIndex).getString("title");
                    String[] splittedTitle = title.split(",");
                    String country = splittedTitle[splittedTitle.length - 1];


                    int currentValue = toReturn.getOrDefault(country, 0);
                    toReturn.put(country, currentValue + 1);
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
