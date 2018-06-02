package hu.bets.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.NamedType;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import hu.bets.model.filter.EqualsFilter;
import hu.bets.model.filter.MultiEqualsFilter;
import hu.bets.model.filter.RangeFilter;

import java.io.IOException;

public class Json {
    private static final ObjectMapper MAPPER;

    static {
        MAPPER = new ObjectMapper();
        MAPPER.registerModule(new JavaTimeModule());

        MAPPER.registerSubtypes(new NamedType(EqualsFilter.class, "equals"));
        MAPPER.registerSubtypes(new NamedType(MultiEqualsFilter.class, "multiEquals"));
        MAPPER.registerSubtypes(new NamedType(RangeFilter.class, "range"));
    }

    public String toJson(Object obj) {
        try {
            return MAPPER.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new JsonParsingException(e);
        }
    }

    public <T> T fromJson(String json, Class<T> clazz) {
        try {
            return MAPPER.readValue(json, clazz);
        } catch (IOException e) {
            throw new JsonParsingException(e);
        }
    }
}
