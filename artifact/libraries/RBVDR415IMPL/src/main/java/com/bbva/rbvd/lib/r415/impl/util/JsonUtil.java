package com.bbva.rbvd.lib.r415.impl.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;


import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class JsonUtil {

    private static final String DATE = "yyyy-MM-dd";
    public static final JsonUtil INSTANCE = new JsonUtil();

    private final Gson gson;

    private JsonUtil() {
        this.gson = new GsonBuilder()
                .setDateFormat(DATE)
                .registerTypeHierarchyAdapter(Calendar.class, new CalendarAdapter())
                .create();
    }

    public static JsonUtil getInstance() { return INSTANCE; }

    public String serialization(Object o) { return this.gson.toJson(o); }

    class CalendarAdapter implements JsonSerializer<Calendar> {

        @Override
        public JsonElement serialize(Calendar src, Type typeOfSrc, JsonSerializationContext context) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            return new JsonPrimitive(dateFormat.format(src.getTime()));
        }

    }

}
