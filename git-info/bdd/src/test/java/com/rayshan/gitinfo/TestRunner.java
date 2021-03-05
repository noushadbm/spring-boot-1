package com.rayshan.gitinfo;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"src/test/resources/features"},
        glue = {"com.rayshan.gitinfo.stepdefs"},
        tags = {"@gitinfo-service"},
        plugin = {"pretty", "html:target/cucumber-reports", "json:target/cucumber.json"},
        stepNotifications = true)
public class TestRunner {}
