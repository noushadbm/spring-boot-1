package com.rayshan.gitinfo.bdd.api.context;

import com.rayshan.gitinfo.bdd.api.httpservice.HttpRequest;
import com.rayshan.gitinfo.bdd.api.httpservice.HttpResponse;
import com.rayshan.gitinfo.bdd.api.httpservice.InitEnvProperties;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TestContext {
    public InitEnvProperties initEnvProperties;
    private HttpRequest httpRequest;
    private HttpResponse httpResponse;
    private ScenarioContext scenarioContext;
    private Map<String, Object> cache;

    public TestContext() {
        this.initEnvProperties = new InitEnvProperties();
        this.httpRequest = new HttpRequest(initEnvProperties);
        this.httpResponse = new HttpResponse(this.httpRequest);
        this.scenarioContext = new ScenarioContext();
        this.cache = new ConcurrentHashMap<>();
    }

    public HttpRequest getHttpRequest() {
        return httpRequest;
    }

    public HttpResponse getHttpResponse() {
        return httpResponse;
    }

    public ScenarioContext getScenarioContext() {
        return scenarioContext;
    }

    public String getBaseURI(String envKey) {
        return initEnvProperties.getEnvProperty(envKey);
    }

    public void cacheAnObject(String key, Object object) {
        cache.put(key, object);
    }

    public Object getCachedObject(String key) {
        return cache.get(key);
    }
}
