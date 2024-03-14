package com.bbva.rbvd.lib.rbvd118.util;

import com.bbva.apx.exception.business.BusinessException;
import com.bbva.pisd.dto.insurance.utils.PISDProperties;
import com.bbva.rbvd.dto.insrncsale.dao.RequiredFieldsEmissionDAO;
import com.bbva.rbvd.dto.insrncsale.utils.RBVDProperties;
import com.bbva.rbvd.lib.rbvd118.impl.util.ValidationUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ValidationUtilTest {

    @Mock
    private Map<String, Object> responseQueryGetRequiredFields;
    @Mock
    private Map<String, Object> responseQueryGetPaymentPeriod;

    @InjectMocks
    private ValidationUtil validationUtil;

    @Test
    public void shouldValidateResponseQueryGetRequiredFields() {
        when(responseQueryGetRequiredFields.get(RBVDProperties.FIELD_INSURANCE_PRODUCT_ID.getValue())).thenReturn(BigDecimal.ONE);
        when(responseQueryGetRequiredFields.get(RBVDProperties.FIELD_CONTRACT_DURATION_NUMBER.getValue())).thenReturn(BigDecimal.ONE);
        when(responseQueryGetRequiredFields.get(RBVDProperties.FIELD_CONTRACT_DURATION_TYPE.getValue())).thenReturn("A");
        when(responseQueryGetPaymentPeriod.get(RBVDProperties.FIELD_PAYMENT_FREQUENCY_ID.getValue())).thenReturn(BigDecimal.ONE);
        when(responseQueryGetRequiredFields.get(RBVDProperties.FIELD_INSURANCE_COMPANY_QUOTA_ID.getValue())).thenReturn("QuotaId");
        when(responseQueryGetRequiredFields.get(PISDProperties.FIELD_INSURANCE_PRODUCT_DESC.getValue())).thenReturn("ProductDesc");
        when(responseQueryGetRequiredFields.get(PISDProperties.FIELD_INSURANCE_MODALITY_NAME.getValue())).thenReturn("ModalityName");
        when(responseQueryGetPaymentPeriod.get(PISDProperties.FIELD_PAYMENT_FREQUENCY_NAME.getValue())).thenReturn("FrequencyName");
        when(responseQueryGetRequiredFields.get(PISDProperties.FIELD_VEHICLE_BRAND_NAME.getValue())).thenReturn("BrandName");
        when(responseQueryGetRequiredFields.get(PISDProperties.FIELD_VEHICLE_MODEL_NAME.getValue())).thenReturn("ModelName");
        when(responseQueryGetRequiredFields.get(PISDProperties.FIELD_VEHICLE_YEAR_ID.getValue())).thenReturn("YearId");
        when(responseQueryGetRequiredFields.get(PISDProperties.FIELD_VEHICLE_LICENSE_ID.getValue())).thenReturn("LicenseId");
        when(responseQueryGetRequiredFields.get(PISDProperties.FIELD_VEHICLE_GAS_CONVERSION_TYPE.getValue())).thenReturn("GasConversionType");
        when(responseQueryGetRequiredFields.get(PISDProperties.FIELD_VEHICLE_CIRCULATION_SCOPE_TYPE.getValue())).thenReturn("CirculationType");
        when(responseQueryGetRequiredFields.get(PISDProperties.FIELD_COMMERCIAL_VEHICLE_AMOUNT.getValue())).thenReturn(BigDecimal.ONE);

        RequiredFieldsEmissionDAO result = validationUtil.validateResponseQueryGetRequiredFields(responseQueryGetRequiredFields, responseQueryGetPaymentPeriod);

        assertEquals(BigDecimal.ONE, result.getInsuranceProductId());
        assertEquals(BigDecimal.ONE, result.getContractDurationNumber());
        assertEquals("A", result.getContractDurationType());
        assertEquals(BigDecimal.ONE, result.getPaymentFrequencyId());
        assertEquals("QuotaId", result.getInsuranceCompanyQuotaId());
        assertEquals("ProductDesc", result.getInsuranceProductDesc());
        assertEquals("ModalityName", result.getInsuranceModalityName());
        assertEquals("FrequencyName", result.getPaymentFrequencyName());
        assertEquals("BrandName", result.getVehicleBrandName());
        assertEquals("ModelName", result.getVehicleModelName());
        assertEquals("YearId", result.getVehicleYearId());
        assertEquals("LicenseId", result.getVehicleLicenseId());
        assertEquals("GasConversionType", result.getGasConversionType());
        assertEquals("CirculationType", result.getVehicleCirculationType());
        assertEquals(BigDecimal.ONE, result.getCommercialVehicleAmount());
    }

    @Test()
    public void shouldThrowExceptionWhenResponseQueryGetRequiredFieldsIsEmpty() {
        when(responseQueryGetRequiredFields.isEmpty()).thenReturn(true);

        assertThrows(BusinessException.class, () -> validationUtil.validateResponseQueryGetRequiredFields(responseQueryGetRequiredFields, responseQueryGetPaymentPeriod));
    }
}