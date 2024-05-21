package com.bbva.rbvd.lib.r415.impl.service.dao.impl;

import com.bbva.pisd.lib.r601.PISDR601;
import com.bbva.rbvd.dto.preformalization.dao.QuotationDAO;
import com.bbva.rbvd.lib.r415.impl.service.dao.IQuotationDAO;
import com.bbva.rbvd.lib.r415.impl.transform.bean.QuotationBean;
import com.bbva.rbvd.lib.r415.impl.util.ValidationUtil;

import java.util.Map;

public class QuotationDAOImpl implements IQuotationDAO {

    private final PISDR601 pisdr601;

    public QuotationDAOImpl(PISDR601 pisdr601) {
        this.pisdr601 = pisdr601;
    }

    @Override
    public QuotationDAO getQuotationDetails(String quotationId) {
        QuotationDAO quotationDAO = null;

        Map<String, Object> contractRequiredFields = pisdr601.executeFindQuotationDetailForPreEmision(quotationId);

        if(!ValidationUtil.mapIsNullOrEmpty(contractRequiredFields)){
            quotationDAO = QuotationBean.transformQuotationMapToBean(contractRequiredFields);
        }

        return quotationDAO;
    }

}
