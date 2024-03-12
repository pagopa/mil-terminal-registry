package it.pagopa.swclient.mil.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.pagopa.swclient.mil.controller.model.CommonHeader;
import java.util.Base64;
import java.util.UUID;

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
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }
}
