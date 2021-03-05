package com.rayshan.gitinfo.bdd.api.httpcore;

import com.rayshan.gitinfo.bdd.utils.TimeUtil;
import io.restassured.response.Response;
import org.hamcrest.Matcher;

public class HttpWaitCondition {

    private final TimeUtil waitTime;
    private final TimeUtil interval;
    private final Matcher<Response> successMatcher;
    private final Matcher<Response> failureMatcher;

    public HttpWaitCondition(
            TimeUtil waitTime, TimeUtil interval, Matcher<Response> successMatcher) {
        this.waitTime = waitTime;
        this.interval = interval;
        this.successMatcher = successMatcher;
        failureMatcher = null;
    }

    public HttpWaitCondition(
            TimeUtil waitTime,
            TimeUtil interval,
            Matcher<Response> successMatcher,
            Matcher<Response> failureMatcher) {
        this.waitTime = waitTime;
        this.interval = interval;
        this.successMatcher = successMatcher;
        this.failureMatcher = failureMatcher;
    }

    public TimeUtil getWaitTime() {
        return waitTime;
    }

    public TimeUtil getInterval() {
        return interval;
    }

    public Matcher<Response> getSuccessMatcher() {
        return successMatcher;
    }

    public Matcher<Response> getFailureMatcher() {
        return failureMatcher;
    }
}
