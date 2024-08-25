package mangaApp.Helper.JavaScript;

import org.apache.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import mangaApp.Helper.Logger.LoggerHelper;
import mangaApp.QA.Base.TestBase;

public class JavaScriptHelper extends TestBase {
	// private WebDriver driver;
	private Logger log = LoggerHelper.getLogger(JavaScriptHelper.class);

	public JavaScriptHelper(WebDriver driver) {
		JavaScriptHelper.driver = driver;
		log.info("JavaScriptHelper class has been initialised");
	}

	public Object executeScript(String script) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		return js.executeScript(script);
	}

	public Object executeScript(String script, Object... args) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		return js.executeScript(script, args);
	}

	public void scrollToElement(WebElement element) {
		log.info("Scroll to WebElement ...");
		executeScript("window.scrollTo(arguments[0],arguments[1])", element.getLocation().x, element.getLocation().y);
	}

	public void scrollToElementAndClick(WebElement element) {
		scrollToElement(element);
		clickElement(element);
		log.info("element is clicked...." + element.toString());
	}
	
	public void SendElement(String Element, String Stringvalue) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("document.getElementById(Element).setAttribute('value', Stringvalue)");
	}

	/**
	 * This method will click on element
	 * 
	 * @param pixel
	 */
	public void clickElement(WebElement element) {
		log.info("click on element..." + element.toString());
		executeScript("arguments[0].click();", element);
	}

}
