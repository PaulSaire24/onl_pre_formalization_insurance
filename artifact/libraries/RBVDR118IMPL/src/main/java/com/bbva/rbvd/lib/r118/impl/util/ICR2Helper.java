package com.bbva.rbvd.lib.r118.impl.util;

import com.bbva.rbvd.dto.cicsconnection.icr2.ICR2Request;
import com.bbva.rbvd.dto.insrncsale.commons.HolderDTO;
import com.bbva.rbvd.dto.insrncsale.policy.*;

import static java.util.Objects.nonNull;

public class ICR2Helper {

    public ICR2Request mapRequestFromPolicy(PolicyDTO policyDTO) {
        ICR2Request icr2Request = new ICR2Request();

        setBasicDetails(icr2Request, policyDTO);
        setPaymentMethodDetails(icr2Request, policyDTO.getPaymentMethod());
        setRelatedContractDetails(icr2Request, policyDTO.getRelatedContracts().get(ConstantsUtil.Number.CERO));
        setHolderDetails(icr2Request, policyDTO.getHolder());
        setInstallmentPlanDetails(icr2Request, policyDTO.getInstallmentPlan());
        setInsuredAmountDetails(icr2Request, policyDTO.getInsuredAmount());
        setParticipantDetails(icr2Request, policyDTO, ConstantsUtil.Participant.PAYMENT_MANAGER);
        setParticipantDetails(icr2Request, policyDTO, ConstantsUtil.Participant.LEGAL_REPRESENTATIVE);
        setTotalAmountDetails(icr2Request, policyDTO.getTotalAmount());

        // icr2Request.setINDPREF();

        return icr2Request;
    }

    private void setBasicDetails(ICR2Request icr2Request, PolicyDTO policyDTO) {
        icr2Request.setNUMPOL(policyDTO.getPolicyNumber());
        icr2Request.setCODPRO(policyDTO.getProductId());
        icr2Request.setFECINI(policyDTO.getValidityPeriod().getStartDate().toString());
        icr2Request.setCODMOD(policyDTO.getProductPlan().getId());
        icr2Request.setCOBRO(policyDTO.getFirstInstallment().getIsPaymentRequired() ? "S" : "N");
        icr2Request.setGESTOR(policyDTO.getBusinessAgent().getId());
        icr2Request.setPRESEN(policyDTO.getPromoter().getId());
        icr2Request.setCODBAN(policyDTO.getBank().getId());
        icr2Request.setOFICON(policyDTO.getBank().getBranch().getId());
        icr2Request.setCODCIA(policyDTO.getInsuranceCompany().getId());
        icr2Request.setSUBCANA(policyDTO.getSaleSupplier().getId());
        icr2Request.setNUMCOTIZ(policyDTO.getQuotationId());
    }

    private void setPaymentMethodDetails(ICR2Request icr2Request, PolicyPaymentMethodDTO paymentMethod) {
        if (nonNull(paymentMethod)) {
            icr2Request.setMTDPGO(paymentMethod.getPaymentType());
            icr2Request.setTFOPAG(paymentMethod.getInstallmentFrequency());
        }
    }

    private void setRelatedContractDetails(ICR2Request icr2Request, RelatedContractDTO relatedContract) {
        if (nonNull(relatedContract)) {
            String contractType = relatedContract.getContractDetails().getContractType();
            icr2Request.setNROCTA(relatedContract.getNumber());
            icr2Request.setMEDPAG(relatedContract.getProduct().getId());
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

    private void setParticipantDetails(ICR2Request icr2Request, PolicyDTO policyDTO, String role) {
        ParticipantDTO participant = getParticipantByRole(policyDTO, role);
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

    private ParticipantDTO getParticipantByRole(PolicyDTO policyDTO, String role) {
        return policyDTO.getParticipants().stream()
                .filter(participant -> participant.getParticipantType().getId().equals(role))
                .findFirst()
                .orElse(null);
    }
}