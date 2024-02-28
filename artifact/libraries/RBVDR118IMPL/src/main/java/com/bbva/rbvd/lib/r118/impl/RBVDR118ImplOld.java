package com.bbva.rbvd.lib.r118.impl;

import com.bbva.apx.exception.business.BusinessException;
import com.bbva.pisd.dto.insurance.aso.CustomerListASO;
import com.bbva.rbvd.dto.insrncsale.aso.emision.PolicyASO;
import com.bbva.rbvd.dto.insrncsale.bo.emision.EmisionBO;
import com.bbva.rbvd.dto.insrncsale.dao.InsuranceContractDAO;
import com.bbva.rbvd.dto.insrncsale.dao.RequiredFieldsEmissionDAO;
import com.bbva.rbvd.dto.insrncsale.utils.RBVDProperties;
import com.bbva.rbvd.dto.preformalization.PreformalizationDTO;
import com.bbva.rbvd.lib.r118.impl.util.ConstantsUtil;
import com.bbva.rbvd.lib.r118.impl.util.ValidationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

import static java.util.Collections.singletonMap;

public class RBVDR118ImplOld extends RBVDR118Abstract {
    private static final Logger LOGGER = LoggerFactory.getLogger(RBVDR118ImplOld.class);
    ValidationUtil validationUtil;

