package io.xhub.smwall.utlis;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class JsonUtils {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static String toJsonString(Object object) throws JsonProcessingException {
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper.writeValueAsString(object);
    }
}
