package com.rayshan.gitinfo;

import static org.assertj.core.api.Assertions.assertThat;

import com.rayshan.gitinfo.controller.GitController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class GitInfoApplicationTests {
    @Autowired private GitController controller;

    @Test
    void contextLoads() {
        assertThat(controller).isNotNull();
    }
}
