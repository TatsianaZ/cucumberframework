package com.gmail.framework;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.math.NumberUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Function;

public abstract class AbstractPage {

	private static final int DRIVER_WAIT_TIME = org.apache.commons.lang3.StringUtils
			.isNumeric(System.getProperty("DRIVER_WAIT_TIME_SEC"))
					? NumberUtils.toInt(System.getProperty("DRIVER_WAIT_TIME_SEC")) : 60;
	private static final int DEFAULT_IMPLICIT_WAIT_TIMEOUT = org.apache.commons.lang3.StringUtils
			.isNumeric(System.getProperty("IMPLICITLY_WAIT_SEC"))
					? NumberUtils.toInt(System.getProperty("IMPLICITLY_WAIT_SEC")) : 20;
	private static final int MINIMUM_IMPLICITY_WAIT_TIMEOUT = 3;
	protected static final Logger LOG = LoggerFactory.getLogger(AbstractPage.class);

	public static RemoteWebDriver getDriver;

	public AbstractPage() {
		WebDriverDiscovery webDriverDiscovery = new WebDriverDiscovery();
		getDriver = webDriverDiscovery.getRemoteWebDriver();
	}

	public void navigate(String url) {
		getDriver.get(url);
	}

	public String getCurrentUrl() {
		return getDriver.getCurrentUrl();
	}

	public void maximizeWindow() {
		getDriver.manage().window().maximize();
	}

	public void refreshPage() {
		String currentURL = getDriver.getCurrentUrl();
		getDriver.navigate().to(currentURL);
		getDriver.navigate().refresh();
	}

	public void deleteAllCookies() {
		getDriver.manage().deleteAllCookies();
	}

	public WebElement findElement(By by) {
		return getDriver.findElement(by);
	}

	public List<WebElement> findElements(By by) {
		return getDriver.findElements(by);
	}

	public WebElement findChildElement(WebElement element, final By by) {
		Wait<WebElement> wait = new FluentWait<WebElement>(element).withTimeout(DRIVER_WAIT_TIME, TimeUnit.SECONDS)
				.ignoring(ElementNotVisibleException.class).ignoring(NoSuchElementException.class);

		return wait.until(new Function<WebElement, WebElement>() {
			@Override
			public WebElement apply(WebElement webElement) {
				return webElement.findElement(by);
			}
		});
	}

