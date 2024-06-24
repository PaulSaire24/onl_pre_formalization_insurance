package com.bbva.rbvd.lib.r415.impl.business;

import com.bbva.elara.configuration.manager.application.ApplicationConfigurationService;
import com.bbva.rbvd.dto.cicsconnection.icr2.enums.YesNoIndicator;
import com.bbva.rbvd.dto.cicsconnection.icr3.ICR3Request;
import com.bbva.rbvd.dto.insrncsale.commons.HolderDTO;
import com.bbva.rbvd.dto.insrncsale.commons.ValidityPeriodDTO;
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

import java.util.Date;

import static java.util.Objects.nonNull;

public class ICR3Business {

    private final ApplicationConfigurationService applicationConfigurationService;

    public ICR3Business(ApplicationConfigurationService applicationConfigurationService) {
        this.applicationConfigurationService = applicationConfigurationService;
    }

    public ICR3Request mapRequestFromPreformalizationBody(PolicyDTO requestBody) {
        ICR3Request icr3Request = new ICR3Request();

        setBasicDetails(icr3Request, requestBody);
        setPaymentMethodDetails(icr3Request, requestBody.getPaymentMethod());
        setRelatedContractDetails(icr3Request, requestBody);
        setHolderDetails(icr3Request, requestBody.getHolder());
        setInstallmentPlanDetails(icr3Request, requestBody.getInstallmentPlan());
        setInsuredAmountDetails(icr3Request, requestBody.getInsuredAmount());
        setParticipantDetails(icr3Request, requestBody, ConstantsUtil.Participant.PAYMENT_MANAGER);
        setTotalAmountDetails(icr3Request, requestBody.getTotalAmount());

        return icr3Request;
    }

    private void setBasicDetails(ICR3Request icr3Request, PolicyDTO requestBody) {
        icr3Request.setNUMPOL(requestBody.getPolicyNumber());
        icr3Request.setCODPRO(requestBody.getProduct().getId());
        icr3Request.setFECINI(getStartDate(requestBody.getValidityPeriod()));
        icr3Request.setCODMOD(requestBody.getProduct().getPlan().getId());
        icr3Request.setCOBRO(YesNoIndicator.NO);
        icr3Request.setGESTOR(requestBody.getBusinessAgent().getId());
        icr3Request.setPRESEN(requestBody.getPromoter().getId());
        icr3Request.setCODBAN(requestBody.getBank().getId());
        icr3Request.setOFICON(requestBody.getBank().getBranch().getId());
        icr3Request.setCODCIA(requestBody.getInsuranceCompany().getId());
        icr3Request.setINDPREF("S");
    }

    private String getStartDate(ValidityPeriodDTO validityPeriod){
        if(validityPeriod != null){
            return ConvertUtil.convertDateToLocalDate(validityPeriod.getStartDate()).toString();
        }
        return null;
    }

    private void setPaymentMethodDetails(ICR3Request icr3Request, PolicyPaymentMethodDTO paymentMethod) {
        if (nonNull(paymentMethod)) {
            icr3Request.setMTDPGO(paymentMethod.getPaymentType());
            icr3Request.setTFOPAG(paymentMethod.getInstallmentFrequency());
        }
    }

    private void setRelatedContractDetails(ICR3Request icr3Request, PolicyDTO requestBody) {
        if (!CollectionUtils.isEmpty(requestBody.getRelatedContracts())) {
            RelatedContractDTO relatedContract = ConvertUtil.getRelatedContractByTye(requestBody.getRelatedContracts(), ConstantsUtil.RelatedContractType.INTERNAL_CONTRACT);

            if(relatedContract != null){
                icr3Request.setNROCTA(relatedContract.getContractDetails().getContractId());
                icr3Request.setTCONVIN(relatedContract.getContractDetails().getContractType());
            }

            String listTypeCards = this.applicationConfigurationService.getProperty(ConstantsUtil.ApxConsole.LIST_PAYMENT_TYPE_CARD);
            String listTypeAccounts = this.applicationConfigurationService.getProperty(ConstantsUtil.ApxConsole.LIST_PAYMENT_TYPE_ACCOUNT);
            icr3Request.setMEDPAG(getPaymentMethod(listTypeCards,listTypeAccounts,requestBody.getPaymentMethod().getPaymentType()));
        }
    }

    private String getPaymentMethod(String listTypeCards,String listTypeAccounts,String paymentType){
        if(ValidationUtil.isListContainsValue(listTypeAccounts,paymentType)) {
            return ConstantsUtil.RelatedContractType.PRODUCT_TYPE_ID_ACCOUNT;
        }else if(ValidationUtil.isListContainsValue(listTypeCards,paymentType)){
            return ConstantsUtil.RelatedContractType.PRODUCT_TYPE_ID_CARD;
        }else{
            return null;
        }
    }

    private void setHolderDetails(ICR3Request icr3Request, HolderDTO holder) {
        if (nonNull(holder)) {
            icr3Request.setCODASE(holder.getId());
            icr3Request.setTIPDOC(holder.getIdentityDocument().getDocumentType().getId());
            icr3Request.setNUMASE(holder.getIdentityDocument().getNumber());
        }
    }

    private void setInstallmentPlanDetails(ICR3Request icr3Request, PolicyInstallmentPlanDTO installmentPlan) {
        if (nonNull(installmentPlan)) {
            icr3Request.setFECPAG(getPaymentDate(installmentPlan.getStartDate()));
            icr3Request.setNUMCUO(installmentPlan.getTotalNumberInstallments());
            icr3Request.setMTOCUO(ConvertUtil.getBigDecimalValue(installmentPlan.getPaymentAmount().getAmount()));
            icr3Request.setDIVCUO(installmentPlan.getPaymentAmount().getCurrency());
        }
    }

    private String getPaymentDate(Date startDate){
        if(startDate != null){
            return ConvertUtil.convertDateToLocalDate(startDate).toString();
        }
        return null;
    }

    private void setInsuredAmountDetails(ICR3Request icr3Request, InsuredAmountDTO insuredAmount) {
        if (nonNull(insuredAmount)) {
            icr3Request.setSUMASE(ConvertUtil.getBigDecimalValue(insuredAmount.getAmount()));
            icr3Request.setDIVSUM(insuredAmount.getCurrency());
        }
    }

    private void setParticipantDetails(ICR3Request icr3Request, PolicyDTO requestBody, String role) {
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

    private void setTotalAmountDetails(ICR3Request icr3Request, TotalAmountDTO totalAmount) {
        if (nonNull(totalAmount)) {
            icr3Request.setPRITOT(ConvertUtil.getBigDecimalValue(totalAmount.getAmount()));
            icr3Request.setDIVPRI(totalAmount.getCurrency());
        }
    }
}