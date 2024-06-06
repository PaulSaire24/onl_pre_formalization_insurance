package com.bbva.rbvd.lib.r415.impl.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonUtil {

    public static final JsonUtil INSTANCE = new JsonUtil();

    private final Gson gson;

    private JsonUtil() {
        this.gson = new GsonBuilder().create();
    }

    public static JsonUtil getInstance() { return INSTANCE; }

    public String serialization(Object o) { return this.gson.toJson(o); }

}
