package com.rayshan.gitinfo.model;

import java.util.List;
import lombok.Data;

@Data
public class ListGitRepoResponse {
    private List<String> repos;
}
