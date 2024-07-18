package com.bbva.rbvd.lib.r415.impl.business;

import com.bbva.rbvd.dto.insrncsale.policy.PolicyDTO;
import com.bbva.rbvd.dto.preformalization.event.RelatedContractDTO;
import com.bbva.rbvd.dto.preformalization.event.CreatedInsrcEventDTO;
import com.bbva.rbvd.dto.preformalization.event.InsurancePreFormalizedDTO;
import com.bbva.rbvd.dto.preformalization.event.ProductCreatedInsrcEventDTO;
import com.bbva.rbvd.dto.preformalization.event.PlanCreatedInsrcEventDTO;
import com.bbva.rbvd.dto.preformalization.event.TotalInstallmentDTO;
import com.bbva.rbvd.dto.preformalization.event.InstallmentPlansCreatedInsrcEvent;
import com.bbva.rbvd.dto.preformalization.event.PaymentAmountDTO;
import com.bbva.rbvd.dto.preformalization.event.PaymentPeriodDTO;
import com.bbva.rbvd.dto.preformalization.event.HolderDTO;
import com.bbva.rbvd.dto.preformalization.event.IdentityDocumentDTO;
import com.bbva.rbvd.dto.preformalization.event.DocumentTypeDTO;
import com.bbva.rbvd.dto.preformalization.event.ContactDetailDTO;
import com.bbva.rbvd.dto.preformalization.event.ContactDTO;
import com.bbva.rbvd.dto.preformalization.header.EventDTO;
import com.bbva.rbvd.dto.preformalization.header.OriginDTO;
import com.bbva.rbvd.dto.preformalization.header.BranchEventDTO;
import com.bbva.rbvd.dto.preformalization.header.BankEventDTO;
import com.bbva.rbvd.dto.preformalization.header.ResultDTO;
import com.bbva.rbvd.dto.preformalization.header.TraceDTO;
import com.bbva.rbvd.dto.preformalization.header.FlagDTO;
import com.bbva.rbvd.dto.preformalization.header.HeaderDTO;
import com.bbva.rbvd.dto.preformalization.event.StatusDTO;
import com.bbva.rbvd.dto.preformalization.event.PolicyPaymentMethodDTO;
import com.bbva.rbvd.dto.preformalization.event.ValidityPeriodDTO;
import com.bbva.rbvd.dto.preformalization.util.ConstantsUtil;

import java.util.Calendar;
import java.util.Collections;
import java.util.Objects;
import java.util.stream.Collectors;

public class CreatedInsuranceEventBusiness {

    private CreatedInsuranceEventBusiness(){}

    public static CreatedInsrcEventDTO createRequestCreatedInsuranceEvent(PolicyDTO requestBody){
        CreatedInsrcEventDTO createdInsrcEventDTO = new CreatedInsrcEventDTO();

        InsurancePreFormalizedDTO body = new InsurancePreFormalizedDTO();
        body.setQuotationId(requestBody.getQuotationNumber());
        body.setContractId(requestBody.getId());
        Calendar operationDate = Calendar.getInstance();
        operationDate.setTime(requestBody.getOperationDate());
        body.setOperationDate(operationDate);
        ValidityPeriodDTO validityPeriod = new ValidityPeriodDTO();
        validityPeriod.setStartDate(requestBody.getValidityPeriod().getStartDate());
        validityPeriod.setEndDate(requestBody.getValidityPeriod().getEndDate());
        body.setValidityPeriod(validityPeriod);
        body.setHolder(getHolderFromResponse(requestBody));
        body.setProduct(getProductFromResponse(requestBody));
        body.setPaymentMethod(getPaymentMethodFromResponse(requestBody));
        body.setStatus(getStatus());

        createdInsrcEventDTO.setInsurancePreFormalized(body);

        EventDTO event = new EventDTO("InsurancePreFormalized", "pe.rbvd.app-id-105529.prod");
        OriginDTO origin = new OriginDTO();
        origin.setAap(requestBody.getAap());

        BranchEventDTO branch = new BranchEventDTO();
        branch.setBranchId(requestBody.getBank().getBranch().getId());
        BankEventDTO bank = new BankEventDTO(requestBody.getBank().getId(), branch);
        origin.setBank(bank);

        origin.setChannelCode(requestBody.getSaleChannelId());
        origin.setEnvironCode(requestBody.getEnvironmentCode());
        origin.setIpv4(requestBody.getIpv4());
        origin.setOperation("APX_RBVDT211_InsurancePreFormalized");
        origin.setProductCode(requestBody.getProductCode());

        String timestamp = requestBody.getHeaderOperationDate().concat(" ").concat(requestBody.getHeaderOperationTime());
        origin.setTimestamp(timestamp);

        origin.setUser(requestBody.getCreationUser());

        ResultDTO result = new ResultDTO("201", "Evento registrado de manera satisfactoria");
        TraceDTO traces = new TraceDTO("fspan", requestBody.getTraceId(), requestBody.getTraceId());

        FlagDTO flag = new FlagDTO();
        flag.setDebug("debug");
        flag.setTest("test");

        HeaderDTO header = new HeaderDTO(event, flag, origin, result, traces, "1.1.0");

        createdInsrcEventDTO.setHeader(header);

        return createdInsrcEventDTO;
    }

