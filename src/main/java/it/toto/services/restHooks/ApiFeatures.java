package it.toto.services.restHooks;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import lombok.extern.slf4j.Slf4j;

import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by toto on 07/10/14.
 */
@Slf4j
@Provider
public class ApiFeatures implements ContextResolver<ObjectMapper> {

    private final ObjectMapper mObjectMapper;

    public ApiFeatures() {

        final ObjectMapper objectMapper = new ObjectMapper()
                .setSerializationInclusion(JsonInclude.Include.ALWAYS)
                .configure(JsonParser.Feature.ALLOW_COMMENTS, true)
                .configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true)
                .configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true)
                .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
                .configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);


        //for deserializing
        final TimeZone tz = TimeZone.getTimeZone("UTC");
        final DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        df.setTimeZone(tz);

        SimpleModule testModule = new SimpleModule();
        testModule.addSerializer(new StdSerializer<Date>(Date.class) {
            @Override
            public void serialize(Date o, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonGenerationException {
                log.debug("serialize {}", o);
                jsonGenerator.writeString(df.format(o));
            }
        });
        testModule.addDeserializer(Date.class, new JsonDeserializer<Date>() {

            @Override
            public Date deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
                final String valueAsString = jsonParser.getValueAsString();
                log.debug("deserialize {}", valueAsString);
                try {
                    return df.parse(valueAsString);
                } catch (ParseException e) {
                    throw new IOException(e);
                }
            }
        });

        objectMapper.registerModule(testModule);

        this.mObjectMapper = objectMapper;


    }

    @Override
    public ObjectMapper getContext(Class<?> type) {
        return mObjectMapper;
    }

}
