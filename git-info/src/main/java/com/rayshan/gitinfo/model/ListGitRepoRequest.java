package com.rayshan.gitinfo.model;

import lombok.Data;

@Data
public class ListGitRepoRequest {
    private String sortBy;
    private String order;
    private String limit;
}
