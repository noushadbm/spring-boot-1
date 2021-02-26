package com.rayshan.gitinfo.clients.github;

import com.google.common.flogger.FluentLogger;
import com.rayshan.gitinfo.clients.config.HttpConfig;
import com.rayshan.gitinfo.clients.github.model.RepoDetails;
import com.rayshan.gitinfo.exception.ErrorData;
import com.rayshan.gitinfo.exception.GitInfoException;
import com.rayshan.gitinfo.exception.ReasonCode;
import com.rayshan.gitinfo.model.ListGitRepoRequest;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
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
            String user, ListGitRepoRequest req, Map<String, String> headers)
            throws GitInfoException {
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
            logger.atSevere().log("Github API call failed with error : %s", ioe.getMessage());
        }

        handleNonSuccessScenario(response);

        logger.atInfo().log("Response Body:  %s", response.body());
        return response.body();
    }

    private void handleNonSuccessScenario(Response<List<RepoDetails>> response)
            throws GitInfoException {
        ErrorData errorData = new ErrorData();

        if (null == response) {
            errorData.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            errorData.setMessage("Null response from github API");
            errorData.setCode("GIT_HUB_API_ERR_500_01");
            errorData.setReasonCode(ReasonCode.SERVICE_UNAVAILABLE);
            throw new GitInfoException(errorData);
        }

        if (!response.isSuccessful()) {
            if (response.code() == 404) {
                errorData.setMessage("Username not found");
            } else {
                errorData.setMessage("Request failed.");
            }
            errorData.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            errorData.setCode("GIT_HUB_API_ERR_500_02");
            errorData.setReasonCode(ReasonCode.SERVICE_UNAVAILABLE);
            throw new GitInfoException(errorData);
        }
    }
}
