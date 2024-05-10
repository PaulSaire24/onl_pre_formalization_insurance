package com.bbva.rbvd.lib.rbvd118.util;

import com.bbva.rbvd.dto.cicsconnection.icr2.ICR2Request;
import com.bbva.rbvd.dto.insrncsale.commons.HolderDTO;
import com.bbva.rbvd.dto.insrncsale.policy.*;
import com.bbva.rbvd.dto.preformalization.dto.InsuranceDTO;
import com.bbva.rbvd.dto.preformalization.RelatedContract;
import com.bbva.rbvd.dto.preformalization.util.ConstantsUtil;
import com.bbva.rbvd.lib.rbvd118.dummies.ICR2HelperDummy;
import com.bbva.rbvd.lib.rbvd118.impl.business.ICR2Business;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class ICR2HelperTest {

    @InjectMocks
    private ICR2Business icr2Helper;

    @Mock
    private ICR2Request icr2Request;

    @Mock
    private PolicyDTO preformalizationRequest;

    @Mock
    private HolderDTO holder;

    @Mock
    private PolicyInstallmentPlanDTO installmentPlan;

    @Mock
    private InsuredAmountDTO insuredAmount;

    @Mock
    private TotalAmountDTO totalAmount;

    @Mock
    private ParticipantDTO participant;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        // Set holder defaults
        when(holder.getId()).thenReturn(ICR2HelperDummy.id);
        when(holder.getIdentityDocument()).thenReturn(ICR2HelperDummy.identityDocumentDTO);

        // Set InstallmentPlan default
        when(installmentPlan.getStartDate()).thenReturn(ICR2HelperDummy.startDate);
        when(installmentPlan.getTotalNumberInstallments()).thenReturn(ICR2HelperDummy.totalNumberOfInstallments);
        when(installmentPlan.getPaymentAmount()).thenReturn(ICR2HelperDummy.paymentAmountDTO);

        // Set participant defaults
        when(participant.getParticipantType()).thenReturn(ICR2HelperDummy.paymentManagerParticipantTypeDTO);
        when(participant.getId()).thenReturn(ICR2HelperDummy.id);
        when(participant.getIdentityDocument()).thenReturn(ICR2HelperDummy.identityDocumentDTO);

        // Set preformalizationRequest defaults
        when(preformalizationRequest.getPolicyNumber()).thenReturn(ICR2HelperDummy.preformalizationRequest.getPolicyNumber());
        when(preformalizationRequest.getProduct()).thenReturn(ICR2HelperDummy.preformalizationRequest.getProduct());
        when(preformalizationRequest.getValidityPeriod()).thenReturn(ICR2HelperDummy.preformalizationRequest.getValidityPeriod());
        when(preformalizationRequest.getFirstInstallment()).thenReturn(ICR2HelperDummy.preformalizationRequest.getFirstInstallment());
        when(preformalizationRequest.getBusinessAgent()).thenReturn(ICR2HelperDummy.preformalizationRequest.getBusinessAgent());
        when(preformalizationRequest.getPromoter()).thenReturn(ICR2HelperDummy.preformalizationRequest.getPromoter());
        when(preformalizationRequest.getBank()).thenReturn(ICR2HelperDummy.preformalizationRequest.getBank());
        when(preformalizationRequest.getInsuranceCompany()).thenReturn(ICR2HelperDummy.preformalizationRequest.getInsuranceCompany());
        when(preformalizationRequest.getQuotationId()).thenReturn(ICR2HelperDummy.preformalizationRequest.getQuotationId());
        when(preformalizationRequest.getParticipants()).thenReturn(ICR2HelperDummy.preformalizationRequest.getParticipants());
        when(preformalizationRequest.getPaymentMethod()).thenReturn(ICR2HelperDummy.preformalizationRequest.getPaymentMethod());
        when(preformalizationRequest.getRelatedContracts()).thenReturn(ICR2HelperDummy.preformalizationRequest.getRelatedContracts());
    }

    @Test
    public void setHolderDetailsShouldSetCorrectValuesWhenHolderIsNotNull() {
        ICR2Request icr2Request = new ICR2Request();
        icr2Helper.setHolderDetails(icr2Request, holder);

        assertEquals(ICR2HelperDummy.id, icr2Request.getCODASE());
        assertEquals(ICR2HelperDummy.documentType, icr2Request.getTIPDOC());
        assertEquals(ICR2HelperDummy.documentNumber, icr2Request.getNUMASE());
    }

    @Test
    public void setHolderDetailsShouldNotSetValuesWhenHolderIsNull() {
        ICR2Request icr2Request = new ICR2Request();
        icr2Helper.setHolderDetails(icr2Request, null);

        assertNull(icr2Request.getCODASE());
        assertNull(icr2Request.getTIPDOC());
        assertNull(icr2Request.getNUMASE());
    }

    @Test
    public void setInstallmentPlanDetailsShouldSetCorrectValuesWhenInstallmentPlanIsNotNull() {

        ICR2Request icr2Request = new ICR2Request();
        icr2Helper.setInstallmentPlanDetails(icr2Request, installmentPlan);

        assertEquals(ICR2HelperDummy.startDate.toString(), icr2Request.getFECPAG());
        assertEquals(ICR2HelperDummy.totalNumberOfInstallments.toString(), icr2Request.getNUMCUO());
        assertEquals(ICR2HelperDummy.paymentAmount.toString(), icr2Request.getMTOCUO());
        assertEquals(ICR2HelperDummy.paymentCurrency, icr2Request.getDIVCUO());
    }

    @Test
    public void setInstallmentPlanDetailsShouldNotSetValuesWhenInstallmentPlanIsNull() {
        ICR2Request icr2Request = new ICR2Request();
        icr2Helper.setInstallmentPlanDetails(icr2Request, null);

        assertNull(icr2Request.getFECPAG());
        assertNull(icr2Request.getNUMCUO());
        assertNull(icr2Request.getMTOCUO());
        assertNull(icr2Request.getDIVCUO());
    }

    @Test
    public void setInsuredAmountDetailsShouldSetCorrectValuesWhenInsuredAmountIsNotNull() {
        when(insuredAmount.getAmount()).thenReturn(Double.valueOf(5000));
        when(insuredAmount.getCurrency()).thenReturn("PEN");

        ICR2Request icr2Request = new ICR2Request();
        icr2Helper.setInsuredAmountDetails(icr2Request, insuredAmount);

        assertEquals("5000.0", icr2Request.getSUMASE());
        assertEquals("PEN", icr2Request.getDIVSUM());
    }

    @Test
    public void setInsuredAmountDetailsShouldNotSetValuesWhenInsuredAmountIsNull() {
        ICR2Request icr2Request = new ICR2Request();
        icr2Helper.setInsuredAmountDetails(icr2Request, null);

        assertNull(icr2Request.getSUMASE());
        assertNull(icr2Request.getDIVSUM());
    }

    @Test
    public void setParticipantDetailsShouldSetCorrectValuesWhenParticipantIsNotNullAndRoleIsPaymentManager() {
        ICR2Request icr2Request = new ICR2Request();
        icr2Helper.setParticipantDetails(icr2Request, preformalizationRequest, ConstantsUtil.Participant.PAYMENT_MANAGER);

        assertEquals(ICR2HelperDummy.id, icr2Request.getCODRSP());
        assertEquals(ICR2HelperDummy.documentType, icr2Request.getTIPDO1());
        assertEquals(ICR2HelperDummy.documentNumber, icr2Request.getNUMRSP());
    }

    @Test
    public void setParticipantDetailsShouldSetCorrectValuesWhenParticipantIsNotNullAndRoleIsLegalRepresentative() {
        ICR2Request icr2Request = new ICR2Request();
        when(participant.getParticipantType()).thenReturn(ICR2HelperDummy.legalRepresentativeParticipantTypeDTO);

        icr2Helper.setParticipantDetails(icr2Request, preformalizationRequest, ConstantsUtil.Participant.LEGAL_REPRESENTATIVE);

        assertEquals(ICR2HelperDummy.id, icr2Request.getCODRPL());
        assertEquals(ICR2HelperDummy.documentType, icr2Request.getTIPDOR());
        assertEquals(ICR2HelperDummy.documentNumber, icr2Request.getNUMRPL());
    }

    @Test
    public void setParticipantDetailsShouldNotSetValuesWhenParticipantIsNull() {
        ICR2Request icr2Request = new ICR2Request();
        icr2Helper.setParticipantDetails(icr2Request, preformalizationRequest, "OTHER_ROLE");

        assertNull(icr2Request.getCODRSP());
        assertNull(icr2Request.getTIPDO1());
        assertNull(icr2Request.getNUMRSP());
    }

    @Test
    public void setTotalAmountDetailsShouldSetCorrectValuesWhenTotalAmountIsNotNull() {
        when(totalAmount.getAmount()).thenReturn(10000.0);
        when(totalAmount.getCurrency()).thenReturn("PEN");

        ICR2Request icr2Request = new ICR2Request();
        icr2Helper.setTotalAmountDetails(icr2Request, totalAmount);

        assertEquals("10000.0", icr2Request.getPRITOT());
        assertEquals("PEN", icr2Request.getDIVPRI());
    }

    @Test
    public void setTotalAmountDetailsShouldNotSetValuesWhenTotalAmountIsNull() {
        ICR2Request icr2Request = new ICR2Request();
        icr2Helper.setTotalAmountDetails(icr2Request, null);

        assertNull(icr2Request.getPRITOT());
        assertNull(icr2Request.getDIVPRI());
    }

    @Test
    public void setBasicDetailsShouldSetCorrectValuesWhenPreformalizationRequestIsNotNull() {
        icr2Helper.setBasicDetails(icr2Request, preformalizationRequest);

        verify(icr2Request, times(1)).setNUMPOL(preformalizationRequest.getPolicyNumber());
        verify(icr2Request, times(1)).setCODPRO(preformalizationRequest.getProduct().getId());
        verify(icr2Request, times(1)).setFECINI(preformalizationRequest.getValidityPeriod().getStartDate().toString());
        verify(icr2Request, times(1)).setCODMOD(preformalizationRequest.getProduct().getPlan().getId());
        verify(icr2Request, times(1)).setCOBRO(preformalizationRequest.getFirstInstallment().getIsPaymentRequired() ? "S" : "N");
        verify(icr2Request, times(1)).setGESTOR(preformalizationRequest.getBusinessAgent().getId());
        verify(icr2Request, times(1)).setPRESEN(preformalizationRequest.getPromoter().getId());
        verify(icr2Request, times(1)).setCODBAN(preformalizationRequest.getBank().getId());
        verify(icr2Request, times(1)).setOFICON(preformalizationRequest.getBank().getBranch().getId());
        verify(icr2Request, times(1)).setCODCIA(preformalizationRequest.getInsuranceCompany().getId());
        verify(icr2Request, times(1)).setNUMCOTIZ(preformalizationRequest.getQuotationId());
    }

    @Test
    public void setBasicDetailsShouldNotSetValuesWhenPreformalizationRequestIsNull() {
        icr2Helper.setBasicDetails(icr2Request, null);

        verify(icr2Request, never()).setNUMPOL(anyString());
        verify(icr2Request, never()).setCODPRO(anyString());
        verify(icr2Request, never()).setFECINI(anyString());
        verify(icr2Request, never()).setCODMOD(anyString());
        verify(icr2Request, never()).setCOBRO(anyString());
        verify(icr2Request, never()).setGESTOR(anyString());
        verify(icr2Request, never()).setPRESEN(anyString());
        verify(icr2Request, never()).setCODBAN(anyString());
        verify(icr2Request, never()).setOFICON(anyString());
        verify(icr2Request, never()).setCODCIA(anyString());
        verify(icr2Request, never()).setNUMCOTIZ(anyString());
    }

    @Test
    public void setPaymentMethodDetailsShouldSetCorrectValuesWhenPaymentMethodIsNotNull() {
        icr2Helper.setPaymentMethodDetails(icr2Request, preformalizationRequest.getPaymentMethod());

        verify(icr2Request, times(1)).setMTDPGO(preformalizationRequest.getPaymentMethod().getPaymentType());
        verify(icr2Request, times(1)).setTFOPAG(preformalizationRequest.getPaymentMethod().getInstallmentFrequency());
    }

    @Test
    public void setPaymentMethodDetailsShouldNotSetValuesWhenPaymentMethodIsNull() {
        icr2Helper.setPaymentMethodDetails(icr2Request, null);

        verify(icr2Request, never()).setMTDPGO(anyString());
        verify(icr2Request, never()).setTFOPAG(anyString());
    }

    @Test
    public void setRelatedContractDetailsShouldSetCorrectValuesWhenRelatedContractIsNotNull() {
        RelatedContractDTO relatedContract = preformalizationRequest.getRelatedContracts().get(0);
        icr2Helper.setRelatedContractDetails(icr2Request, relatedContract);

        verify(icr2Request).setNROCTA(relatedContract.getNumber());
        verify(icr2Request).setMEDPAG(relatedContract.getContractDetails().getProductType().getId());
        verify(icr2Request).setTCONVIN(relatedContract.getContractDetails().getContractType());
        verify(icr2Request).setCONVIN(relatedContract.getContractDetails().getContractId());
    }

    @Test
    public void setRelatedContractDetailsShouldNotSetValuesWhenRelatedContractIsNull() {
        icr2Helper.setRelatedContractDetails(icr2Request, null);

        verify(icr2Request, never()).setNROCTA(anyString());
        verify(icr2Request, never()).setMEDPAG(anyString());
        verify(icr2Request, never()).setTCONVIN(anyString());
        verify(icr2Request, never()).setCONVIN(anyString());
    }
}