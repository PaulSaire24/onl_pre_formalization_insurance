package com.bbva.rbvd.lib.r415.impl.service.dao.impl;

import com.bbva.pisd.dto.insurancedao.entities.PaymentPeriodEntity;
import com.bbva.pisd.lib.r226.PISDR226;
import com.bbva.rbvd.dto.cicsconnection.icr2.ICR2Response;
import com.bbva.rbvd.dto.insrncsale.dao.InsuranceContractDAO;
import com.bbva.rbvd.dto.insrncsale.policy.PolicyDTO;
import com.bbva.rbvd.dto.insrncsale.utils.RBVDErrors;
import com.bbva.rbvd.dto.preformalization.dao.QuotationDAO;
import com.bbva.rbvd.lib.r415.impl.service.dao.IContractDAO;
import com.bbva.rbvd.lib.r415.impl.transform.bean.ContractBean;
import com.bbva.rbvd.lib.r415.impl.transform.map.ContractMap;
import com.bbva.rbvd.lib.r415.impl.util.ValidationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class ContractDAOImpl implements IContractDAO {
    private static final Logger LOGGER = LoggerFactory.getLogger(ContractDAOImpl.class);

    private final PISDR226 pisdr226;

    public ContractDAOImpl(PISDR226 pisdr226) {
        this.pisdr226 = pisdr226;
    }


    @Override
    public void insertInsuranceContract(PolicyDTO input, QuotationDAO quotationDAO,
                                        ICR2Response icr2Response, boolean isEndorsement, PaymentPeriodEntity paymentPeriod) {
        InsuranceContractDAO contractDao = ContractBean.buildInsuranceContract(input, quotationDAO,
                icr2Response, isEndorsement, paymentPeriod);
        LOGGER.info("***** ContractDAOImpl - insertInsuranceContract() | contractDao: {} *****",contractDao.toString());

        Map<String, Object> argumentsForSaveContract = ContractMap.createSaveContractArguments(contractDao);
        LOGGER.info("***** ContractDAOImpl - insertInsuranceContract() | argumentsForSaveContract: {} *****",argumentsForSaveContract);

        int insertContract = this.pisdr226.executeInsertInsuranceContract(argumentsForSaveContract);

        ValidationUtil.validateInsertion(insertContract, RBVDErrors.INSERTION_ERROR_IN_CONTRACT_TABLE);
    }

}
