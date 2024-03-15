package com.bbva.rbvd.lib.rbvd118.util;

import com.bbva.elara.configuration.manager.application.ApplicationConfigurationService;
import com.bbva.rbvd.dto.cicsconnection.icr2.ICMRYS2;
import com.bbva.rbvd.dto.cicsconnection.icr2.ICR2Response;
import com.bbva.rbvd.dto.insrncsale.dao.InsuranceContractDAO;
import com.bbva.rbvd.dto.insrncsale.dao.RequiredFieldsEmissionDAO;
import com.bbva.rbvd.dto.insrncsale.utils.RBVDProperties;
import com.bbva.rbvd.dto.preformalization.PreformalizationDTO;
import com.bbva.rbvd.lib.rbvd118.dummies.ICR2HelperDummy;
import com.bbva.rbvd.lib.rbvd118.impl.util.MapperHelper;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class MapperHelperTest {
    @InjectMocks
    private MapperHelper mapperHelper;
    @Mock
    private ApplicationConfigurationService applicationConfigurationService;
    @Mock
    private PreformalizationDTO preformalizationBody;
    @Mock
    private RequiredFieldsEmissionDAO emissionDao;
    @Mock
    private InsuranceContractDAO contractDaoMock;
    @Mock
    private ICMRYS2 icmrys2;
    @Mock
    private ICR2Response icr2Response;

    @Before
    public void setUp() {
        when(icr2Response.getIcmrys2()).thenReturn(icmrys2);

        // Set preformalizationRequest defaults
        when(preformalizationBody.getPolicyNumber()).thenReturn(ICR2HelperDummy.preformalizationRequest.getPolicyNumber());
        when(preformalizationBody.getProduct()).thenReturn(ICR2HelperDummy.preformalizationRequest.getProduct());
        when(preformalizationBody.getInsuranceValidityPeriod()).thenReturn(ICR2HelperDummy.preformalizationRequest.getInsuranceValidityPeriod());
        when(preformalizationBody.getFirstInstallment()).thenReturn(ICR2HelperDummy.preformalizationRequest.getFirstInstallment());
        when(preformalizationBody.getInstallmentPlan()).thenReturn(ICR2HelperDummy.preformalizationRequest.getInstallmentPlan());
        when(preformalizationBody.getBusinessAgent()).thenReturn(ICR2HelperDummy.preformalizationRequest.getBusinessAgent());
        when(preformalizationBody.getPromoter()).thenReturn(ICR2HelperDummy.preformalizationRequest.getPromoter());
        when(preformalizationBody.getBank()).thenReturn(ICR2HelperDummy.preformalizationRequest.getBank());
        when(preformalizationBody.getInsuranceCompany()).thenReturn(ICR2HelperDummy.preformalizationRequest.getInsuranceCompany());
        when(preformalizationBody.getQuotationId()).thenReturn(ICR2HelperDummy.preformalizationRequest.getQuotationId());
        when(preformalizationBody.getParticipants()).thenReturn(ICR2HelperDummy.preformalizationRequest.getParticipants());
        when(preformalizationBody.getPaymentMethod()).thenReturn(ICR2HelperDummy.preformalizationRequest.getPaymentMethod());
        when(preformalizationBody.getRelatedContracts()).thenReturn(ICR2HelperDummy.preformalizationRequest.getRelatedContracts());
        when(preformalizationBody.getHolder()).thenReturn(ICR2HelperDummy.preformalizationRequest.getHolder());
        when(preformalizationBody.getTotalAmount()).thenReturn(ICR2HelperDummy.preformalizationRequest.getTotalAmount());
        when(preformalizationBody.getInsuredAmount()).thenReturn(ICR2HelperDummy.preformalizationRequest.getInsuredAmount());
    }

    @Test
    public void shouldCreateSingleArgument() {
        String argument = "frequencyType";
        String parameterName = "paymentFrequencyType";
        when(applicationConfigurationService.getProperty(argument)).thenReturn("MONTHLY");

        Map<String, Object> result = mapperHelper.createSingleArgument(argument, parameterName);

        assertEquals(argument, result.get(parameterName));
    }

    @Test
    public void shouldBuildInsuranceContractWithEndorsement() {
        preformalizationBody.getPaymentMethod().setPaymentType("DIRECT_DEBIT");
        when(icmrys2.getNUMCON()).thenReturn("1234567890123123123");
        when(emissionDao.getContractDurationType()).thenReturn("A");
        when(emissionDao.getContractDurationNumber()).thenReturn(BigDecimal.valueOf(12));

        InsuranceContractDAO result = mapperHelper.buildInsuranceContract(preformalizationBody, emissionDao, icr2Response, true);

        assertEquals("S", result.getEndorsementPolicyIndType());
        assertEquals("N", result.getInsurPendingDebtIndType());
        assertEquals("S", result.getAutomaticDebitIndicatorType());
    }

    @Test
    public void shouldBuildInsuranceContractWithoutEndorsement() {
        when(icmrys2.getNUMCON()).thenReturn("1234567890123123123");
        preformalizationBody.getFirstInstallment().setIsPaymentRequired(false);
        preformalizationBody.getPaymentMethod().setPaymentType("OTHER");
        when(emissionDao.getContractDurationType()).thenReturn("M");
        when(emissionDao.getContractDurationNumber()).thenReturn(BigDecimal.valueOf(6));

        InsuranceContractDAO result = mapperHelper.buildInsuranceContract(preformalizationBody, emissionDao, icr2Response, false);

        assertEquals("N", result.getEndorsementPolicyIndType());
        assertEquals("S", result.getInsurPendingDebtIndType());
        assertEquals("N", result.getAutomaticDebitIndicatorType());
    }

    @Test
    public void shouldValidatePrevPendBillRcptsNumberForVidaEasyYesProduct() {
        preformalizationBody.getProduct().setId(RBVDProperties.INSURANCE_PRODUCT_TYPE_VIDA_EASYYES.getValue());
        when(emissionDao.getContractDurationNumber()).thenReturn(BigDecimal.valueOf(5));

        mapperHelper.validatePrevPendBillRcptsNumber(preformalizationBody, emissionDao, contractDaoMock);

        verify(contractDaoMock).setPrevPendBillRcptsNumber(BigDecimal.valueOf(5));
    }

    @Test
    public void shouldValidatePrevPendBillRcptsNumberForVida2Product() {
        preformalizationBody.getProduct().setId(RBVDProperties.INSURANCE_PRODUCT_TYPE_VIDA_2.getValue());
        preformalizationBody.getFirstInstallment().setIsPaymentRequired(true);
        when(emissionDao.getContractDurationNumber()).thenReturn(BigDecimal.valueOf(5));

        mapperHelper.validatePrevPendBillRcptsNumber(preformalizationBody, emissionDao, contractDaoMock);

        verify(contractDaoMock).setPrevPendBillRcptsNumber(BigDecimal.valueOf(5));
    }

    @Test
    public void shouldValidatePrevPendBillRcptsNumberForOtherProductWithPaymentRequired() {
        preformalizationBody.getFirstInstallment().setIsPaymentRequired(true);

        mapperHelper.validatePrevPendBillRcptsNumber(preformalizationBody, emissionDao, contractDaoMock);

        verify(contractDaoMock).setPrevPendBillRcptsNumber(BigDecimal.valueOf(ICR2HelperDummy.totalNumberOfInstallments - 1L));
    }

    @Test
    public void shouldValidatePrevPendBillRcptsNumberForOtherProductWithoutPaymentRequired() {
        preformalizationBody.getFirstInstallment().setIsPaymentRequired(false);

        when(emissionDao.getContractDurationNumber()).thenReturn(BigDecimal.valueOf(ICR2HelperDummy.totalNumberOfInstallments));

        mapperHelper.validatePrevPendBillRcptsNumber(preformalizationBody, emissionDao, contractDaoMock);

        verify(contractDaoMock).setPrevPendBillRcptsNumber(BigDecimal.valueOf(ICR2HelperDummy.totalNumberOfInstallments));
    }

    @Test
    public void shouldGenerateCorrectDateFormat() {
        LocalDate date = LocalDate.now(DateTimeZone.forID("GMT"));
        String result = mapperHelper.generateCorrectDateFormat(date);

        assertNotNull(result);
        assertEquals(result.length(), 10);
    }

    @Test
    public void shouldConvertDateToLocalDate() {
        Date date = ICR2HelperDummy.startDate;
        LocalDate result = mapperHelper.convertDateToLocalDate(date);

        assertNotNull(result);
    }

    @Test
    public void shouldCreateSaveContractArguments() {
        Map<String, Object> result = mapperHelper.createSaveContractArguments(contractDaoMock);

        verify(contractDaoMock, times(1)).getEntityId();
        verify(contractDaoMock, times(1)).getBranchId();
        verify(contractDaoMock, times(1)).getIntAccountId();
        verify(contractDaoMock, times(1)).getFirstVerfnDigitId();
        verify(contractDaoMock, times(1)).getSecondVerfnDigitId();
        verify(contractDaoMock, times(1)).getPolicyQuotaInternalId();
        verify(contractDaoMock, times(1)).getInsuranceProductId();
        verify(contractDaoMock, times(1)).getInsuranceModalityType();
        verify(contractDaoMock, times(1)).getInsuranceCompanyId();
        verify(contractDaoMock, times(1)).getPolicyId();
        verify(contractDaoMock, times(1)).getInsuranceManagerId();
        verify(contractDaoMock, times(1)).getInsurancePromoterId();
        verify(contractDaoMock, times(1)).getContractManagerBranchId();
        verify(contractDaoMock, times(1)).getContractInceptionDate();
        verify(contractDaoMock, times(1)).getInsuranceContractStartDate();
        verify(contractDaoMock, times(1)).getInsuranceContractEndDate();
        verify(contractDaoMock, times(1)).getValidityMonthsNumber();
        verify(contractDaoMock, times(1)).getInsurancePolicyEndDate();
        verify(contractDaoMock, times(1)).getCustomerId();
        verify(contractDaoMock, times(1)).getDomicileContractId();
        verify(contractDaoMock, times(1)).getCardIssuingMarkType();
        verify(contractDaoMock, times(1)).getIssuedReceiptNumber();
        verify(contractDaoMock, times(1)).getPaymentFrequencyId();
        verify(contractDaoMock, times(1)).getPremiumAmount();
        verify(contractDaoMock, times(1)).getSettlePendingPremiumAmount();
        verify(contractDaoMock, times(1)).getCurrencyId();
        verify(contractDaoMock, times(1)).getLastInstallmentDate();
        verify(contractDaoMock, times(1)).getInstallmentPeriodFinalDate();
        verify(contractDaoMock, times(1)).getInsuredAmount();
        verify(contractDaoMock, times(1)).getBeneficiaryType();
        verify(contractDaoMock, times(1)).getRenewalNumber();
        verify(contractDaoMock, times(1)).getCtrctDisputeStatusType();
        verify(contractDaoMock, times(1)).getPeriodNextPaymentDate();
        verify(contractDaoMock, times(1)).getEndorsementPolicyIndType();
        verify(contractDaoMock, times(1)).getInsrncCoContractStatusType();
        verify(contractDaoMock, times(1)).getContractStatusId();
        verify(contractDaoMock, times(1)).getCreationUserId();
        verify(contractDaoMock, times(1)).getUserAuditId();
        verify(contractDaoMock, times(1)).getInsurPendingDebtIndType();
        verify(contractDaoMock, times(1)).getTotalDebtAmount();
        verify(contractDaoMock, times(1)).getPrevPendBillRcptsNumber();
        verify(contractDaoMock, times(1)).getSettlementVarPremiumAmount();
        verify(contractDaoMock, times(1)).getSettlementFixPremiumAmount();
        verify(contractDaoMock, times(1)).getInsuranceCompanyProductId();
        verify(contractDaoMock, times(1)).getAutomaticDebitIndicatorType();
        verify(contractDaoMock, times(1)).getBiometryTransactionId();
        verify(contractDaoMock, times(1)).getTelemarketingTransactionId();
        verify(contractDaoMock, times(1)).getOriginalPaymentSubchannelId();
    }
}