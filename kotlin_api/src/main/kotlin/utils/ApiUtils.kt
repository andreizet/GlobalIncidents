package utils

import org.springframework.util.MultiValueMap

class ApiUtils {
    companion object {
        fun getParam(aArg: Constants, aParam: MultiValueMap<String, String>): Any? {
            if(!aParam.getFirst(aArg.getKey()).isNullOrEmpty())
                return aParam.getFirst(aArg.getKey())
            else
                return aArg.getDefault()
        }
    }
}