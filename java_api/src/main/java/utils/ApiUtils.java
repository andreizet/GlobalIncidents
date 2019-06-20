package utils;

import org.springframework.util.MultiValueMap;

public class ApiUtils {

  public static Object getParamString(Constants aParameter, MultiValueMap<String, String> aAllParameters){
    if(aAllParameters.getFirst(aParameter.getKey()) != null)
      return aAllParameters.getFirst(aParameter.getKey());
    else
      return aParameter.getDefault();
  }
}
