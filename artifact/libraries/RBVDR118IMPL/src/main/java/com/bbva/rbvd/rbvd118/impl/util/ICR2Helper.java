package com.bbva.rbvd.rbvd118.impl.util;

import com.bbva.rbvd.dto.cicsconnection.icr2.ICR2Request;
import com.bbva.rbvd.dto.insrncsale.commons.HolderDTO;
import com.bbva.rbvd.dto.insrncsale.policy.InsuredAmountDTO;
import com.bbva.rbvd.dto.insrncsale.policy.ParticipantDTO;
import com.bbva.rbvd.dto.insrncsale.policy.PolicyInstallmentPlanDTO;
import com.bbva.rbvd.dto.insrncsale.policy.PolicyPaymentMethodDTO;
import com.bbva.rbvd.dto.insrncsale.policy.TotalAmountDTO;
import com.bbva.rbvd.dto.preformalization.PreformalizationDTO;
import com.bbva.rbvd.dto.preformalization.RelatedContract;

import static java.util.Objects.nonNull;

public class ICR2Helper {

    public ICR2Request mapRequestFromPreformalizationBody(PreformalizationDTO preformalizationRequest) {
        ICR2Request icr2Request = new ICR2Request();

        setBasicDetails(icr2Request, preformalizationRequest);
        setPaymentMethodDetails(icr2Request, preformalizationRequest.getPaymentMethod());
        setRelatedContractDetails(icr2Request, preformalizationRequest.getRelatedContracts().get(ConstantsUtil.Number.CERO));
        setHolderDetails(icr2Request, preformalizationRequest.getHolder());
        setInstallmentPlanDetails(icr2Request, preformalizationRequest.getInstallmentPlan());
        setInsuredAmountDetails(icr2Request, preformalizationRequest.getInsuredAmount());
        setParticipantDetails(icr2Request, preformalizationRequest, ConstantsUtil.Participant.PAYMENT_MANAGER);
        setParticipantDetails(icr2Request, preformalizationRequest, ConstantsUtil.Participant.LEGAL_REPRESENTATIVE);
        setTotalAmountDetails(icr2Request, preformalizationRequest.getTotalAmount());

        return icr2Request;
    }

    public void setBasicDetails(ICR2Request icr2Request, PreformalizationDTO preformalizationRequest) {
        icr2Request.setNUMPOL(preformalizationRequest.getPolicyNumber());
        icr2Request.setCODPRO(preformalizationRequest.getProductId());
        icr2Request.setFECINI(preformalizationRequest.getValidityPeriod().getStartDate().toString());
        icr2Request.setCODMOD(preformalizationRequest.getProduct().getPlan().getId());
        icr2Request.setCOBRO(preformalizationRequest.getFirstInstallment().getIsPaymentRequired() ? "S" : "N");
        icr2Request.setGESTOR(preformalizationRequest.getBusinessAgent().getId());
        icr2Request.setPRESEN(preformalizationRequest.getPromoter().getId());
        icr2Request.setCODBAN(preformalizationRequest.getBank().getId());
        icr2Request.setOFICON(preformalizationRequest.getBank().getBranch().getId());
        icr2Request.setCODCIA(preformalizationRequest.getInsuranceCompany().getId());
        icr2Request.setNUMCOTIZ(preformalizationRequest.getQuotationId());
    }

    public void setPaymentMethodDetails(ICR2Request icr2Request, PolicyPaymentMethodDTO paymentMethod) {
        if (nonNull(paymentMethod)) {
            icr2Request.setMTDPGO(paymentMethod.getPaymentType());
            icr2Request.setTFOPAG(paymentMethod.getInstallmentFrequency());
        }
    }

    private void setRelatedContractDetails(ICR2Request icr2Request, RelatedContract relatedContract) {
        if (nonNull(relatedContract)) {
            String contractType = relatedContract.getContractDetails().getContractType();
            icr2Request.setNROCTA(relatedContract.getNumber());
            icr2Request.setMEDPAG(relatedContract.getContractDetails().getProductType().getId());
            icr2Request.setTCONVIN(contractType);
            icr2Request.setCONVIN(contractType.equals(ConstantsUtil.RelatedContractType.InternalContract) ? relatedContract.getContractDetails().getContractId() : relatedContract.getContractDetails().getNumber());
        }
    }

    private void setHolderDetails(ICR2Request icr2Request, HolderDTO holder) {
        if (nonNull(holder)) {
            icr2Request.setCODASE(holder.getId());
            icr2Request.setTIPDOC(holder.getIdentityDocument().getDocumentType().getId());
            icr2Request.setNUMASE(holder.getIdentityDocument().getDocumentNumber());
        }
    }

    private void setInstallmentPlanDetails(ICR2Request icr2Request, PolicyInstallmentPlanDTO installmentPlan) {
        if (nonNull(installmentPlan)) {
            icr2Request.setFECPAG(installmentPlan.getStartDate().toString());
            icr2Request.setNUMCUO(installmentPlan.getTotalNumberInstallments().toString());
            icr2Request.setMTOCUO(installmentPlan.getPaymentAmount().getAmount().toString());
            icr2Request.setDIVCUO(installmentPlan.getPaymentAmount().getCurrency());
        }
    }

    private void setInsuredAmountDetails(ICR2Request icr2Request, InsuredAmountDTO insuredAmount) {
        if (nonNull(insuredAmount)) {
            icr2Request.setSUMASE(insuredAmount.getAmount().toString());
            icr2Request.setDIVSUM(insuredAmount.getCurrency());
        }
    }

    public void setParticipantDetails(ICR2Request icr2Request, PreformalizationDTO preformalizationRequest, String role) {
        ParticipantDTO participant = getParticipantByRole(preformalizationRequest, role);
        if (nonNull(participant)) {
            icr2Request.setPARTIC(role);
            if (role.equals(ConstantsUtil.Participant.PAYMENT_MANAGER)) {
                icr2Request.setCODRSP(participant.getId());
                icr2Request.setTIPDO1(participant.getIdentityDocument().getDocumentType().getId());
                icr2Request.setNUMRSP(participant.getIdentityDocument().getDocumentNumber());
            } else if (role.equals(ConstantsUtil.Participant.LEGAL_REPRESENTATIVE)) {
                icr2Request.setCODRPL(participant.getId());
                icr2Request.setTIPDOR(participant.getIdentityDocument().getDocumentType().getId());
                icr2Request.setNUMRPL(participant.getIdentityDocument().getDocumentNumber());
            }
        }
    }

    private void setTotalAmountDetails(ICR2Request icr2Request, TotalAmountDTO totalAmount) {
        if (nonNull(totalAmount)) {
            icr2Request.setPRITOT(totalAmount.getAmount().toString());
            icr2Request.setDIVPRI(totalAmount.getCurrency());
        }
    }

    private ParticipantDTO getParticipantByRole(PreformalizationDTO preformalizationRequest, String role) {
        return preformalizationRequest.getParticipants().stream()
                .filter(participant -> participant.getParticipantType().getId().equals(role))
                .findFirst()
                .orElse(null);
    }
}