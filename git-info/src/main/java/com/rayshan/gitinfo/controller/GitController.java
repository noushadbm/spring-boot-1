package com.rayshan.gitinfo.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.rayshan.gitinfo.model.ListGitRepoRequest;
import com.rayshan.gitinfo.model.ListGitRepoResponse;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/v1/git-info")
public class GitController {
    @PostMapping(
            value = "/user/{user}/list",
            consumes = APPLICATION_JSON_VALUE,
            produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<ListGitRepoResponse> listGitRepos(
            @PathVariable String user, @RequestBody ListGitRepoRequest request) {
        System.out.println("got request for " + user);
        ListGitRepoResponse resp = new ListGitRepoResponse();
        List<String> repos = new ArrayList<>();
        repos.add("ok-" + user);
        resp.setRepos(repos);
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }
}