    private static StatusDTO getStatus(){
        StatusDTO status = new StatusDTO();

        status.setId(ConstantsUtil.StatusContract.PREFORMALIZADA.getValue());
        status.setName(ConstantsUtil.StatusContract.PREFORMALIZADA.getValue());

        return status;
    }

    private static PolicyPaymentMethodDTO getPaymentMethodFromResponse(PolicyDTO requestBody) {
        PolicyPaymentMethodDTO paymentMethod = new PolicyPaymentMethodDTO();

        paymentMethod.setPaymentType(requestBody.getPaymentMethod().getPaymentType());

        RelatedContractDTO relatedContracts = requestBody.getRelatedContracts().stream()
                .filter(relatedContractDTO -> ConstantsUtil.RelatedContractType.INTERNAL_CONTRACT.equals(relatedContractDTO.getContractDetails().getContractType()))
                .map(relatedContract ->{
                RelatedContractDTO relatedContractDTO = new RelatedContractDTO();
                relatedContractDTO.setContractId(relatedContract.getContractDetails().getContractId());
                relatedContractDTO.setNumber(relatedContract.getContractDetails().getContractId());
                return relatedContractDTO;
                }).findFirst().orElse(null);

        if(Objects.nonNull(relatedContracts)){
            paymentMethod.setRelatedContracts(Collections.singletonList(relatedContracts));
        }
        return paymentMethod;
    }

    private static ProductCreatedInsrcEventDTO getProductFromResponse(PolicyDTO requestBody){
        ProductCreatedInsrcEventDTO product = new ProductCreatedInsrcEventDTO();

        product.setId(requestBody.getProduct().getId());
        product.setPlan(getPlanFromResponse(requestBody));

        return product;
    }

    private static PlanCreatedInsrcEventDTO getPlanFromResponse(PolicyDTO requestBody){
        PlanCreatedInsrcEventDTO plan = new PlanCreatedInsrcEventDTO();

        plan.setId(requestBody.getProduct().getPlan().getId());

        TotalInstallmentDTO totalInstallment = new TotalInstallmentDTO();
        totalInstallment.setAmount(requestBody.getTotalAmount().getAmount());
        totalInstallment.setCurrency(requestBody.getTotalAmount().getCurrency());
        totalInstallment.setPeriod(new PaymentPeriodDTO());
        totalInstallment.getPeriod().setId(requestBody.getInstallmentPlan().getPeriod().getId());
        plan.setTotalInstallment(totalInstallment);

        InstallmentPlansCreatedInsrcEvent installmentPlans = new InstallmentPlansCreatedInsrcEvent();
        installmentPlans.setPaymentsTotalNumber(getPaymentsTotalNumber(requestBody.getInstallmentPlan().getTotalNumberInstallments()));
        installmentPlans.setPaymentAmount(new PaymentAmountDTO());
        installmentPlans.getPaymentAmount().setAmount(requestBody.getInstallmentPlan().getPaymentAmount().getAmount());
        installmentPlans.getPaymentAmount().setCurrency(requestBody.getInstallmentPlan().getPaymentAmount().getCurrency());
        installmentPlans.setPeriod(new PaymentPeriodDTO());
        installmentPlans.getPeriod().setId(requestBody.getInstallmentPlan().getPeriod().getId());
        plan.setInstallmentPlans(Collections.singletonList(installmentPlans));

        return plan;
    }

    private static int getPaymentsTotalNumber(Long number){
        if(number != null){
            return number.intValue();
        }
        return 1;
    }

    private static HolderDTO getHolderFromResponse(PolicyDTO requestBody){
        HolderDTO holderDTO = new HolderDTO();

        holderDTO.setId(requestBody.getHolder().getId());

        IdentityDocumentDTO identityDocument = new IdentityDocumentDTO();
        identityDocument.setDocumentNumber(requestBody.getHolder().getIdentityDocument().getNumber());
        DocumentTypeDTO documentType = new DocumentTypeDTO();
        documentType.setId(requestBody.getHolder().getIdentityDocument().getDocumentType().getId());
        identityDocument.setDocumentType(documentType);
        holderDTO.setIdentityDocument(identityDocument);

        holderDTO.setContactDetails(requestBody.getHolder().getContactDetails().stream().map(CreatedInsuranceEventBusiness::getContactDetailToHolder).collect(Collectors.toList()));

        return holderDTO;
    }


    private static com.bbva.rbvd.dto.preformalization.event.ContactDetailDTO getContactDetailToHolder(com.bbva.rbvd.dto.insrncsale.commons.ContactDetailDTO contactDetailDTO) {
        ContactDetailDTO contactDetailForHolderEvent = new ContactDetailDTO();
        ContactDTO contact = new ContactDTO();
        if(ConstantsUtil.ContactDetailsType.EMAIL.equals(contactDetailDTO.getContact().getContactDetailType())) {
            contact.setContactType(ConstantsUtil.ContactDetailsType.EMAIL);
            contact.setValue(contactDetailDTO.getContact().getAddress());
        } else {
            contact.setContactType(ConstantsUtil.ContactDetailsType.MOBILE);
            contact.setValue(contactDetailDTO.getContact().getPhoneNumber());
        }
        contactDetailForHolderEvent.setContact(contact);
        return contactDetailForHolderEvent;
    }

}
