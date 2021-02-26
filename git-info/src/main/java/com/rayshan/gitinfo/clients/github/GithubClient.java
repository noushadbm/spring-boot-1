package com.rayshan.gitinfo.clients.github;

import com.google.common.flogger.FluentLogger;
import com.rayshan.gitinfo.clients.config.HttpConfig;
import com.rayshan.gitinfo.clients.github.model.RepoDetails;
import com.rayshan.gitinfo.model.ListGitRepoRequest;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import retrofit2.Call;
import retrofit2.Response;

public class GithubClient {
    private static final FluentLogger logger = FluentLogger.forEnclosingClass();

    private GithubApi githubApi;
    private String baseUrl;
    private String endpointPath;

    public GithubClient(HttpConfig githubHttpConfig) {
        this.githubApi = (GithubApi) githubHttpConfig.getHttpService(GithubApi.class);
        this.baseUrl = githubHttpConfig.getBaseUrl();
        this.endpointPath = githubHttpConfig.getEndpointPath();
    }

    public List<RepoDetails> listRepos(
            String user, ListGitRepoRequest req, Map<String, String> headers) {
        Call<List<RepoDetails>> gitHubCall;

        // String url = baseUrl + endpointPath;
        // logger.atInfo().log("Initiating call to url: %s", url);
        // gitHubCall = githubApi.listRepos(url, headers);

        logger.atInfo().log("Initiating call to github");
        gitHubCall = githubApi.listReposByName(user, headers);
        Response<List<RepoDetails>> response = null;

        try {
            response = gitHubCall.execute();
            logger.atInfo().log("client response = %s", response);
        } catch (IOException ioe) {
            ioe.printStackTrace();
            System.out.println("=============> " + ioe.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("=============> " + e.getMessage());
        }

        logger.atInfo().log("Response Body:  %s", response.body());
        return response.body();
    }
}
