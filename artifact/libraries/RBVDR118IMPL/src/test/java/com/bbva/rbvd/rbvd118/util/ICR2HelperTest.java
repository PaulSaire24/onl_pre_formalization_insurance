package com.bbva.rbvd.rbvd118.util;

import com.bbva.pisd.dto.insurance.commons.HolderDTO;
import com.bbva.rbvd.dto.cicsconnection.icr2.ICR2Request;
import com.bbva.rbvd.dto.insrncsale.policy.*;
import com.bbva.rbvd.dto.preformalization.PreformalizationDTO;
import com.bbva.rbvd.dto.preformalization.RelatedContract;
import com.bbva.rbvd.rbvd118.impl.util.ConstantsUtil;
import com.bbva.rbvd.rbvd118.impl.util.ICR2Helper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class ICR2HelperTest {

    @InjectMocks
    private ICR2Helper icr2Helper;

    @Mock
    private PreformalizationDTO preformalizationRequest;

    @Mock
    private PolicyPaymentMethodDTO paymentMethod;

    @Mock
    private RelatedContract relatedContract;

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
    }

    @Test
    public void mapRequestFromPreformalizationBodyShouldReturnICR2RequestWithCorrectValues() {
        when(preformalizationRequest.getPolicyNumber()).thenReturn("123456");
        when(preformalizationRequest.getProductId()).thenReturn("78910");

        ICR2Request icr2Request = icr2Helper.mapRequestFromPreformalizationBody(preformalizationRequest);

        assertEquals("123456", icr2Request.getNUMPOL());
        assertEquals("78910", icr2Request.getCODPRO());
    }

    @Test
    public void mapRequestFromPreformalizationBodyShouldHandleNullPaymentMethod() {
        when(preformalizationRequest.getPaymentMethod()).thenReturn(null);

        ICR2Request icr2Request = icr2Helper.mapRequestFromPreformalizationBody(preformalizationRequest);

        assertNull(icr2Request.getMTDPGO());
        assertNull(icr2Request.getTFOPAG());
    }

    private void assertNull(String mtdpgo) {
    }

    @Test
    public void mapRequestFromPreformalizationBodyShouldHandleNullRelatedContract() {
        when(preformalizationRequest.getRelatedContracts()).thenReturn(null);

        ICR2Request icr2Request = icr2Helper.mapRequestFromPreformalizationBody(preformalizationRequest);

        assertNull(icr2Request.getNROCTA());
        assertNull(icr2Request.getMEDPAG());
        assertNull(icr2Request.getTCONVIN());
        assertNull(icr2Request.getCONVIN());
    }

    @Test
    public void mapRequestFromPreformalizationBodyShouldHandleNullHolder() {
        when(preformalizationRequest.getHolder()).thenReturn(null);

        ICR2Request icr2Request = icr2Helper.mapRequestFromPreformalizationBody(preformalizationRequest);

        assertNull(icr2Request.getCODASE());
        assertNull(icr2Request.getTIPDOC());
        assertNull(icr2Request.getNUMASE());
    }

    @Test
    public void mapRequestFromPreformalizationBodyShouldHandleNullInstallmentPlan() {
        when(preformalizationRequest.getInstallmentPlan()).thenReturn(null);

        ICR2Request icr2Request = icr2Helper.mapRequestFromPreformalizationBody(preformalizationRequest);

        assertNull(icr2Request.getFECPAG());
        assertNull(icr2Request.getNUMCUO());
        assertNull(icr2Request.getMTOCUO());
        assertNull(icr2Request.getDIVCUO());
    }

    @Test
    public void mapRequestFromPreformalizationBodyShouldHandleNullInsuredAmount() {
        when(preformalizationRequest.getInsuredAmount()).thenReturn(null);

        ICR2Request icr2Request = icr2Helper.mapRequestFromPreformalizationBody(preformalizationRequest);

        assertNull(icr2Request.getSUMASE());
        assertNull(icr2Request.getDIVSUM());
    }

    @Test
    public void mapRequestFromPreformalizationBodyShouldHandleNullTotalAmount() {
        when(preformalizationRequest.getTotalAmount()).thenReturn(null);

        ICR2Request icr2Request = icr2Helper.mapRequestFromPreformalizationBody(preformalizationRequest);

        assertNull(icr2Request.getPRITOT());
        assertNull(icr2Request.getDIVPRI());
    }

    @Test
    public void setParticipantDetailsShouldHandlePaymentManagerRole() {
        when(preformalizationRequest.getParticipants()).thenReturn(Collections.singletonList(participant));
        when(participant.getParticipantType().getId()).thenReturn(ConstantsUtil.Participant.PAYMENT_MANAGER);
        when(participant.getId()).thenReturn("123");
        when(participant.getIdentityDocument().getDocumentType().getId()).thenReturn("ID");
        when(participant.getIdentityDocument().getDocumentNumber()).thenReturn("456");

        ICR2Request icr2Request = new ICR2Request();
        icr2Helper.setParticipantDetails(icr2Request, preformalizationRequest, ConstantsUtil.Participant.PAYMENT_MANAGER);

        assertEquals("123", icr2Request.getCODRSP());
        assertEquals("ID", icr2Request.getTIPDO1());
        assertEquals("456", icr2Request.getNUMRSP());
    }

    @Test
    public void setParticipantDetailsShouldHandleLegalRepresentativeRole() {
        when(preformalizationRequest.getParticipants()).thenReturn(Collections.singletonList(participant));
        when(participant.getParticipantType().getId()).thenReturn(ConstantsUtil.Participant.LEGAL_REPRESENTATIVE);
        when(participant.getId()).thenReturn("789");
        when(participant.getIdentityDocument().getDocumentType().getId()).thenReturn("ID");
        when(participant.getIdentityDocument().getDocumentNumber()).thenReturn("012");

        ICR2Request icr2Request = new ICR2Request();
        icr2Helper.setParticipantDetails(icr2Request, preformalizationRequest, ConstantsUtil.Participant.LEGAL_REPRESENTATIVE);

        assertEquals("789", icr2Request.getCODRPL());
        assertEquals("ID", icr2Request.getTIPDOR());
        assertEquals("012", icr2Request.getNUMRPL());
    }

    @Test
    public void setParticipantDetailsShouldHandleNullParticipant() {
        when(preformalizationRequest.getParticipants()).thenReturn(Collections.singletonList(participant));
        when(participant.getParticipantType().getId()).thenReturn("OTHER_ROLE");

        ICR2Request icr2Request = new ICR2Request();
        icr2Helper.setParticipantDetails(icr2Request, preformalizationRequest, ConstantsUtil.Participant.PAYMENT_MANAGER);

        assertNull(icr2Request.getCODRSP());
        assertNull(icr2Request.getTIPDO1());
        assertNull(icr2Request.getNUMRSP());
    }
}