package com.bbva.rbvd;

import com.bbva.elara.domain.transaction.Context;
import com.bbva.elara.domain.transaction.RequestHeaderParamsName;
import com.bbva.elara.domain.transaction.Severity;
import com.bbva.elara.domain.transaction.TransactionParameter;
import com.bbva.elara.domain.transaction.request.TransactionRequest;
import com.bbva.elara.domain.transaction.request.body.CommonRequestBody;
import com.bbva.elara.domain.transaction.request.header.CommonRequestHeader;
import com.bbva.elara.test.osgi.DummyBundleContext;
import com.bbva.rbvd.dto.insrncsale.policy.PolicyDTO;
import com.bbva.rbvd.lib.rbvd118.RBVDR118;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath:/META-INF/spring/elara-test.xml",
        "classpath:/META-INF/spring/RBVDT11801PETest.xml"})
public class RBVDT11801PETransactionTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(RBVDT11801PETransactionTest.class);

    @Spy
    @Autowired
    private RBVDT11801PETransaction transaction;

    @Resource(name = "dummyBundleContext")
    private DummyBundleContext bundleContext;

    @Resource(name = "rbvdR118")
    private RBVDR118 rbvdr118;

    @Mock
    private CommonRequestHeader header;

    @Mock
    private TransactionRequest transactionRequest;

    @Before
    public void initializeClass() throws Exception {
        MockitoAnnotations.initMocks(this);

        this.transaction.start(bundleContext);
        this.transaction.setContext(new Context());

        CommonRequestBody commonRequestBody = new CommonRequestBody();
        commonRequestBody.setTransactionParameters(new ArrayList<>());

        this.transactionRequest.setBody(commonRequestBody);

        when(header.getHeaderParameter(RequestHeaderParamsName.REQUESTID)).thenReturn("traceId");
        when(header.getHeaderParameter(RequestHeaderParamsName.CHANNELCODE)).thenReturn("BI");
        when(header.getHeaderParameter(RequestHeaderParamsName.USERCODE)).thenReturn("user");

        this.transactionRequest.setHeader(header);
        this.transaction.getContext().setTransactionRequest(transactionRequest);

        this.addParameter("isDataTreatment", true);
        this.addParameter("hasAcceptedContract", true);
        this.addParameter("productId", "830");
    }

    @Test
    public void execute() throws IOException {
        PolicyDTO simulateResponse = new PolicyDTO();
        simulateResponse.setOperationDate(new Date());
        simulateResponse.setProductId("842");

        when(rbvdr118.executeLogicPreFormalization(anyObject())).thenReturn(simulateResponse);

        this.transaction.getContext().getParameterList().forEach(
                (key, value) -> LOGGER.info("Key {} with value: {}", key, value)
        );

        this.transaction.execute();

        assertTrue(this.transaction.getAdviceList().isEmpty());
    }

    @Test
    public void testNotNull() {
        assertNotNull(this.transaction);
        this.transaction.execute();

        assertNotNull(this.transaction.getSeverity());
    }

    @Test
    public void testNull() {
        when(rbvdr118.executeLogicPreFormalization(anyObject())).thenReturn(null);
        this.transaction.execute();
        assertEquals(Severity.ENR.getValue(), this.transaction.getSeverity().getValue());
    }

    private void addParameter(final String parameter, final Object value) {
        final TransactionParameter tParameter = new TransactionParameter(parameter, value);
        transaction.getContext().getParameterList().put(parameter, tParameter);
    }

}
