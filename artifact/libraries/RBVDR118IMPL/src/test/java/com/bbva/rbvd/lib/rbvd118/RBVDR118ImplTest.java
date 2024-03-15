package com.bbva.rbvd.lib.rbvd118;

import com.bbva.apx.exception.business.BusinessException;
import com.bbva.elara.configuration.manager.application.ApplicationConfigurationService;
import com.bbva.pisd.lib.r012.PISDR012;
import com.bbva.pisd.lib.r401.PISDR401;
import com.bbva.rbvd.dto.cicsconnection.icr2.ICMRYS2;
import com.bbva.rbvd.dto.cicsconnection.icr2.ICR2Request;
import com.bbva.rbvd.dto.cicsconnection.icr2.ICR2Response;
import com.bbva.rbvd.dto.insrncsale.dao.InsuranceContractDAO;
import com.bbva.rbvd.dto.insrncsale.dao.RequiredFieldsEmissionDAO;
import com.bbva.rbvd.dto.insrncsale.policy.BusinessAgentDTO;
import com.bbva.rbvd.dto.insrncsale.policy.PromoterDTO;
import com.bbva.rbvd.dto.insrncsale.utils.RBVDErrors;
import com.bbva.rbvd.dto.insrncsale.utils.RBVDProperties;
import com.bbva.rbvd.dto.preformalization.PreformalizationDTO;
import com.bbva.rbvd.lib.r047.RBVDR047;
import com.bbva.rbvd.lib.rbvd118.dummies.ICR2HelperDummy;
import com.bbva.rbvd.lib.rbvd118.impl.RBVDR118Impl;
import com.bbva.rbvd.lib.rbvd118.impl.util.ICR2Helper;
import com.bbva.rbvd.lib.rbvd118.impl.util.MapperHelper;
import com.bbva.rbvd.lib.rbvd118.impl.util.ValidationUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.bbva.pisd.dto.insurance.utils.PISDConstants.CHANNEL_GLOMO;
import static com.bbva.rbvd.lib.rbvd118.impl.RBVDR118Impl.*;
import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RBVDR118ImplTest {

    @InjectMocks
    private RBVDR118Impl rbvdr118Impl;

    @Mock
    private MapperHelper mapperHelper;

    @Mock
    private PISDR012 pisdR012;

    @Mock
    private PISDR401 pisdR401;

    @Mock
    private RBVDR047 rbvdR047;

    @Mock
    private ValidationUtil validationUtil;

    @Mock
    private ApplicationConfigurationService applicationConfigurationService;

    @Mock
    private PreformalizationDTO requestBody;

    @Mock
    private ICR2Response icr2Response;

    @Mock
    private RequiredFieldsEmissionDAO emissionDao;

    @Mock
    private ICR2Helper icr2Helper;

    @Mock
    private ICMRYS2 icmrys2;

    @Before
    public void setUp() {
        when(requestBody.getSaleChannelId()).thenReturn("TM");
        when(applicationConfigurationService.getProperty(KEY_PIC_CODE)).thenReturn("PicCode");
        when(applicationConfigurationService.getProperty(CHANNEL_GLOMO)).thenReturn("Glomo");
        when(applicationConfigurationService.getProperty(KEY_CONTACT_CENTER_CODE)).thenReturn("TM");
        when(applicationConfigurationService.getProperty(CHANNEL_CONTACT_DETAIL)).thenReturn("Glomo;Other");
        when(requestBody.getAap()).thenReturn("NotGlomo");
        when(applicationConfigurationService.getProperty(KEY_AGENT_PROMOTER_CODE)).thenReturn("DefaultCode");
        when(icr2Response.getIcmrys2()).thenReturn(icmrys2);

        when(requestBody.getInstallmentPlan()).thenReturn(ICR2HelperDummy.preformalizationRequest.getInstallmentPlan());
        when(requestBody.getProduct()).thenReturn(ICR2HelperDummy.preformalizationRequest.getProduct());
        when(requestBody.getInsuranceValidityPeriod()).thenReturn(ICR2HelperDummy.preformalizationRequest.getInsuranceValidityPeriod());
        when(requestBody.getFirstInstallment()).thenReturn(ICR2HelperDummy.preformalizationRequest.getFirstInstallment());
        when(requestBody.getBank()).thenReturn(ICR2HelperDummy.preformalizationRequest.getBank());
    }

    @Test
    public void shouldExecutePreFormalizationSuccessfully() {
        when(mapperHelper.createSingleArgument(any(), any())).thenReturn(new HashMap<>());
        when(pisdR012.executeGetASingleRow(any(), any())).thenReturn(new HashMap<>());
        when(pisdR401.executeGetProductById(any(), any())).thenReturn(new HashMap<>());
        when(validationUtil.validateResponseQueryGetRequiredFields(any(), any())).thenReturn(emissionDao);
        when(icr2Helper.mapRequestFromPreformalizationBody(any())).thenReturn(new ICR2Request());
        when(rbvdR047.executePolicyRegistration(any())).thenReturn(icr2Response);
        when(icmrys2.getOFICON()).thenReturn("123");
        when(applicationConfigurationService.getProperty(any())).thenReturn("TM");
        when(mapperHelper.buildInsuranceContract(any(), any(), any(), any())).thenReturn(new InsuranceContractDAO());
        when(mapperHelper.createSaveContractArguments(any())).thenReturn(new HashMap<>());
        when(pisdR012.executeInsertSingleRow(any(), any(), any(), any(), any(), any(), any(), any(), any(), any(), any(), any())).thenReturn(1);

        PreformalizationDTO result = rbvdr118Impl.executePreFormalization(requestBody);

        assertNotNull(result);
    }

    @Test()
    public void shouldThrowExceptionWhenPolicyExists() {
        when(mapperHelper.createSingleArgument(any(), any())).thenReturn(new HashMap<>());
        Map<String, Object> response = new HashMap<>();
        response.put(RBVDProperties.FIELD_RESULT_NUMBER.getValue(), BigDecimal.ONE);
        when(pisdR012.executeGetASingleRow(any(), any())).thenReturn(response);

        assertThrows(BusinessException.class, () -> rbvdr118Impl.validatePolicyExists(requestBody));
    }

    @Test()
    public void shouldThrowExceptionWhenDigitalSaleNotImplemented() {
        when(applicationConfigurationService.getProperty(CHANNEL_CONTACT_DETAIL)).thenReturn("Glomo;Other");
        when(requestBody.getAap()).thenReturn("Other");


        assertThrows(BusinessException.class, () -> rbvdr118Impl.validateDigitalSale(requestBody));
    }

    @Test
    public void shouldValidateDigitalSaleWhenSaleChannelIsPicCode() {
        when(requestBody.getSaleChannelId()).thenReturn("PicCode");
        when(applicationConfigurationService.getProperty(KEY_PIC_CODE)).thenReturn("PicCode");

        rbvdr118Impl.validateDigitalSale(requestBody);
    }

    @Test
    public void shouldValidateDigitalSaleWhenSaleChannelIsNotTelemarketing() {
        when(requestBody.getSaleChannelId()).thenReturn("NotTM");
        when(applicationConfigurationService.getProperty(KEY_PIC_CODE)).thenReturn("PicCode");

        rbvdr118Impl.validateDigitalSale(requestBody);
    }

    @Test
    public void shouldValidateDigitalSaleWhenSaleChannelIsNotContactCenter() {
        when(requestBody.getSaleChannelId()).thenReturn("NotCC");
        when(applicationConfigurationService.getProperty(KEY_PIC_CODE)).thenReturn("PicCode");

        rbvdr118Impl.validateDigitalSale(requestBody);
    }

    @Test()
    public void shouldThrowExceptionWhenDigitalSaleIsGlomo() {
        when(applicationConfigurationService.getProperty(CHANNEL_GLOMO)).thenReturn("Glomo");
        when(requestBody.getAap()).thenReturn("Glomo");

        assertThrows(BusinessException.class, () -> rbvdr118Impl.validateDigitalSale(requestBody));
    }

    @Test()
    public void shouldThrowExceptionWhenDigitalSaleIsInContactDetail() {
        when(applicationConfigurationService.getProperty(CHANNEL_GLOMO)).thenReturn("Glomo");
        when(applicationConfigurationService.getProperty(CHANNEL_CONTACT_DETAIL)).thenReturn("Glomo;Other");
        when(requestBody.getAap()).thenReturn("Glomo");
        assertThrows(BusinessException.class, () -> rbvdr118Impl.validateDigitalSale(requestBody));
    }

    @Test
    public void shouldSetBusinessAgentAndPromoterWhenDigitalSaleIsNotGlomoOrInContactDetail() {
        rbvdr118Impl.validateDigitalSale(requestBody);

        verify(requestBody).setBusinessAgent(any(BusinessAgentDTO.class));
        verify(requestBody).setPromoter(any(PromoterDTO.class));
    }

    @Test
    public void shouldReturnFalseWhenEndorsementNotValid() {
        when(requestBody.getParticipants()).thenReturn(new ArrayList<>());

        boolean result = rbvdr118Impl.validateEndorsement(requestBody);

        assertFalse(result);
    }

    @Test(expected = BusinessException.class)
    public void shouldThrowExceptionWhenInsertionFails() {
        rbvdr118Impl.validateInsertion(0, RBVDErrors.INSERTION_ERROR_IN_CONTRACT_TABLE);
    }
}