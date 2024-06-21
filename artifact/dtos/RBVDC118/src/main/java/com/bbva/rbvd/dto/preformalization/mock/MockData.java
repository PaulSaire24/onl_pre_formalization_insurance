package com.bbva.rbvd.dto.preformalization.mock;


public class MockData {

    private static final MockData INSTANCE = new MockData();

    private final ObjectMapperHelper objectMapperHelper;

    private MockData() {objectMapperHelper = ObjectMapperHelper.getInstance();}

    public static MockData getInstance() {
        return INSTANCE;
    }

    

}
