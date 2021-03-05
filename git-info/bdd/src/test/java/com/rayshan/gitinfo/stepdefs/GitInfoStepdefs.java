package com.rayshan.gitinfo.stepdefs;

import com.rayshan.gitinfo.bdd.api.context.TestContext;
import com.rayshan.gitinfo.bdd.api.steps.CommonSteps;
import com.rayshan.gitinfo.bdd.constants.HTTPConstants;
import com.rayshan.gitinfo.bdd.constants.RequestConstants;
import com.rayshan.gitinfo.utils.BDDUtil;
import io.cucumber.core.api.Scenario;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;

import java.io.*;

public class GitInfoStepdefs {
    private static final String logFolder = "logs/rest-assured";

    private final TestContext testContext;
    private final CommonSteps commonSteps;
    private String username;

    BDDUtil bddUtil;

    public GitInfoStepdefs(TestContext testContext) {
        this.testContext = testContext;
        this.commonSteps = new CommonSteps(this.testContext);
        this.bddUtil = new BDDUtil(this.testContext,
                this.commonSteps);

    }

    @Before
    public void beforeScenario(Scenario scenario) {
        System.out.println("Before scenario hook: " + scenario.getId());
        ThreadLocal<PrintStream> localStream = new ThreadLocal<PrintStream>();
        File fileWriter;
        String scenarioID = scenario.getId();
        String[] parts = scenarioID.split("[.;:/]");
        String dir =
                logFolder
                        + "/"
                        + parts[1]
                        + "/"
                        + parts[2]
                        + "/"
                        + parts[3]
                        + "/"
                        + parts[4]
                        + "/"
                        + BDDUtil.getCurrentThreadName();
        File featureDirectory = new File(dir);
        if (!featureDirectory.exists()) featureDirectory.mkdirs();
        fileWriter = new File(dir + "/" + parts[5] + ".log");
        if (!fileWriter.exists()) {
            try {
                fileWriter.createNewFile();
            } catch (IOException e) {
                System.out.println("file not created:" + fileWriter.getPath());
            }
        }

        try {
            localStream.set(new PrintStream(new FileOutputStream(fileWriter), true));
            testContext.cacheAnObject(BDDUtil.getCurrentThreadName() + "-log", localStream.get());
        } catch (FileNotFoundException e) {
            System.out.println("file not found");
        }
    }

    @Given("that a user with {string} try to access gitinfo service")
    public void thatAuserWithUsernameTryToAccessGitinfoService(String username) {
        this.username = username;
    }

    @When("gitinfo service is invoked with sortBy as {string} and order as {string}")
    public void gitinfoServiceIsInvokedWithSortByAsAndOrderAs(String sortBy, String order) throws Exception {
        bddUtil.createRequestPayloadFromFile(
                HTTPConstants.GIT_INFO_API_NAME,
                "GitInfoRequest",
                sortBy,
                order);
        //bddUtil.updateDetailsInReqBody(eventType, this.customerId, this.strongId);
        commonSteps.iCallMethod(RequestConstants.HTTP_POST, bddUtil.getLogStream());
    }
}
