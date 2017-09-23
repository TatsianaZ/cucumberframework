package com.gmail.tests;

import org.junit.BeforeClass;
import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/org/bsuir/tests/features", plugin = { "pretty",
		"html:target/cucumber-html-report", "junit:target/junit_cucumber.xml", "json:target/cucumber.json" })
public class RunCukesTest {
	@BeforeClass
	public static void reporting() {
	}
}