	public List<WebElement> waitForElementsVisible(final By by) {
		Wait<WebDriver> wait = new WebDriverWait(getDriver, DRIVER_WAIT_TIME);
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(by));
		return getDriver.findElements(by);
	}

	public List<WebElement> waitForElementsClickable(final By by) {
		Wait<WebDriver> wait = new WebDriverWait(getDriver, DRIVER_WAIT_TIME);
		wait.until(ExpectedConditions.elementToBeClickable(by));
		return getDriver.findElements(by);
	}

	public void waitForElementInvisible(final By by) {
		Wait<WebDriver> wait = new WebDriverWait(getDriver, DRIVER_WAIT_TIME);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
	}

	public List<WebElement> waitForExpectedElements(final By by) {
		Wait<WebDriver> wait = new WebDriverWait(getDriver, DRIVER_WAIT_TIME);
		wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(by));
		return getDriver.findElements(by);
	}

	public List<WebElement> waitForExpectedElements(final By by, int timeout) {
		Wait<WebDriver> wait = new WebDriverWait(getDriver, timeout);
		wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(by));
		return getDriver.findElements(by);
	}

	public List<WebElement> waitForElementsVisible(final By by, int timeout) {
		Wait<WebDriver> wait = new WebDriverWait(getDriver, timeout);
		return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(by));
	}

	public WebElement waitForElementPresent(final By by) {
		Wait<WebDriver> wait = new WebDriverWait(getDriver, DRIVER_WAIT_TIME);
		return wait.until(ExpectedConditions.presenceOfElementLocated(by));
	}

	public WebElement waitForElementVisible(final By by) {
		Wait<WebDriver> wait = new WebDriverWait(getDriver, DRIVER_WAIT_TIME);
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		return getDriver.findElement(by);
	}

	public WebElement waitForElementVisible(final WebElement element, int timeOut) {
		Wait<WebDriver> wait = new WebDriverWait(getDriver, timeOut);
		wait.until(ExpectedConditions.visibilityOf(element));
		return element;
	}

	public void waitForTextPresent(final By by, String txt, int timeOut) {
		LOG.info("waiting for the text " + txt + " to be present... ");
		Wait<WebDriver> wait = new WebDriverWait(getDriver, timeOut);
		wait.until(ExpectedConditions.textToBePresentInElementLocated(by, txt));
	}

	public WebElement waitForElementToBeClickableAndReturnElement(final By by) {
		Wait<WebDriver> wait = new WebDriverWait(getDriver, DRIVER_WAIT_TIME, 100);
		wait.until(ExpectedConditions.elementToBeClickable(by));
		return getDriver.findElement(by);
	}

	public List<WebElement> waitForElementsToBeClickableAndReturnElement(final By by) {
		Wait<WebDriver> wait = new WebDriverWait(getDriver, DRIVER_WAIT_TIME, 100);
		wait.until(ExpectedConditions.elementToBeClickable(by));
		return getDriver.findElements(by);
	}

	public WebElement waitForElementToBeClickable(final WebElement element) {
		Wait<WebDriver> wait = new WebDriverWait(getDriver, DRIVER_WAIT_TIME, 100);
		wait.until(ExpectedConditions.elementToBeClickable(element));
		return element;
	}

	public WebElement waitForElementToBeClickable(final WebElement element, int timeout) {
		Wait<WebDriver> wait = new WebDriverWait(getDriver, timeout, 100);
		wait.until(ExpectedConditions.elementToBeSelected(element));
		return element;
	}

	public void waitForElementToDissappear(final By byLocator) {
		Wait<WebDriver> wait = new WebDriverWait(getDriver, DRIVER_WAIT_TIME, 100);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(byLocator));
	}

	public void waitForPageTitle(String title) {
		pageTitleWait(title, DRIVER_WAIT_TIME);
	}

	public void pageTitleWait(String title, int timeOut) {
		Wait<WebDriver> wait = new WebDriverWait(getDriver, timeOut);
		wait.until(ExpectedConditions.titleIs(title));
	}

	public boolean isExists(By by) {
		removeTimeOuts();
		try {
			return getDriver.findElements(by) != null && !getDriver.findElements(by).isEmpty();
		} finally {
			setTimeOutsToDefault();
		}
	}

	private void removeTimeOuts() {
		getDriver.manage().timeouts().implicitlyWait(MINIMUM_IMPLICITY_WAIT_TIMEOUT, TimeUnit.SECONDS);
	}

	private void setTimeOutsToDefault() {
		getDriver.manage().timeouts().implicitlyWait(DEFAULT_IMPLICIT_WAIT_TIMEOUT, TimeUnit.SECONDS);
	}

	public boolean isElementDisplayed(By by) {
		return isExists(by) ? findElement(by).isDisplayed() : false;
	}

	public static <F, T> T waitFor(Function<F, T> condition, F waitWith, long timeOutInSeconds) {
		Wait<F> wait = new FluentWait<>(waitWith).withTimeout(timeOutInSeconds, TimeUnit.SECONDS).pollingEvery(300,
				TimeUnit.MILLISECONDS);
		try {
			return wait.until(condition);
		} catch (Exception | AssertionError e) {
		}
		return null;
	}

	public static <F, T> T waitFor(Function<F, T> condition, F waitWith) {
		return waitFor(condition, waitWith, DRIVER_WAIT_TIME);
	}

	public Boolean waitForViewScrollingToElement(final By by) {
		ExpectedCondition<Boolean> scrollingCondition = new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver driver) {
				return isViewScrolledToElement(driver.findElement(by));
			}
		};
		return waitFor(scrollingCondition, getDriver) != null;
	}

	public boolean isViewScrolledToElement(WebElement element) {
		String jqueryVisiblePlugin = "!function(a){var b=a(window);a.fn.visible=function(a,c,d){if(!(this.length<1))"
				+ "{var e=this.length>1?this.eq(0):this,f=e.get(0),g=b.width(),h=b.height(),d=d?d:\"both\",i=c===!0?"
				+ "f.offsetWidth*f.offsetHeight:!0;if(\"function\"==typeof f.getBoundingClientRect){"
				+ "var j=f.getBoundingClientRect(),k=j.top>=0&&j.top<h,l=j.bottom>0&&j.bottom<=h,m=j.left>=0&&j.left<g,"
				+ "n=j.right>0&&j.right<=g,o=a?k||l:k&&l,p=a?m||n:m&&n;if(\"both\"===d)return i&&o&&p;if(\"vertical\"===d)"
				+ "return i&&o;if(\"horizontal\"===d)return i&&p}else{var q=b.scrollTop(),r=q+h,s=b.scrollLeft(),"
				+ "t=s+g,u=e.offset(),v=u.top,w=v+e.height(),x=u.left,y=x+e.width(),z=a===!0?w:v,A=a===!0?v:w,B=a===!0?y:x,"
				+ "C=a===!0?x:y;if(\"both\"===d)return!!i&&r>=A&&z>=q&&t>=C&&B>=s;if(\"vertical\"===d)return!!i&&r>=A&&z>=q;"
				+ "if(\"horizontal\"===d)return!!i&&t>=C&&B>=s}}}}(jQuery);";
		String pluginAvailabilityCheck = "try { $('body').visible('both'); return true; } catch (err) { return false; }";
		JavascriptExecutor jsEx = ((JavascriptExecutor) getDriver);
		if (!((Boolean) jsEx.executeScript(pluginAvailabilityCheck))) {
			jsEx.executeScript(jqueryVisiblePlugin);
		}
		return (Boolean) jsEx.executeScript("return $(arguments[0]).visible('both');", element);
	}

	public void scrollIntoViewAndClick(WebElement element) {
		try {
			((JavascriptExecutor) getDriver).executeScript("arguments[0].scrollIntoView(false)", element);
		} catch (Exception e) {
			LOG.error(String.format("Error scroll into view for element. Error message: %s", e.getMessage()));
		} finally {
			element.click();
		}
	}

	public WebElement getElementByLinkText(String linkText) {
		return getDriver.findElement(By.linkText(linkText));
	}

	public void waitForPageToLoad() {
		try {
			ExpectedCondition<Boolean> expectedCondition = new ExpectedCondition<Boolean>() {
				@Override
				public Boolean apply(WebDriver driver) {
					return getDocumentReadyState().equals("complete");
				}
			};
			Wait<WebDriver> wait = new WebDriverWait(getDriver, DRIVER_WAIT_TIME);
			wait.until(expectedCondition);
		} catch (Exception ex) {
			LOG.error("Fail waiting for ready document state. Current state is " + getDocumentReadyState());
		}
	}

	private String getDocumentReadyState() {
		return (String) ((JavascriptExecutor) getDriver).executeScript("return document.readyState");
	}

	public void waitForPageToLoadAndJQueryProcessing() {
		waitForPageToLoadAndJQueryProcessing(DRIVER_WAIT_TIME);
	}

	private void waitForPageToLoadAndJQueryProcessing(int waitTimeOutInSeconds) {
		new WebDriverWait(getDriver, waitTimeOutInSeconds) {
		}.until(new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver driverObject) {
				return (Boolean) ((JavascriptExecutor) driverObject)
						.executeScript("return typeof jQuery != 'undefined' && jQuery.active == 0");
			}
		});
	}

}
