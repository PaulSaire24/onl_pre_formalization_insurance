package com.bbva.rbvd.dto.preformalization.mock;


import java.io.IOException;
import java.io.InputStream;

public class ObjectMapperHelper {

    private static final ObjectMapperHelper INSTANCE = new ObjectMapperHelper();
    private ObjectMapperHelper mapper;

    private ObjectMapperHelper() { this.mapper = new ObjectMapperHelper(); }

    public static ObjectMapperHelper getInstance() { return INSTANCE; }

    public <T> T readValue(final InputStream src, final Class<T> valueType) throws IOException {
        return mapper.readValue(src, valueType);
    }

}
