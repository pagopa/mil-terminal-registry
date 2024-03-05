package it.pagopa.swclient.mil.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.logging.Log;
import it.pagopa.swclient.mil.controller.model.CommonHeader;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.stream.Collectors;

public class Utility {
    private Utility() {
    }

    public static String decodeBearerPayload(CommonHeader header) throws JsonProcessingException {
        String[] chunks = header.authorization().split("\\.");
        String payload = new String(Base64.getUrlDecoder().decode(chunks[1]));

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(payload);

        return jsonNode.get("sub").asText();
    }

    public static String generateTerminalUuid() {
        return new SecureRandom().ints(0, 16)
                .mapToObj(Integer::toHexString)
                .limit(24)
                .collect(Collectors.joining());
    }
}
