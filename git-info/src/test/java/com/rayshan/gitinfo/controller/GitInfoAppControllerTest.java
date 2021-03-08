package com.rayshan.gitinfo.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.rayshan.gitinfo.clients.github.model.RepoDetails;
import com.rayshan.gitinfo.constants.CommonConstants;
import com.rayshan.gitinfo.exception.GitInfoException;
import com.rayshan.gitinfo.model.ListGitRepoRequest;
import com.rayshan.gitinfo.model.ListGitRepoResponse;
import com.rayshan.gitinfo.service.GitInfoService;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = {GitController.class})
public class GitInfoAppControllerTest {
    @Autowired MockMvc mockMvc;
    @MockBean private GitInfoService service;

    @BeforeEach
    public void setup() throws GitInfoException {
        Mockito.when(
                        service.listGitRepos(
                                Mockito.anyString(), Mockito.any(ListGitRepoRequest.class)))
                .thenReturn(getMockResults());
    }

    @Test
    public void getList() throws Exception {
        RequestBuilder request =
                MockMvcRequestBuilders.post("/v1/git-info/user/test-user/list")
                        .header(CommonConstants.CORRELATION_ID, "12345")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}");

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.repos[0].id").value("id-1"))
                .andExpect(jsonPath("$.repos[0].name").value("name-1"))
                .andReturn();
        ;
    }

    private ListGitRepoResponse getMockResults() {
        List<RepoDetails> list = new ArrayList<>();
        RepoDetails details1 = new RepoDetails();
        details1.setName("name-1");
        details1.setId("id-1");
        list.add(details1);

        ListGitRepoResponse resp = new ListGitRepoResponse(list);
        return resp;
    }
}
