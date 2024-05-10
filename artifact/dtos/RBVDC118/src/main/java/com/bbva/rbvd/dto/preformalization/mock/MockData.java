package com.bbva.rbvd.dto.preformalization.mock;

import com.bbva.rbvd.dto.preformalization.dto.InsuranceDTO;

import java.io.IOException;

public class MockData {

    private static final MockData INSTANCE = new MockData();

    private final ObjectMapperHelper objectMapperHelper;

    private MockData() {objectMapperHelper = ObjectMapperHelper.getInstance();}

    public static MockData getInstance() {
        return INSTANCE;
    }

    public InsuranceDTO getRequestBodyOfTransaction() throws IOException {
        return objectMapperHelper.readValue(
                Thread.currentThread().getContextClassLoader().getResourceAsStream(
                        "mocks/inputTrxRequest.json"),
                InsuranceDTO.class);
    }

}
