package com.rayshan.gitinfo.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.rayshan.gitinfo.bdd.constants.FilePathConstants;
import com.rayshan.gitinfo.bdd.constants.RequestConstants;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class HttpUtil {
    private HttpUtil() {}

    public static String getBasePath(String apiName) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode objectNode =
                mapper.readValue(
                        new File(
                                ClassLoader.getSystemResource(
                                        FilePathConstants.TEST_DATA_SCHEMA_FILE.replaceAll(
                                                "api_path", apiName))
                                        .getPath()),
                        ObjectNode.class);
        return getJsonNodeValue(objectNode, RequestConstants.BASE_PATH);
    }

    public static String getJsonNodeValue(JsonNode jsonNode, String nodeKey) {
        String value;
        JsonNode node = jsonNode;
        if (jsonNode.isArray()) {
            if (jsonNode.size() == 0) {
                return null;
            } else if (jsonNode.size() == 1) {
                node = jsonNode.get(0);
            } else {
                node = jsonNode.get(0);
            }
        }

        if (nodeKey.contains("/")) {
            value = node.at(nodeKey) == null ? null : node.at(nodeKey).asText();
        } else {
            value = node.get(nodeKey) == null ? null : node.get(nodeKey).asText();
        }
        return value;
    }

    public static Map<String, String> getHeader(String apiName) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode objectNode =
                mapper.readValue(
                        new File(
                                ClassLoader.getSystemResource(
                                        FilePathConstants.TEST_DATA_SCHEMA_FILE.replaceAll(
                                                "api_path", apiName))
                                        .getPath()),
                        ObjectNode.class);
        return mapper.convertValue(
                objectNode.get(RequestConstants.HEADER),
                new TypeReference<Map<String, String>>() {});
    }

    public static String parseJsonData(String apiName, String requestName) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode defaults =
                mapper.readValue(
                        new File(
                                ClassLoader.getSystemResource(
                                        FilePathConstants.TEST_DATA_JSON_FILE
                                                .replaceAll("api_path", apiName)
                                                .replaceAll("request", requestName))
                                        .getPath()),
                        ObjectNode.class);
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(defaults);
    }
}
