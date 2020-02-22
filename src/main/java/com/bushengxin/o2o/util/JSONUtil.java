package com.bushengxin.o2o.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JSONUtil {
    public static String stringify(Object o) {
        ObjectMapper mapper = new ObjectMapper();
        String jsonstr = null;
        try {
            jsonstr = mapper.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return jsonstr;
    }
}
