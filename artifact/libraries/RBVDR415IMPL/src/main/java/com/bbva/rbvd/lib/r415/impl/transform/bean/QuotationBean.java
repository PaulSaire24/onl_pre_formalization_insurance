package com.bbva.rbvd.lib.r415.impl.transform.bean;

import com.bbva.pisd.dto.insurance.utils.PISDProperties;
import com.bbva.rbvd.dto.insrncsale.utils.RBVDProperties;
import com.bbva.rbvd.dto.preformalization.dao.QuotationDAO;
import com.bbva.rbvd.lib.r415.impl.util.ConvertUtil;

import java.util.Map;

public class QuotationBean {

    private QuotationBean(){}

    public static QuotationDAO transformQuotationMapToBean(Map<String, Object> responseQueryGetRequiredFields) {

        QuotationDAO quotationDAO = new QuotationDAO();

        quotationDAO.setInsuranceBusinessName((String) responseQueryGetRequiredFields.get("INSURANCE_BUSINESS_NAME"));
        quotationDAO.setInsuranceProductId(ConvertUtil.getBigDecimalValue(responseQueryGetRequiredFields.get(RBVDProperties.FIELD_INSURANCE_PRODUCT_ID.getValue())));
        quotationDAO.setInsuranceProductDesc((String) responseQueryGetRequiredFields.get(PISDProperties.FIELD_INSURANCE_PRODUCT_DESC.getValue()));
        quotationDAO.setContractDurationNumber(ConvertUtil.getBigDecimalValue(responseQueryGetRequiredFields.get(RBVDProperties.FIELD_CONTRACT_DURATION_NUMBER.getValue())));
        quotationDAO.setContractDurationType((String) responseQueryGetRequiredFields.get(RBVDProperties.FIELD_CONTRACT_DURATION_TYPE.getValue()));
        quotationDAO.setInsuranceCompanyQuotaId((String) responseQueryGetRequiredFields.get(RBVDProperties.FIELD_INSURANCE_COMPANY_QUOTA_ID.getValue()));
        quotationDAO.setInsuranceModalityName((String) responseQueryGetRequiredFields.get(PISDProperties.FIELD_INSURANCE_MODALITY_NAME.getValue()));

        return quotationDAO;
    }

}
