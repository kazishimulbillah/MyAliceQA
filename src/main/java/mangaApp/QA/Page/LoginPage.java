package mangaApp.QA.Page;

import mangaApp.Helper.Action.ActionHelper;
import mangaApp.Helper.Alert.AlertHelper;
import mangaApp.Helper.JavaScript.JavaScriptHelper;
import mangaApp.Helper.Wait.WaitHelper;
import mangaApp.QA.Base.TestBase;
import mangaApp.QA.ConfigFileReader.FileReaderManager;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;


import java.util.List;

public class LoginPage extends TestBase {

    @FindBy(xpath = "//*[@id=\"root\"]/div/div")
    WebElement LoginForm;

    @FindBy(xpath = "//*[@id=\"root\"]/div/div/h2")
    WebElement LoginText;

    @FindBy(xpath = "//*[@id=\"username\"]")
    WebElement UsernameField;
    
    @FindBy(xpath = "//*[@id=\"login-btn\"]")
    WebElement LoginBtn;
    
    @FindBy(xpath = "//*[@id=\"password\"]")
    WebElement PasswordField;

    JavaScriptHelper javaScript = new JavaScriptHelper(driver);
    ActionHelper action = new ActionHelper(driver);
    WaitHelper wait = new WaitHelper(driver);
    AlertHelper alert = new AlertHelper(driver);

    public LoginPage() {
        PageFactory.initElements(driver, this);
    }
    
    public MangaSearchPage LoginSuccessfully() throws Exception {
    	Thread.sleep(500);
    	UsernameField.clear();
    	UsernameField.sendKeys(FileReaderManager.getInstance().getConfigReader().getUserName());
		
    	Thread.sleep(500);
    	PasswordField.clear();
    	PasswordField.sendKeys(FileReaderManager.getInstance().getConfigReader().getPasswordInitiator());
    	
    	Thread.sleep(500);
    	LoginBtn.click();
    	
		return new MangaSearchPage();
    }
    
    public void VerifyLoginPage() throws Exception {
    	Thread.sleep(500);
    	assert(LoginForm.isDisplayed());
    	
    	Thread.sleep(500);
    	Assert.assertEquals(LoginText.getText(), "Login");
    	
    }
    
    public void WrongCredential() throws Exception {
    	Thread.sleep(500);
    	UsernameField.clear();
    	UsernameField.sendKeys("test");
		
    	Thread.sleep(500);
    	PasswordField.clear();
    	PasswordField.sendKeys("123456");
    	
    	Thread.sleep(500);
    	LoginBtn.click();
    	
    	Thread.sleep(500);
    	Assert.assertEquals(alert.getAlertText(), "Invalid credentials");
    	
    	alert.acceptAlert();
    }

}
