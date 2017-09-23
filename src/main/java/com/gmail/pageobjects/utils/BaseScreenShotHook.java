package com.gmail.pageobjects.utils;

import java.util.Map;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriverException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gmail.framework.ScreenShots;
import com.gmail.pageobjects.pages.LoginPage;

import cucumber.api.Scenario;

public class BaseScreenShotHook extends BaseStepDef {

	protected static final Logger LOG = LoggerFactory.getLogger(BaseScreenShotHook.class);


	private LoginPage loginPage = new LoginPage();

	public void deleteCookies() {
		LOG.info("Deleting cookies");
		loginPage.deleteAllCookies();
		// resetCurrentUser();
	}

	/**
	 * Performs necessary after steps if test failed
	 *
	 * @param scenario
	 * @throws InterruptedException
	 */
	public void afterTest(Scenario scenario) throws InterruptedException {
		takeScreenshotAndLogSessionIfTestFailed(scenario);
		signOffIfTestFailed(scenario);
	}

	/**
	 * Takes screen-shot if the scenario fails
	 *
	 * @param scenario
	 */
	public void takeScreenshotAndLogSessionIfTestFailed(Scenario scenario) throws InterruptedException {
		LOG.info("Taking screenshot if test failed");
		try {
			Map<String, Object> screenShots = ScreenShots.getScreenShotsForCurrentTest();
			for (Map.Entry<String, Object> screenShot : screenShots.entrySet()) {
				scenario.write(screenShot.getKey());
				scenario.embed((byte[]) screenShot.getValue(), "image/png");
			}
			ScreenShots.tidyUpAfterTestRun();

			if (scenario.isFailed()) {
				LOG.info("Test is failed - taking screenshot");
				scenario.write(loginPage.getCurrentUrl());
				byte[] screenShot = ((TakesScreenshot) loginPage.getDriver).getScreenshotAs(OutputType.BYTES);
				scenario.embed(screenShot, "image/png");

			}
		} catch (WebDriverException e) {
			LOG.error(e.getMessage());
		}

	}

	public void signOffIfTestFailed(Scenario scenario) {
		if (scenario.isFailed()) {
			signOffAfterTest();
		}
	}

	private void signOffAfterTest() {
		LOG.info("Sign-off gmail user");
	}

	public boolean newSession() {
		return System.getProperty("newSession").equalsIgnoreCase("true");
	}
}
