package com.bbva.rbvd.lib.r415.impl.business;

import com.bbva.rbvd.dto.insrncsale.commons.PolicyInspectionDTO;
import com.bbva.rbvd.dto.insrncsale.commons.ContactDetailDTO;
import com.bbva.rbvd.dto.insrncsale.commons.PaymentAmountDTO;
import com.bbva.rbvd.dto.insrncsale.commons.ValidityPeriodDTO;
import com.bbva.rbvd.dto.insrncsale.commons.HolderDTO;
import com.bbva.rbvd.dto.insrncsale.commons.ContactDTO;
import com.bbva.rbvd.dto.insrncsale.events.CreatedInsrcEventDTO;
import com.bbva.rbvd.dto.insrncsale.events.CreatedInsuranceDTO;
import com.bbva.rbvd.dto.insrncsale.events.StatusDTO;
import com.bbva.rbvd.dto.insrncsale.events.ProductCreatedInsrcEventDTO;
import com.bbva.rbvd.dto.insrncsale.events.PlanCreatedInsrcEventDTO;
import com.bbva.rbvd.dto.insrncsale.events.InstallmentPlansCreatedInsrcEvent;
import com.bbva.rbvd.dto.insrncsale.events.header.EventDTO;
import com.bbva.rbvd.dto.insrncsale.events.header.OriginDTO;
import com.bbva.rbvd.dto.insrncsale.events.header.BranchEventDTO;
import com.bbva.rbvd.dto.insrncsale.events.header.ResultDTO;
import com.bbva.rbvd.dto.insrncsale.events.header.TraceDTO;
import com.bbva.rbvd.dto.insrncsale.events.header.BankEventDTO;
import com.bbva.rbvd.dto.insrncsale.events.header.FlagDTO;
import com.bbva.rbvd.dto.insrncsale.events.header.HeaderDTO;
import com.bbva.rbvd.dto.insrncsale.policy.PolicyDTO;
import com.bbva.rbvd.dto.insrncsale.policy.PolicyPaymentMethodDTO;
import com.bbva.rbvd.dto.insrncsale.policy.RelatedContractDTO;
import com.bbva.rbvd.dto.insrncsale.policy.TotalInstallmentDTO;
import com.bbva.rbvd.dto.insrncsale.policy.PaymentPeriodDTO;
import com.bbva.rbvd.dto.preformalization.util.ConstantsUtil;
import com.bbva.rbvd.lib.r415.impl.util.ConvertUtil;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class CreatedInsuranceEventBusiness {

    private CreatedInsuranceEventBusiness(){}

    public static CreatedInsrcEventDTO createRequestCreatedInsuranceEvent(PolicyDTO requestBody){
        CreatedInsrcEventDTO createdInsrcEventDTO = new CreatedInsrcEventDTO();

        CreatedInsuranceDTO body = new CreatedInsuranceDTO();
        body.setQuotationId(requestBody.getQuotationNumber());
        body.setOperationDate(ConvertUtil.convertDateToCalendar(requestBody.getOperationDate()));
        body.setValidityPeriod(getValidityPeriodFromInput(requestBody));
        body.setHolder(getHolderFronInput(requestBody));
        body.setProduct(getProductFromInput(requestBody));
        body.setPaymentMethod(getPaymentMethodFromInput(requestBody));
        body.setInspection(getInspectionFromInput(requestBody));
        body.setStatus(getStatus());

        createdInsrcEventDTO.setCreatedInsurance(body);

        EventDTO event = new EventDTO("CreatedInsurance", "pe.rbvd.app-id-105529.prod");
        OriginDTO origin = new OriginDTO();
        origin.setAap(requestBody.getAap());

        BranchEventDTO branch = new BranchEventDTO();
        branch.setBranchId(requestBody.getBank().getBranch().getId());
        BankEventDTO bank = new BankEventDTO(requestBody.getBank().getId(), branch);
        origin.setBank(bank);

        origin.setChannelCode(requestBody.getSaleChannelId());
        origin.setEnvironCode(requestBody.getEnvironmentCode());
        origin.setIpv4(requestBody.getIpv4());
        origin.setOperation("APX_RBVDT211_CreatedInsurance");
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

    private static PolicyInspectionDTO getInspectionFromInput(PolicyDTO requestBody){
        if(requestBody.getInspection() != null){
            PolicyInspectionDTO inspectionDTO = new PolicyInspectionDTO();
            inspectionDTO.setIsRequired(requestBody.getInspection().getIsRequired());
            inspectionDTO.setFullName(requestBody.getInspection().getFullName());
            List<ContactDetailDTO> contactDetailsForInspection = requestBody.getInspection().getContactDetails().
                    stream().map(CreatedInsuranceEventBusiness::getContactDetailForInspection).collect(Collectors.toList());
            inspectionDTO.setContactDetails(contactDetailsForInspection);
        }

        return null;
    }

    private static PolicyPaymentMethodDTO getPaymentMethodFromInput(PolicyDTO requestBody) {
        PolicyPaymentMethodDTO paymentMethod = new PolicyPaymentMethodDTO();

        paymentMethod.setPaymentType(requestBody.getPaymentMethod().getPaymentType());

        RelatedContractDTO relatedContract = new RelatedContractDTO();
        relatedContract.setContractId(requestBody.getRelatedContracts().get(0).getContractId());
        relatedContract.setNumber(requestBody.getRelatedContracts().get(0).getNumber());
        paymentMethod.setRelatedContracts(Collections.singletonList(relatedContract));

        return paymentMethod;
    }

    private static ProductCreatedInsrcEventDTO getProductFromInput(PolicyDTO requestBody){
        ProductCreatedInsrcEventDTO product = new ProductCreatedInsrcEventDTO();

        product.setId(requestBody.getProduct().getId());
        product.setPlan(getPlanFromInput(requestBody));

        return product;
    }

    private static PlanCreatedInsrcEventDTO getPlanFromInput(PolicyDTO requestBody){
        PlanCreatedInsrcEventDTO plan = new PlanCreatedInsrcEventDTO();

        plan.setId(requestBody.getProduct().getPlan().getId());

        TotalInstallmentDTO totalInstallment = new TotalInstallmentDTO();
        totalInstallment.setAmount(requestBody.getTotalAmount().getAmount());
        totalInstallment.setCurrency(requestBody.getTotalAmount().getCurrency());
        totalInstallment.setPeriod(new PaymentPeriodDTO());
        totalInstallment.getPeriod().setId(requestBody.getInstallmentPlan().getPeriod().getId());
        plan.setTotalInstallment(totalInstallment);

        InstallmentPlansCreatedInsrcEvent installmentPlans = new InstallmentPlansCreatedInsrcEvent();
        installmentPlans.setPaymentsTotalNumber(requestBody.getInstallmentPlan().getTotalNumberInstallments().intValue());
        installmentPlans.setPaymentAmount(new PaymentAmountDTO());
        installmentPlans.getPaymentAmount().setAmount(requestBody.getInstallmentPlan().getPaymentAmount().getAmount());
        installmentPlans.getPaymentAmount().setCurrency(requestBody.getInstallmentPlan().getPaymentAmount().getCurrency());
        installmentPlans.setPeriod(new PaymentPeriodDTO());
        installmentPlans.getPeriod().setId(requestBody.getInstallmentPlan().getPeriod().getId());
        plan.setInstallmentPlans(Collections.singletonList(installmentPlans));

        return plan;
    }

    private static ValidityPeriodDTO getValidityPeriodFromInput(PolicyDTO requestBody) {
        ValidityPeriodDTO validityPeriodDTO = new ValidityPeriodDTO();
        validityPeriodDTO.setStartDate(requestBody.getValidityPeriod().getStartDate());
        validityPeriodDTO.setEndDate(requestBody.getValidityPeriod().getEndDate());
        return validityPeriodDTO;
    }

    private static HolderDTO getHolderFronInput(PolicyDTO requestBody){
        HolderDTO holderDTO = requestBody.getHolder();
        holderDTO.setContactDetails(requestBody.getHolder().getContactDetails()
                .stream().map(CreatedInsuranceEventBusiness::getContactDetailDTO).collect(Collectors.toList()));

        return holderDTO;
    }

    private static ContactDetailDTO getContactDetailForInspection(ContactDetailDTO contactDetailDTO) {
        ContactDetailDTO contact = new ContactDetailDTO();
        if(ConstantsUtil.ContactDetailsType.EMAIL.equals(contactDetailDTO.getContact().getContactDetailType())) {
            contact.setContactType(ConstantsUtil.ContactDetailsType.EMAIL);
            contact.setValue(contactDetailDTO.getContact().getAddress());
        } else {
            contact.setContactType(ConstantsUtil.ContactDetailsType.MOBILE);
            contact.setValue(contactDetailDTO.getContact().getPhoneNumber());
        }
        return contact;
    }

    private static ContactDetailDTO getContactDetailDTO(ContactDetailDTO contactDetailDTO) {
        ContactDetailDTO contactDetailForHolderEvent = new ContactDetailDTO();
        ContactDTO contact = new ContactDTO();
        if(ConstantsUtil.ContactDetailsType.EMAIL.equals(contactDetailDTO.getContact().getContactDetailType())) {
            contact.setContactDetailType(ConstantsUtil.ContactDetailsType.EMAIL);
            contact.setValue(contactDetailDTO.getContact().getAddress());
        } else {
            contact.setContactDetailType(ConstantsUtil.ContactDetailsType.MOBILE);
            contact.setValue(contactDetailDTO.getContact().getPhoneNumber());
        }
        contactDetailForHolderEvent.setContact(contact);
        return contactDetailForHolderEvent;
    }

}
