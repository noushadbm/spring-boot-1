package com.rayshan.gitinfo.bdd.api.httpcore;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public enum HttpMethod {
    GET,
    POST,
    PUT,
    DELETE,
    OPTIONS,
    HEAD,
    PATCH;

    public static HttpMethod parseHttpMethod(String requestMethod) {
        for (HttpMethod httpMethod : values()) {
            if (httpMethod.toString().equalsIgnoreCase(requestMethod)) return httpMethod;
        }
        throw new IllegalArgumentException();
    }

    public Response doRequest(RequestSpecification requestSpecification, String url) {
        switch (this) {
            case GET:
                return requestSpecification.get(url);
            case POST:
                return requestSpecification.post(url);
            case PUT:
                return requestSpecification.put(url);
            case DELETE:
                return requestSpecification.delete(url);
            case OPTIONS:
                return requestSpecification.options(url);
            case HEAD:
                return requestSpecification.head(url);
            case PATCH:
                return requestSpecification.patch(url);
        }
        throw new IllegalArgumentException("The given http method: " + url + "is not valid.");
    }
}
