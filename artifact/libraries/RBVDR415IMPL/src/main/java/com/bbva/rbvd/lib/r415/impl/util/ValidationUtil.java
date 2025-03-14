package com.bbva.rbvd.lib.r415.impl.util;


import com.bbva.apx.exception.business.BusinessException;
import com.bbva.rbvd.dto.cicsconnection.icr3.ICR3Response;
import com.bbva.rbvd.dto.cicsconnection.utils.HostAdvice;
import com.bbva.rbvd.dto.insrncsale.policy.ParticipantDTO;
import com.bbva.rbvd.dto.preformalization.util.RBVDMessageError;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;


public class ValidationUtil {

    private ValidationUtil(){}

    public static ParticipantDTO filterOneParticipantByType(List<ParticipantDTO> participants,String participantType){
        if(!CollectionUtils.isEmpty(participants)){
            Optional<ParticipantDTO> filter = participants.stream()
                    .filter(participantDTO -> participantType.equals(participantDTO.getParticipantType().getId()))
                    .findFirst();
            return filter.orElse(null);
        }else{
            return null;
        }
    }

    public static List<ParticipantDTO> filterListParticipantsByType(List<ParticipantDTO> participants, String participantType) {
        List<ParticipantDTO> participantDTOS = participants.stream()
                    .filter(participantDTO -> participantType.equals(participantDTO.getParticipantType().getId()))
                    .collect(Collectors.toList());
        return participantDTOS.isEmpty() ? null : participantDTOS;
    }

    public static void validateInsertion(int insertedRows, String code, String message) {
        if (insertedRows != 1) {
            throw new BusinessException(code,false,message);
        }
    }

    public static void validateMultipleInsertion(int[] insertedRows, String code, String message) {
        if(isNull(insertedRows) || insertedRows.length == 0 || !Arrays.stream(insertedRows).allMatch(n -> n == 1)) {
            throw new BusinessException(code,false,message);
        }
    }

    public static void validateObjectIsNull(Object object, String adviceCode, String message) {
        if (isNull(object)) {
            throw new BusinessException(adviceCode,false,message);
        }

        if (object instanceof Map && ((Map<?, ?>) object).isEmpty()) {
            throw new BusinessException(adviceCode, false, message);
        }

    }


    public static boolean mapIsNullOrEmpty(Map<?,?> map){
        return map == null || map.isEmpty();
    }

    public static void validateQuotationExistsInContract(boolean validateExist) {
        if (validateExist) {
            throw new BusinessException(
                    RBVDMessageError.QUOTATION_EXIST_IN_CONTRACT.getAdviceCode(),
                    RBVDMessageError.QUOTATION_EXIST_IN_CONTRACT.isRollback(),
                    RBVDMessageError.QUOTATION_EXIST_IN_CONTRACT.getMessage());
        }
    }

    public static boolean isListContainsValue(String propertyInConsole,String value){
        List<String> listValues = Arrays.asList(propertyInConsole.split(","));
        return listValues.contains(value);
    }

    public static void checkHostAdviceErrors(ICR3Response icr3Response) {
        if(!CollectionUtils.isEmpty(icr3Response.getHostAdviceCode())){
            HostAdvice firstAdviceError = icr3Response.getHostAdviceCode().get(0);
            throw new BusinessException(firstAdviceError.getCode(),false,firstAdviceError.getDescription());
        }
    }

    public static boolean allValuesNotNullOrEmpty(List<Object> values) {
        for (Object value : values) {
            if (value == null) {
                return false;
            }

            if (value instanceof String && ((String) value).isEmpty()) {
                return false;
            }
        }
        return true;
    }

}