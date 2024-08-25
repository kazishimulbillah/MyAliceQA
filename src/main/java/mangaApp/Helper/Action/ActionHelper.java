package mangaApp.Helper.Action;

import org.apache.log4j.Logger;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import mangaApp.Helper.Action.ActionHelper;

import mangaApp.Helper.Logger.LoggerHelper;
import mangaApp.Helper.Wait.WaitHelper;
import mangaApp.QA.Base.TestBase;

public class ActionHelper extends TestBase {

	public WebDriver driver;
	private Logger log = LoggerHelper.getLogger(ActionHelper.class);

	WaitHelper waithelper;

	public ActionHelper(WebDriver driver) {
		this.driver = driver;
		log.info("ActionHelper object created");
	}

	public void clickOnElement(WebElement element) {
		Actions action = new Actions(driver);
		action.moveToElement(element).build().perform();
		element.click();
		log.info("element is clicked...." + element.toString());
	}

	public void sendKeysToElement(Keys key) {
		Actions action = new Actions(driver);
		action.sendKeys(key).build().perform();
		log.info("key is sent...." + key);
	}
}
