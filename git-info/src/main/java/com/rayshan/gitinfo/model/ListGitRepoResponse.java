package com.rayshan.gitinfo.model;

import com.rayshan.gitinfo.clients.github.model.RepoDetails;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ListGitRepoResponse {
    private List<RepoDetails> repos;
}
