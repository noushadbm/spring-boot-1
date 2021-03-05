package com.rayshan.gitinfo.bdd.api.httpservice;

import com.rayshan.gitinfo.bdd.api.httpcore.HttpTrustManager;
import com.rayshan.gitinfo.bdd.api.httpcore.HttpWaitCondition;
import com.rayshan.gitinfo.bdd.constants.HTTPConstants;
import com.rayshan.gitinfo.bdd.exception.BddException;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class HttpRequest {
    private static final Logger log = LoggerFactory.getLogger(HttpRequest.class);
    private final InitEnvProperties initEnvProperties;
    private String requestBody;
    private Map<String, String> header = new HashMap<>();

    private RequestSpecification requestSpecification;
    private RestAssuredConfiguration restAssuredConfiguration;
    private HttpWaitCondition httpWaitCondition;

    public HttpRequest(InitEnvProperties initEnvProperties) {
        this.restAssuredConfiguration = new RestAssuredConfiguration(initEnvProperties);
        this.initEnvProperties = initEnvProperties;
        initNewMinimumRequestSpec(null);
    }

    public void initNewMinimumRequestSpec(String baseURI) {
        try {
            requestSpecification =
                    RestAssured.given().config(restAssuredConfiguration.getRestAssuredConfig());

            if (initEnvProperties.getBoolean("logging_enabled", false)) {
                requestSpecification.filter(new ResponseLoggingFilter());
                requestSpecification.filter(new RequestLoggingFilter());
            }
            httpWaitCondition = null;
            onCreate(baseURI);
        } catch (Exception e) {
            throw new BddException(e);
        }
    }

    public void onCreate(String baseURI) {
        Optional<String> baseUri;
        if (baseURI == null) {
            baseUri = Optional.of(initEnvProperties.getEnvProperty(HTTPConstants.ILB_BASE_URI));
        } else {
            baseUri = Optional.of(baseURI);
        }

        baseUri.ifPresent(this::baseUri);

        Optional<String> proxy =
                Optional.ofNullable(initEnvProperties.getEnvProperty(HTTPConstants.PROXY));
        if (proxy.isPresent()
                && (initEnvProperties
                .getEnvProperty(HTTPConstants.GCLB_BASE_URI)
                .contains(baseUri.get())
                || initEnvProperties
                .getEnvProperty(HTTPConstants.APIGEE_BASE_URI)
                .contains(baseUri.get()))) {
            URI uri;
            try {
                uri = new URI(proxy.get());
                requestSpecification.proxy(uri);
            } catch (URISyntaxException e) {
                log.error("Unable to apply proxy settings in HTTP request", e);
            }
        }

        boolean urlEncodingEnabled =
                initEnvProperties.getBoolean(HTTPConstants.URL_ENCODING_ENABLED);
        requestSpecification.urlEncodingEnabled(urlEncodingEnabled);

        boolean relaxedHttps = initEnvProperties.getBoolean(HTTPConstants.RELAXED_HTTPS);
        if (relaxedHttps) {
            requestSpecification.relaxedHTTPSValidation();
            HttpTrustManager.trustAllHttpsCertificates();
        }
    }

    public void baseUri(String baseUri) {
        initEnvProperties.put(HTTPConstants.ILB_BASE_URI, baseUri);
        requestSpecification.baseUri(baseUri);
    }

    public RestAssuredConfiguration getRestAssuredConfiguration() {
        return restAssuredConfiguration;
    }

    public RequestSpecification value() {
        return requestSpecification;
    }

    public String getRequestBody() {
        return requestBody;
    }

    public Map<String, String> getHeader() {
        return header;
    }

    public void setRequestBody(String requestBody) {
        this.requestBody = requestBody;
    }

    public void setRequestHeader(Map<String, String> header) {
        this.header.putAll(header);
    }

    public void updateOrAddRequestHeader(String key, String value) {
        this.header.put(key, value);
    }
}
