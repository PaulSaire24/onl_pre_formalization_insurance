package com.bbva.rbvd.lib.r415.impl.service.dao.impl;

import com.bbva.pisd.lib.r012.PISDR012;
import com.bbva.rbvd.dto.insrncsale.utils.RBVDProperties;
import com.bbva.rbvd.dto.preformalization.dao.QuotationDAO;
import com.bbva.rbvd.lib.r415.impl.service.dao.IQuotationDAO;
import com.bbva.rbvd.lib.r415.impl.transform.bean.QuotationBean;
import com.bbva.rbvd.lib.r415.impl.util.ValidationUtil;

import java.util.Collections;
import java.util.Map;

public class QuotationDAOImpl implements IQuotationDAO {

    private final PISDR012 pisdr012;

    public QuotationDAOImpl(PISDR012 pisdr012) {
        this.pisdr012 = pisdr012;
    }

    @Override
    public QuotationDAO getQuotationDetails(String quotationId) {
        QuotationDAO quotationDAO = null;

        Map<String, Object> quotationIdArgument = Collections.singletonMap(
                RBVDProperties.FIELD_POLICY_QUOTA_INTERNAL_ID.getValue(),quotationId);

        //DEVUELVE MUCHOS CAMPOS, REEMPLAZAR POR OTRO QUERY. Usar lib_dao_insurance_quotation
        Map<String, Object> contractRequiredFields = pisdr012.executeGetASingleRow(
                RBVDProperties.DYNAMIC_QUERY_FOR_INSURANCE_CONTRACT.getValue(), quotationIdArgument);

        if(!ValidationUtil.mapIsNullOrEmpty(contractRequiredFields)){
            quotationDAO = QuotationBean.transformQuotationMapToBean(contractRequiredFields);
        }

        return quotationDAO;
    }

}
