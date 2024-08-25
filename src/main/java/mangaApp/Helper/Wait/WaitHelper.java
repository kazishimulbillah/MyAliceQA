package mangaApp.Helper.Wait;

import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import mangaApp.Helper.Logger.LoggerHelper;
import mangaApp.QA.Base.TestBase;

public class WaitHelper extends TestBase {

	// private WebDriver driver;
	private Logger log = LoggerHelper.getLogger(WaitHelper.class);

	public WaitHelper(WebDriver driver) {
		TestBase.driver = driver;
		log.info("waithelper object created..");
	}

	/**
	 * ImplicitWait method
	 * 
	 * @param timeout
	 * @param unit
	 */
	public void setImplicitWait(long timeout, TimeUnit unit) {
		log.info("Implicit Wait has been set to: " + timeout);
		driver.manage().timeouts().implicitlyWait(timeout, unit);
	}

	public void pageLoadTimeOut(long timeout, TimeUnit unit) {
		log.info("waiting for page load timeout for : " + unit + " seconds");
		driver.manage().timeouts().pageLoadTimeout(timeout, unit);
		log.info("page is loaded");
	}
}
