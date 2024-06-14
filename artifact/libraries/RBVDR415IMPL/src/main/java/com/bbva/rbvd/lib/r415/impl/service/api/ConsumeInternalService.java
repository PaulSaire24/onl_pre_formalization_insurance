package com.bbva.rbvd.lib.r415.impl.service.api;

import com.bbva.elara.utility.api.connector.APIConnector;
import com.bbva.rbvd.dto.insrncsale.events.CreatedInsrcEventDTO;
import com.bbva.rbvd.dto.preformalization.util.ConstantsUtil;
import com.bbva.rbvd.lib.r415.impl.util.ConvertUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClientException;

import java.nio.charset.StandardCharsets;

public class ConsumeInternalService {

    private final APIConnector internalApiConnectorImpersonation;

    public ConsumeInternalService(APIConnector internalApiConnectorImpersonation) {
        this.internalApiConnectorImpersonation = internalApiConnectorImpersonation;
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(ConsumeInternalService.class);

    public Integer callEventUpsilonToUpdateStatusInDWP(CreatedInsrcEventDTO createdInsuranceEvent){
        LOGGER.info("***** ConsumeInternalService - callEventUpsilonToUpdateStatusInDWP() START *****");

        String jsonString = ConvertUtil.getRequestJsonFormat(createdInsuranceEvent);
        System.out.println(jsonString);

        LOGGER.info("***** ConsumeInternalService - callEventUpsilonToUpdateStatusInDWP() - Request body: {}", jsonString);

        HttpEntity<String> entity = new HttpEntity<>(jsonString, createHttpHeaders());

        try {
            ResponseEntity<Void> responseEntity = this.internalApiConnectorImpersonation.
                    exchange(ConstantsUtil.ID_PUT_EVENT_UPSILON_SERVICE, HttpMethod.POST, entity, Void.class);

            Integer httpStatusCode = responseEntity.getStatusCode().value();

            LOGGER.info("***** ConsumeInternalService - callEventUpsilonToUpdateStatusInDWP() END *****");
            return httpStatusCode;
        } catch(RestClientException ex) {
            LOGGER.info("***** ConsumeInternalService - callEventUpsilonToUpdateStatusInDWP() ***** Exception: {}", ex.getMessage());
            return 0;
        }
    }

    private HttpHeaders createHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        MediaType mediaType = new MediaType("application","json", StandardCharsets.UTF_8);
        headers.setContentType(mediaType);

        return headers;
    }

}
