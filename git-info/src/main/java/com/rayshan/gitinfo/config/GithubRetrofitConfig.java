package com.rayshan.gitinfo.config;

import com.rayshan.gitinfo.clients.config.HttpConfig;
import com.rayshan.gitinfo.clients.github.GithubClient;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class GithubRetrofitConfig {

    @Value("${github.base.url}")
    private String githubBaseUrl;

    @Value("${github.endpoint.path}")
    private String githubEndpointPath;

    @Value("${github.service.call.timeout}")
    private Long githubCallTimeout;

    public HttpConfig getGithubHttpConfig() {
        HttpConfig httpConfig = new HttpConfig();
        httpConfig.setBaseUrl(githubBaseUrl);
        httpConfig.setEndpointPath(githubEndpointPath);
        httpConfig.setConnectTimeout(300L);
        httpConfig.setWriteTimeout(300L);
        httpConfig.setReadTimeout(300L);
        httpConfig.setCallTimeout(githubCallTimeout);
        // httpConfig.setRetryCount(githubRetryCount);
        httpConfig.setPoolSize(20);

        return httpConfig;
    }

    @Bean
    public GithubClient githubGateway() {
        return new GithubClient(getGithubHttpConfig());
    }
}
