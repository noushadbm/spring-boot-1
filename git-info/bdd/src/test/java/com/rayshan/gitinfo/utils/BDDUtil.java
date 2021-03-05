package com.rayshan.gitinfo.utils;

import com.rayshan.gitinfo.bdd.api.context.TestContext;
import com.rayshan.gitinfo.bdd.api.steps.CommonSteps;
import com.rayshan.gitinfo.bdd.constants.HTTPConstants;

import java.io.IOException;
import java.util.UUID;

public class BDDUtil {

    private final TestContext testContext;
    private final CommonSteps commonSteps;

    public BDDUtil(TestContext testContext,
                   CommonSteps commonSteps){
        this.testContext = testContext;
        this.commonSteps = commonSteps;
    }

    public void createRequestPayloadFromFile(
            String apiName,
            String fileName,
            String sortBy,
            String order
    ) throws IOException {
        String correlationId = UUID.randomUUID().toString();
        commonSteps.initNewMinimumRequestSpec(HTTPConstants.ILB_BASE_URI);
        commonSteps.iHaveAPI(apiName);
        commonSteps.iSetRequestHeaderAs(apiName);
        commonSteps.iUpdateHeaderWithValue(HTTPConstants.CORRELATION_ID_HEADER, correlationId);
        commonSteps.iSetRequestBodyAs(apiName, fileName);

    }

    public Object getLogStream() {
        return testContext.getCachedObject(getCurrentThreadName() + "-log");
    }

    public static String getCurrentThreadName() {
        return Thread.currentThread().getName();
    }
}
