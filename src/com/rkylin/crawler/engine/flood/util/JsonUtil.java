package com.rkylin.crawler.engine.flood.util;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtil {

    public static <T> T convertJsonStr2Obj(String jsonStr, Class<T> clazz) throws Exception {
        if (jsonStr == null || jsonStr.length() == 0) {
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.setSerializationInclusion(Include.NON_NULL);
        mapper.setSerializationInclusion(Include.NON_EMPTY);

        return mapper.readValue(jsonStr, clazz);
    }

    public static String convertObj2JsonStr(Object obj) throws Exception {
        if (obj == null) {
            return "";
        }

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.setSerializationInclusion(Include.NON_NULL);
        mapper.setSerializationInclusion(Include.NON_EMPTY);
        String json = mapper.writeValueAsString(obj);

        return json;
    }
}
