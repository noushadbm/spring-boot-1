package com.rayshan.gitinfo.bdd.api.httpservice;

import com.github.dzieciou.testing.curl.CurlLoggingRestAssuredConfigFactory;
import com.rayshan.gitinfo.bdd.api.httpcore.HttpMethod;
import io.restassured.config.LogConfig;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.PrintStream;
import java.util.Map;
import java.util.concurrent.Callable;

public class HttpResponse {
    private static final Logger log = LoggerFactory.getLogger(HttpResponse.class);

    private final HttpRequest httpRequest;
    private Response response;
    private String responsePrefix;
    private boolean expectException;
    private RuntimeException exception;

    public HttpResponse(HttpRequest httpRequest) {
        this.httpRequest = httpRequest;
    }

    public void setResponsePrefix(String responsePrefix) {
        this.responsePrefix = responsePrefix;
    }

    public Response doRequest(
            String httpMethod,
            final String url,
            RestAssuredConfiguration restAssuredConfiguration,
            PrintStream fileLog)
            throws Exception {
        final HttpMethod method = HttpMethod.parseHttpMethod(httpMethod);
        try {
            response = doRequest(url, method, restAssuredConfiguration, fileLog).call();
        } catch (RuntimeException e) {
            log.error(
                    "Caught runtime exception while invoking "
                            + httpMethod
                            + " call on "
                            + url
                            + ", "
                            + e.getMessage());
            if (!expectException) {
                throw e;
            }
            exception = e;
        }
        httpRequest.initNewMinimumRequestSpec(null);
        httpRequest.setRequestBody(null);
        return response;
    }

    private Callable<Response> doRequest(
            final String url,
            final HttpMethod method,
            RestAssuredConfiguration restAssuredConfiguration,
            PrintStream fileLog) {
        return () -> {
            RequestSpecification requestSpec = httpRequest.value();
            updateRequestSpecWithRequestBodyAndHeader(requestSpec);
            requestSpec
                    .given()
                    .config(
                            CurlLoggingRestAssuredConfigFactory.updateConfig(
                                    restAssuredConfiguration
                                            .getRestAssuredConfig()
                                            .logConfig(
                                                    LogConfig.logConfig()
                                                            .defaultStream(fileLog)
                                                            .enablePrettyPrinting(true))))
                    .log()
                    .all();
            Response response = method.doRequest(requestSpec, url);
            response.then().log().all(true);
            return response;
        };
    }

    public void updateRequestSpecWithRequestBodyAndHeader(
            RequestSpecification requestSpecification) {
        String requestBody = httpRequest.getRequestBody();
        Map<String, String> header = httpRequest.getHeader();
        if (requestBody != null) {
            requestSpecification.body(requestBody);
        }
        header.forEach((k, v) -> requestSpecification.header(k, v));
    }

    public void setReponse(Response response) {
        this.response = response;
    }
}
