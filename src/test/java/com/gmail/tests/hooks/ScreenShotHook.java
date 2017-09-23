package com.gmail.tests.hooks;

import com.gmail.pageobjects.utils.BaseScreenShotHook;
import cucumber.api.Scenario;
import cucumber.api.java.After;

public class ScreenShotHook extends BaseScreenShotHook {

	@After(order = 200)
	public void takeScreenshotAfterTestIfFailed(Scenario scenario) throws InterruptedException {
		super.takeScreenshotAndLogSessionIfTestFailed(scenario);
	}

	@After(order = 100)
	public void signOffAfterTestIfFailed(Scenario scenario) throws InterruptedException {
		super.signOffIfTestFailed(scenario);
	}
}
