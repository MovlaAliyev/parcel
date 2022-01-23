package com.parcel.ms.auth.config;


import com.google.gson.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import springfox.documentation.spring.web.json.Json;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Configuration
public class MessageConvertorConfig {

    @Bean
    public GsonHttpMessageConverter gsonHttpMessageConverter(){
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Json.class, new SwaggerSerializer())
                //.registerTypeAdapter(LocalDate.class, new LocalDateSerializer())
                //.registerTypeAdapter(LocalTime.class, new LocalTimeSerializer())
                //.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer())
                //.registerTypeAdapter(LocalDate.class, new LocalDateDeserializer())
                //.registerTypeAdapter(LocalTime.class, new LocalTimeDeserializer())
                //.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeDeserializer())
                .create();
        GsonHttpMessageConverter converter = new GsonHttpMessageConverter();
        converter.setGson(gson);
        return converter;
    }

    public static class SwaggerSerializer implements JsonSerializer<Json> {
        @Override
        public JsonElement serialize(Json json, Type type, JsonSerializationContext context) {
            return JsonParser.parseString(json.value());
        }
    }

    /*public static class LocalDateSerializer implements JsonSerializer<LocalDate> {
        @Override
        public JsonPrimitive serialize(LocalDate date, Type type, JsonSerializationContext context) {
            return new JsonPrimitive(date.format(DateTimeFormatter.ISO_LOCAL_DATE));
        }
    }

    public static class LocalTimeSerializer implements JsonSerializer<LocalTime> {
        @Override
        public JsonPrimitive serialize(LocalTime time, Type type, JsonSerializationContext context) {
            return new JsonPrimitive(time.format(DateTimeFormatter.ISO_LOCAL_TIME));
        }
    }

    public static class LocalDateTimeSerializer implements JsonSerializer<LocalDateTime> {
        @Override
        public JsonPrimitive serialize(LocalDateTime dateTime, Type type, JsonSerializationContext context) {
            return new JsonPrimitive(dateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        }
    }

    public static class LocalDateDeserializer implements JsonDeserializer<LocalDate> {
        @Override
        public LocalDate deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            return LocalDate.parse(json.getAsString(), DateTimeFormatter.ISO_LOCAL_DATE);
        }
    }

    public static class LocalTimeDeserializer implements JsonDeserializer<LocalTime> {
        @Override
        public LocalTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            return LocalTime.parse(json.getAsString(), DateTimeFormatter.ISO_LOCAL_TIME);
        }
    }

    public static class LocalDateTimeDeserializer implements JsonDeserializer<LocalDateTime> {
        @Override
        public LocalDateTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            return LocalDateTime.parse(json.getAsString(), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        }
    }*/

}
