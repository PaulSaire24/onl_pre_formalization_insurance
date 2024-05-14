package com.bbva.rbvd.lib.r415.impl.business;

import com.bbva.rbvd.dto.cicsconnection.icr2.ICR2Request;
import com.bbva.rbvd.dto.cicsconnection.icr2.enums.YesNoIndicator;
import com.bbva.rbvd.dto.insrncsale.commons.HolderDTO;
import com.bbva.rbvd.dto.insrncsale.policy.PolicyDTO;
import com.bbva.rbvd.dto.insrncsale.policy.PolicyPaymentMethodDTO;
import com.bbva.rbvd.dto.insrncsale.policy.RelatedContractDTO;
import com.bbva.rbvd.dto.insrncsale.policy.InsuredAmountDTO;
import com.bbva.rbvd.dto.insrncsale.policy.ParticipantDTO;
import com.bbva.rbvd.dto.insrncsale.policy.PolicyInstallmentPlanDTO;
import com.bbva.rbvd.dto.insrncsale.policy.TotalAmountDTO;
import com.bbva.rbvd.dto.preformalization.util.ConstantsUtil;
import com.bbva.rbvd.lib.r415.impl.util.ConvertUtil;
import com.bbva.rbvd.lib.r415.impl.util.ValidationUtil;
import org.springframework.util.CollectionUtils;

import java.util.List;

import static java.util.Objects.nonNull;

public class ICR2Business {
    private ICR2Business(){}

    public static ICR2Request mapRequestFromPreformalizationBody(PolicyDTO requestBody) {
        ICR2Request icr2Request = new ICR2Request();

        setBasicDetails(icr2Request, requestBody);
        setPaymentMethodDetails(icr2Request, requestBody.getPaymentMethod());
        setRelatedContractDetails(icr2Request, requestBody.getRelatedContracts());
        setHolderDetails(icr2Request, requestBody.getHolder());
        setInstallmentPlanDetails(icr2Request, requestBody.getInstallmentPlan());
        setInsuredAmountDetails(icr2Request, requestBody.getInsuredAmount());
        setParticipantDetails(icr2Request, requestBody, ConstantsUtil.Participant.PAYMENT_MANAGER);
        setParticipantDetails(icr2Request, requestBody, ConstantsUtil.Participant.LEGAL_REPRESENTATIVE);
        setTotalAmountDetails(icr2Request, requestBody.getTotalAmount());

        return icr2Request;
    }

    public static void setBasicDetails(ICR2Request icr2Request, PolicyDTO requestBody) {
        icr2Request.setNUMPOL(requestBody.getPolicyNumber());
        icr2Request.setCODPRO(requestBody.getProduct().getId());
        icr2Request.setFECINI(ConvertUtil.convertDateToLocalDate(requestBody.getValidityPeriod().getStartDate()).toString());
        icr2Request.setCODMOD(requestBody.getProduct().getPlan().getId());
        icr2Request.setCOBRO(requestBody.getFirstInstallment().getIsPaymentRequired() ? YesNoIndicator.YES : YesNoIndicator.NO);
        icr2Request.setGESTOR(requestBody.getBusinessAgent().getId());
        icr2Request.setPRESEN(requestBody.getPromoter().getId());
        icr2Request.setCODBAN(requestBody.getBank().getId());
        icr2Request.setOFICON(requestBody.getBank().getBranch().getId());
        icr2Request.setCODCIA(requestBody.getInsuranceCompany().getId());
        icr2Request.setINDPREF("S");
    }

    public static void setPaymentMethodDetails(ICR2Request icr2Request, PolicyPaymentMethodDTO paymentMethod) {
        if (nonNull(paymentMethod)) {
            icr2Request.setMTDPGO(paymentMethod.getPaymentType());
            icr2Request.setTFOPAG(paymentMethod.getInstallmentFrequency());
        }
    }

    public static void setRelatedContractDetails(ICR2Request icr2Request, List<RelatedContractDTO> listRelatedContract) {
        if (!CollectionUtils.isEmpty(listRelatedContract)) {
            RelatedContractDTO relatedContract = listRelatedContract.get(0);
            String contractType = relatedContract.getContractDetails().getContractType();
            icr2Request.setNROCTA(relatedContract.getContractDetails().getNumber());
            icr2Request.setMEDPAG(relatedContract.getContractDetails().getProductType().getId());
            icr2Request.setTCONVIN(contractType);
            icr2Request.setCONVIN(contractType.equals(ConstantsUtil.RelatedContractType.INTERNAL_CONTRACT)
                    ? relatedContract.getContractDetails().getContractId() : relatedContract.getContractDetails().getNumber());
        }
    }

    public static void setHolderDetails(ICR2Request icr2Request, HolderDTO holder) {
        if (nonNull(holder)) {
            icr2Request.setCODASE(holder.getId());
            icr2Request.setTIPDOC(holder.getIdentityDocument().getDocumentType().getId());
            icr2Request.setNUMASE(holder.getIdentityDocument().getNumber());
        }
    }

    public static void setInstallmentPlanDetails(ICR2Request icr2Request, PolicyInstallmentPlanDTO installmentPlan) {
        if (nonNull(installmentPlan)) {
            icr2Request.setFECPAG(ConvertUtil.convertDateToLocalDate(installmentPlan.getStartDate()).toString());
            icr2Request.setNUMCUO(installmentPlan.getTotalNumberInstallments().toString());
            icr2Request.setMTOCUO(installmentPlan.getPaymentAmount().getAmount().toString());
            icr2Request.setDIVCUO(installmentPlan.getPaymentAmount().getCurrency());
        }
    }

    public static void setInsuredAmountDetails(ICR2Request icr2Request, InsuredAmountDTO insuredAmount) {
        if (nonNull(insuredAmount)) {
            icr2Request.setSUMASE(insuredAmount.getAmount().toString());
            icr2Request.setDIVSUM(insuredAmount.getCurrency());
        }
    }

    public static void setParticipantDetails(ICR2Request icr2Request, PolicyDTO requestBody, String role) {
        ParticipantDTO participant = ValidationUtil.filterOneParticipantByType(requestBody.getParticipants(), role);
        if (nonNull(participant)) {
            icr2Request.setPARTIC(role);
            if (role.equals(ConstantsUtil.Participant.PAYMENT_MANAGER)) {
                icr2Request.setCODRSP(participant.getCustomerId());
                icr2Request.setTIPDO1(participant.getIdentityDocument().getDocumentType().getId());
                icr2Request.setNUMRSP(participant.getIdentityDocument().getNumber());
            } else if (role.equals(ConstantsUtil.Participant.LEGAL_REPRESENTATIVE)) {
                icr2Request.setCODRPL(participant.getCustomerId());
                icr2Request.setTIPDOR(participant.getIdentityDocument().getDocumentType().getId());
                icr2Request.setNUMRPL(participant.getIdentityDocument().getNumber());
            }
        }
    }

    public static void setTotalAmountDetails(ICR2Request icr2Request, TotalAmountDTO totalAmount) {
        if (nonNull(totalAmount)) {
            icr2Request.setPRITOT(totalAmount.getAmount().toString());
            icr2Request.setDIVPRI(totalAmount.getCurrency());
        }
    }
}