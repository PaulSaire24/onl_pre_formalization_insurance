package com.bbva.rbvd.lib.r415.impl.business;

import com.bbva.rbvd.dto.cicsconnection.icr2.enums.YesNoIndicator;
import com.bbva.rbvd.dto.cicsconnection.icr3.ICR3Request;
import com.bbva.rbvd.dto.insrncsale.commons.HolderDTO;
import com.bbva.rbvd.dto.insrncsale.policy.PolicyDTO;
import com.bbva.rbvd.dto.insrncsale.policy.InsuredAmountDTO;
import com.bbva.rbvd.dto.insrncsale.policy.PolicyInstallmentPlanDTO;
import com.bbva.rbvd.dto.insrncsale.policy.PolicyPaymentMethodDTO;
import com.bbva.rbvd.dto.insrncsale.policy.RelatedContractDTO;
import com.bbva.rbvd.dto.insrncsale.policy.TotalAmountDTO;
import com.bbva.rbvd.dto.insrncsale.policy.ParticipantDTO;
import com.bbva.rbvd.dto.preformalization.util.ConstantsUtil;
import com.bbva.rbvd.lib.r415.impl.util.ConvertUtil;
import com.bbva.rbvd.lib.r415.impl.util.ValidationUtil;
import org.springframework.util.CollectionUtils;

import java.util.List;

import static java.util.Objects.nonNull;

public class ICR3Business {
    private ICR3Business(){}

    public static ICR3Request mapRequestFromPreformalizationBody(PolicyDTO requestBody) {
        ICR3Request icr3Request = new ICR3Request();

        setBasicDetails(icr3Request, requestBody);
        setPaymentMethodDetails(icr3Request, requestBody.getPaymentMethod());
        setRelatedContractDetails(icr3Request, requestBody.getRelatedContracts());
        setHolderDetails(icr3Request, requestBody.getHolder());
        setInstallmentPlanDetails(icr3Request, requestBody.getInstallmentPlan());
        setInsuredAmountDetails(icr3Request, requestBody.getInsuredAmount());
        setParticipantDetails(icr3Request, requestBody, ConstantsUtil.Participant.PAYMENT_MANAGER);
        setTotalAmountDetails(icr3Request, requestBody.getTotalAmount());

        return icr3Request;
    }

    public static void setBasicDetails(ICR3Request icr3Request, PolicyDTO requestBody) {
        icr3Request.setNUMPOL(requestBody.getPolicyNumber());
        icr3Request.setCODPRO(requestBody.getProduct().getId());
        icr3Request.setFECINI(ConvertUtil.convertDateToLocalDate(requestBody.getValidityPeriod().getStartDate()).toString());
        icr3Request.setCODMOD(requestBody.getProduct().getPlan().getId());
        icr3Request.setCOBRO(YesNoIndicator.NO);
        icr3Request.setGESTOR(requestBody.getBusinessAgent().getId());
        icr3Request.setPRESEN(requestBody.getPromoter().getId());
        icr3Request.setCODBAN(requestBody.getBank().getId());
        icr3Request.setOFICON(requestBody.getBank().getBranch().getId());
        icr3Request.setCODCIA(requestBody.getInsuranceCompany().getId());
        icr3Request.setINDPREF("S");
    }

    public static void setPaymentMethodDetails(ICR3Request icr3Request, PolicyPaymentMethodDTO paymentMethod) {
        if (nonNull(paymentMethod)) {
            icr3Request.setMTDPGO(paymentMethod.getPaymentType());
            icr3Request.setTFOPAG(paymentMethod.getInstallmentFrequency());
        }
    }

    public static void setRelatedContractDetails(ICR3Request icr3Request, List<RelatedContractDTO> listRelatedContract) {
        if (!CollectionUtils.isEmpty(listRelatedContract)) {
            RelatedContractDTO relatedContract = listRelatedContract.get(0);
            String contractType = relatedContract.getContractDetails().getContractType();
            icr3Request.setNROCTA(relatedContract.getContractDetails().getNumber());
            icr3Request.setMEDPAG(relatedContract.getContractDetails().getProductType().getId());
            icr3Request.setTCONVIN(contractType);
            icr3Request.setCONVIN(contractType.equals(ConstantsUtil.RelatedContractType.INTERNAL_CONTRACT)
                    ? relatedContract.getContractDetails().getContractId() : relatedContract.getContractDetails().getNumber());
        }
    }

    public static void setHolderDetails(ICR3Request icr3Request, HolderDTO holder) {
        if (nonNull(holder)) {
            icr3Request.setCODASE(holder.getId());
            icr3Request.setTIPDOC(holder.getIdentityDocument().getDocumentType().getId());
            icr3Request.setNUMASE(holder.getIdentityDocument().getNumber());
        }
    }

    public static void setInstallmentPlanDetails(ICR3Request icr3Request, PolicyInstallmentPlanDTO installmentPlan) {
        if (nonNull(installmentPlan)) {
            icr3Request.setFECPAG(ConvertUtil.convertDateToLocalDate(installmentPlan.getStartDate()).toString());
            icr3Request.setNUMCUO(installmentPlan.getTotalNumberInstallments());
            icr3Request.setMTOCUO(ConvertUtil.getBigDecimalValue(installmentPlan.getPaymentAmount().getAmount()));
            icr3Request.setDIVCUO(installmentPlan.getPaymentAmount().getCurrency());
        }
    }

    public static void setInsuredAmountDetails(ICR3Request icr3Request, InsuredAmountDTO insuredAmount) {
        if (nonNull(insuredAmount)) {
            icr3Request.setSUMASE(ConvertUtil.getBigDecimalValue(insuredAmount.getAmount()));
            icr3Request.setDIVSUM(insuredAmount.getCurrency());
        }
    }

    public static void setParticipantDetails(ICR3Request icr3Request, PolicyDTO requestBody, String role) {
        ParticipantDTO participant = ValidationUtil.filterOneParticipantByType(requestBody.getParticipants(), role);
        if (nonNull(participant)) {
            icr3Request.setPARTIC(role);
            if (role.equals(ConstantsUtil.Participant.PAYMENT_MANAGER)) {
                icr3Request.setCODRSP(participant.getCustomerId());
                icr3Request.setTIPDO1(participant.getIdentityDocument().getDocumentType().getId());
                icr3Request.setNUMRSP(participant.getIdentityDocument().getNumber());
            }
        }
    }

    public static void setTotalAmountDetails(ICR3Request icr3Request, TotalAmountDTO totalAmount) {
        if (nonNull(totalAmount)) {
            icr3Request.setPRITOT(ConvertUtil.getBigDecimalValue(totalAmount.getAmount()));
            icr3Request.setDIVPRI(totalAmount.getCurrency());
        }
    }
}