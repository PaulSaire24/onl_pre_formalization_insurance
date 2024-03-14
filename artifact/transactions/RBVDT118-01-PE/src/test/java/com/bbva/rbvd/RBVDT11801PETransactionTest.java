package com.bbva.rbvd;

import com.bbva.elara.domain.transaction.Severity;
import com.bbva.rbvd.dto.preformalization.PreformalizationDTO;
import com.bbva.rbvd.lib.rbvd118.RBVDR118;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.slf4j.Logger;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RBVDT11801PETransactionTest {

    @Mock
    private RBVDR118 rbvdR118;

    @Mock
    private Logger LOGGER;

    @InjectMocks
    private RBVDT11801PETransaction transaction;

}