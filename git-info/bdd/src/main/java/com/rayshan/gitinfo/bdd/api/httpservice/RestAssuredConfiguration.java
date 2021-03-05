package com.rayshan.gitinfo.bdd.api.httpservice;

import com.rayshan.gitinfo.bdd.constants.HTTPConstants;
import io.restassured.config.RestAssuredConfig;
import io.restassured.config.SSLConfig;
import io.restassured.path.json.config.JsonPathConfig;

import static io.restassured.config.DecoderConfig.ContentDecoder.DEFLATE;
import static io.restassured.config.DecoderConfig.decoderConfig;
import static io.restassured.config.JsonConfig.jsonConfig;
import static io.restassured.config.RedirectConfig.redirectConfig;
import static io.restassured.config.RestAssuredConfig.newConfig;

public class RestAssuredConfiguration {
    private InitEnvProperties initEnvProperties;

    private RestAssuredConfig restAssuredConfig;

    public RestAssuredConfiguration(InitEnvProperties initEnvProperties) {
        this.initEnvProperties = initEnvProperties;
    }

    public RestAssuredConfig getRestAssuredConfig() {
        if (restAssuredConfig == null) {
            restAssuredConfig = buildRestAssuredConfig();
        }
        return restAssuredConfig;
    }

    private RestAssuredConfig buildRestAssuredConfig() {
        RestAssuredConfig config =
                newConfig()
                        .jsonConfig(
                                jsonConfig()
                                        .numberReturnType(
                                                JsonPathConfig.NumberReturnType.BIG_DECIMAL))
                        .redirect(
                                redirectConfig()
                                        .followRedirects(
                                                initEnvProperties.getBoolean(
                                                        HTTPConstants.FOLLOW_REDIRECTS, true)));

        if (!initEnvProperties.getBoolean(HTTPConstants.GZIP_SUPPORT, true)) {
            config = config.decoderConfig(decoderConfig().contentDecoders(DEFLATE));
        }
        config = config.sslConfig(new SSLConfig().allowAllHostnames());
        return config;
    }
}
