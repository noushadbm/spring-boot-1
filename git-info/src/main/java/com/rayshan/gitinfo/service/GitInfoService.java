package com.rayshan.gitinfo.service;

import com.rayshan.gitinfo.clients.github.GithubClient;
import com.rayshan.gitinfo.clients.github.model.RepoDetails;
import com.rayshan.gitinfo.model.ListGitRepoRequest;
import com.rayshan.gitinfo.model.ListGitRepoResponse;
import com.rayshan.gitinfo.util.CommonUtil;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class GitInfoService {
    private final GithubClient githubClient;

    public GitInfoService(GithubClient githubClient) {
        this.githubClient = githubClient;
    }

    public ListGitRepoResponse listGitRepos(String user, ListGitRepoRequest request) {
        List<RepoDetails> repoDetailsList = githubClient.listRepos(user, null, populateHeaders());

        ListGitRepoResponse resp =
                new ListGitRepoResponse(CommonUtil.sortRepoDetailsList(repoDetailsList, request));
        return resp;
    }

    private Map<String, String> populateHeaders() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        return headers;
    }
}
