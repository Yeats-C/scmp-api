package com.aiqin.bms.scmp.api.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.io.IOException;

public class ResponseBodyMapper extends Jackson2ObjectMapperBuilder {
    @SuppressWarnings("unchecked")
    @Override
    public <T extends ObjectMapper> T build() {
        ObjectMapper om = new ObjectMapper();
        om.getSerializerProvider().setNullValueSerializer(new JsonSerializer<Object>() {
            @Override
            public void serialize(Object value, JsonGenerator jsonGenerator, SerializerProvider provider) throws IOException, JsonProcessingException {
                jsonGenerator.writeObject(null);
            }
        });
        configure(om);
        return (T) om;
    }

}
