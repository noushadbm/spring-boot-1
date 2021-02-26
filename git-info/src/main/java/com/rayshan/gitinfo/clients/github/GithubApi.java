package com.rayshan.gitinfo.clients.github;

import com.rayshan.gitinfo.clients.github.model.RepoDetails;
import java.util.List;
import java.util.Map;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Path;
import retrofit2.http.Url;

public interface GithubApi {
    @GET
    Call<List<RepoDetails>> listRepos(@Url String url, @HeaderMap Map<String, String> headers);

    @GET("/users/{username}/repos")
    Call<List<RepoDetails>> listReposByName(
            @Path("username") String userName, @HeaderMap Map<String, String> headers);
}
