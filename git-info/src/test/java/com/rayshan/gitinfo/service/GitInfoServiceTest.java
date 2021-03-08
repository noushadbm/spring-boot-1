package com.rayshan.gitinfo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.rayshan.gitinfo.clients.github.GithubClient;
import com.rayshan.gitinfo.clients.github.model.RepoDetails;
import com.rayshan.gitinfo.exception.GitInfoException;
import com.rayshan.gitinfo.model.ListGitRepoRequest;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class GitInfoServiceTest {
    @Mock GithubClient githubClient;
    @InjectMocks GitInfoService service;

    @Test
    public void success() throws GitInfoException {
        Mockito.when(
                        githubClient.listRepos(
                                Mockito.anyString(),
                                Mockito.any(ListGitRepoRequest.class),
                                Mockito.anyMap()))
                .thenReturn(mockResult());

        assertNotNull(service.listGitRepos("test", new ListGitRepoRequest()));
        assertEquals(1, service.listGitRepos("test", new ListGitRepoRequest()).getRepos().size());
    }

    private List<RepoDetails> mockResult() {
        List<RepoDetails> list = new ArrayList<>();
        RepoDetails details1 = new RepoDetails();
        details1.setName("name-1");
        details1.setId("id-1");
        list.add(details1);

        return list;
    }
}
