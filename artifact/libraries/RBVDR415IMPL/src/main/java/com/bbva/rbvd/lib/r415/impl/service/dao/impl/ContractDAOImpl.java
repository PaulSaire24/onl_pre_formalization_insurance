package com.bbva.rbvd.lib.r415.impl.service.dao.impl;

import com.bbva.pisd.lib.r226.PISDR226;
import com.bbva.rbvd.dto.preformalization.dao.ContractDAO;
import com.bbva.rbvd.dto.preformalization.util.RBVDMessageError;
import com.bbva.rbvd.lib.r415.impl.service.dao.IContractDAO;
import com.bbva.rbvd.lib.r415.impl.transfer.PayloadStore;
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
    public void insertInsuranceContract(PayloadStore payloadStore) {
        ContractDAO contractDao = ContractBean.buildInsuranceContract(payloadStore);
        LOGGER.info("***** ContractDAOImpl - insertInsuranceContract() | contractDao: {} *****",contractDao);

        Map<String, Object> argumentsForSaveContract = ContractMap.createSaveContractArguments(contractDao);
        LOGGER.info("***** ContractDAOImpl - insertInsuranceContract() | argumentsForSaveContract: {} *****",argumentsForSaveContract);

        int insertContract = this.pisdr226.executeInsertInsuranceContract(argumentsForSaveContract);

        ValidationUtil.validateInsertion(insertContract,
                RBVDMessageError.ERROR_INSERT_INSURANCE_CONTRACT.getAdviceCode(),
                RBVDMessageError.ERROR_INSERT_INSURANCE_CONTRACT.getMessage());
    }

}
