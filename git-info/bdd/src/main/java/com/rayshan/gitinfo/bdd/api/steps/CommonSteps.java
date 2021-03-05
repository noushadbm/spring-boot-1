package com.rayshan.gitinfo.bdd.api.steps;

import com.rayshan.gitinfo.bdd.api.context.Context;
import com.rayshan.gitinfo.bdd.api.context.TestContext;
import com.rayshan.gitinfo.bdd.api.httpservice.HttpRequest;
import com.rayshan.gitinfo.bdd.api.httpservice.HttpResponse;
import com.rayshan.gitinfo.bdd.api.httpservice.RestAssuredConfiguration;
import com.rayshan.gitinfo.utils.HttpUtil;

import java.io.IOException;
import java.io.PrintStream;
import java.util.Map;
import java.util.UUID;

public class CommonSteps {
    HttpRequest httpRequest;
    HttpResponse httpResponse;
    TestContext testContext;

    public CommonSteps(TestContext testContext) {
        this.testContext = testContext;
        httpRequest = this.testContext.getHttpRequest();
        httpResponse = this.testContext.getHttpResponse();
    }

    public void initNewMinimumRequestSpec(String baseURIKey) {
        httpRequest.initNewMinimumRequestSpec(testContext.getBaseURI(baseURIKey));
    }

    public void iHaveAPI(String apiName) throws IOException {
        testContext.getScenarioContext().setContext(Context.API_NAME, apiName);
        String basePath =
                HttpUtil.getBasePath(
                        (String) testContext.getScenarioContext().getContext(Context.API_NAME));
        testContext.getScenarioContext().setContext(Context.BASE_PATH, basePath);
        testContext.getScenarioContext().setContext(Context.UUID, UUID.randomUUID().toString());
    }

    public void iCallMethod(String httpMethod, Object stream) throws Exception {
        RestAssuredConfiguration restAssuredConfiguration =
                httpRequest.getRestAssuredConfiguration();
        PrintStream fileLog = (PrintStream) stream;
        httpResponse.setResponsePrefix("");
        String basePath = (String) testContext.getScenarioContext().getContext(Context.BASE_PATH);
        httpResponse.setReponse(
                httpResponse.doRequest(httpMethod, basePath, restAssuredConfiguration, fileLog));
    }

    public void iSetRequestHeaderAs(String apiName) throws IOException {
        Map<String, String> headers = HttpUtil.getHeader(apiName);
        httpRequest.setRequestHeader(headers);
    }

    public void iUpdateHeaderWithValue(String name, String value) {
        httpRequest.updateOrAddRequestHeader(name, value);
    }

    public void iSetRequestBodyAs(String apiName, String requestName) throws IOException {
        if (HttpUtil.parseJsonData(apiName, requestName) != null) {
            httpRequest.setRequestBody(HttpUtil.parseJsonData(apiName, requestName));
        }
    }
}
