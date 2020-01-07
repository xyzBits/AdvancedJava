package com.dongfang.advanced.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

public class JsonUtils {
    public static String convertObjectToJson(Object target) {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        try {
            return ow.writeValueAsString(target);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "";
        }
    }
}