    @Override
    public PreformalizationDTO executePreFormalization(PreformalizationDTO requestBody) {

        EmisionBO rimacResponse;
        PreformalizationDTO responseBody;
        Boolean isEndorsement;
        String endosatarioRuc;
        Double endosatarioPorcentaje;
        CustomerListASO customerList;
        String policyNumber = this.applicationConfigurationService.getProperty("policyWithoutNumber");

        try {
            // Step 2 : PISDR012 - executeGetASingleRow ************************************************************
            Map<String, Object> quotationIdArgument = this.createSingleArgument(requestBody.getQuotationId(),
                    RBVDProperties.FIELD_POLICY_QUOTA_INTERNAL_ID.getValue());

            Map<String, Object> responseValidateIfPolicyExists = pisdR012.executeGetASingleRow(RBVDProperties.QUERY_VALIDATE_IF_POLICY_EXISTS.getValue(),
                    quotationIdArgument);

            ValidationUtil.validateIfPolicyExists(responseValidateIfPolicyExists);

            Map<String, Object> responseQueryGetRequiredFields = pisdR012.executeGetASingleRow(RBVDProperties.DYNAMIC_QUERY_FOR_INSURANCE_CONTRACT.getValue(),
                    quotationIdArgument);

            Map<String, Object> frequencyTypeArgument = this.createSingleArgument(requestBody.getInstallmentPlan().getPeriod().getId(),
                    RBVDProperties.FIELD_POLICY_PAYMENT_FREQUENCY_TYPE.getValue());

            Map<String, Object> responseQueryGetPaymentPeriod = pisdR012.executeGetASingleRow(RBVDProperties.QUERY_SELECT_PAYMENT_PERIOD.getValue(),
                    frequencyTypeArgument);

            // End of Step 2 ****************************************************************************************

            // Step 3 : PISDR401 - executeGetProductById ************************************************************

            Map<String, Object> responseQueryGetProductById = (Map<String, Object>) this.pisdR401.executeGetProductById(ConstantsUtil.Queries.QUERY_SELECT_PRODUCT_BY_PRODUCT_TYPE,
                    singletonMap(RBVDProperties.FIELD_INSURANCE_PRODUCT_TYPE.getValue(), requestBody.getProductId()));

            RequiredFieldsEmissionDAO emissionDao = ValidationUtil.validateResponseQueryGetRequiredFields(responseQueryGetRequiredFields, responseQueryGetPaymentPeriod);

            String rangePropertyToValidate = ConstantsUtil.RBVDR118.PROPERTY_VALIDATION_RANGE + requestBody.getProductId() + "." + requestBody.getSaleChannelId();

            if (this.applicationConfigurationService.getDefaultProperty(rangePropertyToValidate, "0").equals("1"))
                ValidationUtil.validateAmountQuotation(responseQueryGetRequiredFields, requestBody, this.applicationConfigurationService);

            ValidationUtil.updatePaymentRequirementStatus(requestBody);

            // End of Step 3 ****************************************************************************************


            // Aqui se debería invocar a la ICR2 mediante interbackend

            PolicyASO asoResponse = rbvdR201.executePrePolicyEmissionASO(this.mapperHelper.buildAsoRequest(requestBody));

            String hostBranchId = asoResponse.getData().getBank().getBranch().getId();
            requestBody.getBank().getBranch().setId(hostBranchId);

            evaluateBranchIdValue(requestBody);

            validateDigitalSale(requestBody);

            EmisionBO rimacRequest = this.mapperHelper.buildRequestBodyRimac(requestBody.getInspection(), createSecondDataValue(asoResponse),
                    requestBody.getSaleChannelId(), asoResponse.getData().getId(), requestBody.getBank().getBranch().getId());

            isEndorsement = validateEndorsement(requestBody);

            InsuranceContractDAO contractDao = this.mapperHelper.buildInsuranceContract(requestBody, emissionDao, asoResponse.getData().getId(), isEndorsement);

            // Hasta aqui se debe invocar a la ICR2 mediante interbackend


            // Step 5 : PISDR012 - executeInsertSingleRow ************************************************************

            /*Map<String, Object> argumentsForSaveContract = this.mapperHelper.createSaveContractArguments(contractDao);

            int insertedContract = this.pisdR012.executeInsertSingleRow(PISDProperties.QUERY_INSERT_INSURANCE_CONTRACT.getValue(), argumentsForSaveContract,
                    RBVDProperties.FIELD_INSURANCE_CONTRACT_ENTITY_ID.getValue(), RBVDProperties.FIELD_INSURANCE_CONTRACT_BRANCH_ID.getValue(),
                    RBVDProperties.FIELD_INSURANCE_PRODUCT_ID.getValue(), RBVDProperties.FIELD_INSURANCE_MODALITY_TYPE.getValue(),
                    RBVDProperties.FIELD_INSURANCE_COMPANY_ID.getValue(), RBVDProperties.FIELD_INSURANCE_CONTRACT_START_DATE.getValue(),
                    RBVDProperties.FIELD_CUSTOMER_ID.getValue(), RBVDProperties.FIELD_INSRNC_CO_CONTRACT_STATUS_TYPE.getValue(),
                    RBVDProperties.FIELD_INSRC_CONTRACT_INT_ACCOUNT_ID.getValue(), RBVDProperties.FIELD_USER_AUDIT_ID.getValue());

            validateInsertion(insertedContract, RBVDErrors.INSERTION_ERROR_IN_CONTRACT_TABLE);

            List<InsuranceCtrReceiptsDAO> receiptsList = this.mapperHelper.buildInsuranceCtrReceipts(asoResponse, requestBody, responseQueryGetRequiredFields);

            Map<String, Object>[] receiptsArguments = this.mapperHelper.createSaveReceiptsArguments(receiptsList);

            validateMultipleInsertion(this.pisdR012.executeMultipleInsertionOrUpdate(RBVDProperties.QUERY_INSERT_INSURANCE_CTR_RECEIPTS.getValue(),
                    receiptsArguments), RBVDErrors.INSERTION_ERROR_IN_RECEIPTS_TABLE);

            IsrcContractMovDAO contractMovDao = this.mapperHelper.buildIsrcContractMov(asoResponse, requestBody.getCreationUser(), requestBody.getUserAudit());
            Map<String, Object> argumentsForContractMov = this.mapperHelper.createSaveContractMovArguments(contractMovDao);

            int insertedContractMove = this.pisdR012.executeInsertSingleRow(RBVDProperties.QUERY_INSERT_INSRNC_CONTRACT_MOV.getValue(), argumentsForContractMov);

            validateInsertion(insertedContractMove, RBVDErrors.INSERTION_ERROR_IN_CONTRACT_MOV_TABLE);

            // End of Step 5 ****************************************************************************************

            // Step 6 : PISDR012 - executeGetRolesByProductAndModality *********************************************

            Map<String, Object> responseQueryRoles = this.pisdR012.executeGetRolesByProductAndModality(emissionDao.getInsuranceProductId(), requestBody.getProductPlan().getId());

            if (!isEmpty((List) responseQueryRoles.get(PISDProperties.KEY_OF_INSRC_LIST_RESPONSES.getValue()))) {

                List<IsrcContractParticipantDAO> participants = this.mapperHelper.buildIsrcContractParticipants(requestBody, responseQueryRoles, asoResponse.getData().getId());

                Map<String, Object>[] participantsArguments = this.mapperHelper.createSaveParticipantArguments(participants);

                validateMultipleInsertion(this.pisdR012.executeMultipleInsertionOrUpdate(RBVDProperties.QUERY_INSERT_INSRNC_CTR_PARTICIPANT.getValue(),
                        participantsArguments), RBVDErrors.INSERTION_ERROR_IN_PARTICIPANT_TABLE);
            }

            // End of Step 6 ****************************************************************************************

            // Step 7 : PISDR012 - executeMultipleInsertionOrUpdate *************************************************

            if (!isEmpty(requestBody.getRelatedContracts())) {
                List<RelatedContractDAO> relatedContractsDao = this.mapperHelper.buildRelatedContractsWithInsurance(requestBody, contractDao);
                Map<String, Object>[] relatedContractsArguments = this.mapperHelper.createSaveRelatedContractsArguments(relatedContractsDao);
                this.pisdR012.executeMultipleInsertionOrUpdate(RBVDProperties.QUERY_INSERT_INSURANCE_CONTRACT_DETAILS.getValue(), relatedContractsArguments);
            }

            if (isEndorsement) {
                endosatarioRuc = requestBody.getParticipants().get(1).getIdentityDocument().getNumber();
                endosatarioPorcentaje = requestBody.getParticipants().get(1).getBenefitPercentage();

                rimacRequest.getPayload().setEndosatario(new EndosatarioBO(endosatarioRuc, endosatarioPorcentaje.intValue()));

                Map<String, Object> argumentsForSaveEndorsement = this.mapperHelper.createSaveEndorsementArguments(contractDao, endosatarioRuc, endosatarioPorcentaje);

                int insertedContractEndorsement = this.pisdR012.executeInsertSingleRow(RBVDProperties.QUERY_INSERT_POLICY_ENDORSEMENT.getValue(), argumentsForSaveEndorsement);

                validateInsertion(insertedContractEndorsement, RBVDErrors.INSERTION_ERROR_IN_ENDORSEMENT_TABLE);
            }

            if (!requestBody.getProductId().equals(RBVDProperties.INSURANCE_PRODUCT_TYPE_VEH.getValue())) {
                customerList = this.rbvdR201.executeGetCustomerInformation(requestBody.getHolder().getId());
                try {
                    validateQueryCustomerResponse(customerList);
                } catch (BusinessException ex) {
                    return null;
                }

                EmisionBO generalEmisionRequest = this.mapperHelper.mapRimacEmisionRequest(rimacRequest, requestBody,
                        responseQueryGetRequiredFields, responseQueryGetProductById, customerList);

                setOrganization(generalEmisionRequest, requestBody, customerList);
                rimacResponse = rbvdR201.executePrePolicyEmissionService(generalEmisionRequest, emissionDao.getInsuranceCompanyQuotaId(), requestBody.getTraceId(), requestBody.getProductId());
            } else {
                rimacResponse = rbvdR201.executePrePolicyEmissionService(rimacRequest, emissionDao.getInsuranceCompanyQuotaId(), requestBody.getTraceId(), requestBody.getProductId());
            }


            if (nonNull(rimacResponse)) {

                Map<String, Object> argumentsRimacContractInformation = this.mapperHelper.getRimacContractInformation(rimacResponse, asoResponse.getData().getId());

                int updatedContract = this.pisdR012.executeInsertSingleRow("PISD.UPDATE_CONTRACT", argumentsRimacContractInformation,
                        RBVDProperties.FIELD_INSURANCE_CONTRACT_END_DATE.getValue(), RBVDProperties.FIELD_INSURANCE_POLICY_END_DATE.getValue(),
                        RBVDProperties.FIELD_LAST_INSTALLMENT_DATE.getValue(), RBVDProperties.FIELD_PERIOD_NEXT_PAYMENT_DATE.getValue());

                validateInsertion(updatedContract, RBVDErrors.INSERTION_ERROR_IN_CONTRACT_TABLE);

                String productsCalculateValidityMonths = this.applicationConfigurationService.getDefaultProperty(PROPERTY_ONLY_FIRST_RECEIPT, "");
                String operacionGlossaryDesc = responseQueryGetRequiredFields.get(RBVDProperties.FIELD_OPERATION_GLOSSARY_DESC.getValue()).toString();

                if (!Arrays.asList(productsCalculateValidityMonths.split(",")).contains(operacionGlossaryDesc)) {
                    List<InsuranceCtrReceiptsDAO> otherReceipts = rimacResponse.getPayload().getCuotasFinanciamiento().stream().
                            filter(cuota -> cuota.getCuota().compareTo(1L) > 0).map(cuota -> this.generateNextReceipt(asoResponse, cuota)).
                            collect(toList());

                    Map<String, Object>[] receiptUpdateArguments = this.mapperHelper.createSaveReceiptsArguments(otherReceipts);

                    validateMultipleInsertion(this.pisdR012.executeMultipleInsertionOrUpdate("PISD.UPDATE_EXPIRATION_DATE_RECEIPTS",
                            receiptUpdateArguments), RBVDErrors.INSERTION_ERROR_IN_RECEIPTS_TABLE);
                }

                policyNumber = rimacResponse.getPayload().getNumeroPoliza();

                String intAccountId = asoResponse.getData().getId().substring(10);

                isItNecessaryUpdateEndorsementRow(isEndorsement, policyNumber, intAccountId);

            }

            responseBody = requestBody;

            this.mapperHelper.mappingOutputFields(responseBody, asoResponse, rimacResponse, emissionDao);

            CreatedInsrcEventDTO createdInsrcEventDTO = this.mapperHelper.buildCreatedInsuranceEventObject(responseBody);

            Integer httpStatusCode = this.rbvdR201.executePutEventUpsilonService(createdInsrcEventDTO);
            */

            return responseBody;
        } catch (BusinessException ex) {
            this.addAdviceWithDescription(ex.getAdviceCode(), ex.getMessage());
            return null;
        }
    }

    private Map<String, Object> createSingleArgument(String argument, String parameterName) {
        Map<String, Object> mapArgument = new HashMap<>();
        if (RBVDProperties.FIELD_POLICY_PAYMENT_FREQUENCY_TYPE.getValue().equals(parameterName)) {
            String frequencyType = this.applicationConfigurationService.getProperty(argument);
            mapArgument.put(parameterName, frequencyType);
            return mapArgument;
        }
        mapArgument.put(parameterName, argument);
        return mapArgument;
    }
}
