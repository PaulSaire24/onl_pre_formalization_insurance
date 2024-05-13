package com.bbva.rbvd.lib.r415.impl.service.dao;

import com.bbva.pisd.dto.insurancedao.entities.PaymentPeriodEntity;
import com.bbva.rbvd.dto.cicsconnection.icr2.ICR2Response;
import com.bbva.rbvd.dto.insrncsale.policy.PolicyDTO;
import com.bbva.rbvd.dto.preformalization.dao.QuotationDAO;

public interface IContractDAO {

    void insertInsuranceContract(PolicyDTO input, QuotationDAO quotationDAO, ICR2Response icr2Response,
                                 boolean isEndorsement, PaymentPeriodEntity paymentPeriod);

}
