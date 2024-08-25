package manga.MangaAPP.TestCases;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.support.PageFactory;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import mangaApp.QA.Base.TestBase;
import mangaApp.QA.Page.LoginPage;
import mangaApp.QA.Page.MangaSearchPage;

/**  
 *
 * @author Kazi Md Shimul Billah
 */

public class EndToEndTests extends TestBase {

//	String sheetName = "NewUserRegistration";
	public ExtentReports extent;
	public ExtentTest extentTest;
	
	LoginPage loginPage;
	MangaSearchPage mangaSearchPage;
	
	public List<String> searchItems = Arrays.asList("Naruto", "One Piece", "Seven Deadly Sins", "No manga found"); 

	public EndToEndTests() {
		super();
	}

	@BeforeTest
	public void setExtent() {
		extent = new ExtentReports(System.getProperty("user.dir") + "/test-output/ExtentReport.html", true);
	}

	@AfterTest
	public void endReport() {
		extent.endTest(extentTest);
		extent.flush();
	}
	
	@BeforeMethod(alwaysRun = true)
	public void before() {
		System.out.print("testing start");
	}

	@BeforeSuite
	public void setUp() throws Exception {
		initialization();
		loginPage = new LoginPage();
		mangaSearchPage = new MangaSearchPage();
	}

	@Test(priority = 1)
	public void VerifyLoginForm()
			throws Throwable {
		extentTest = extent.startTest("VerifyLoginForm");
		
		loginPage.VerifyLoginPage();
	}
	
	@Test(priority = 2)
	public void VerifyLoginWithInvalidCredentials()
			throws Throwable {
		extentTest = extent.startTest("VerifyLoginWithInvalidCredentials");
		
		loginPage.WrongCredential();
	}
	
	@Test(priority = 3)
	public void VerifyLoginWithValidCredentials()
			throws Throwable {
		extentTest = extent.startTest("VerifyLoginWithValidCredentials");
		
		mangaSearchPage = loginPage.LoginSuccessfully();
		mangaSearchPage.VerifyMangaSearchPageTitle();
	}
	
	@Test(priority = 4)
	public void VerifyMangaDetailsModal()
			throws Throwable {
		extentTest = extent.startTest("VerifyMangaSearchWithAndWithoutResult");
		
		mangaSearchPage.ClearSearchField();
		mangaSearchPage.verifyMangaDetailsModal();
		
	}
	
	@Test(priority = 5)
	public void VerifyMangaSearchAndDisplay()
			throws Throwable {
		extentTest = extent.startTest("VerifyMangaSearchAndDisplay");
		
		mangaSearchPage.SearchManga("Naruto");
		mangaSearchPage.VerifySearchedCardName("Naruto");
		
		mangaSearchPage.SearchManga("One Piece");
		mangaSearchPage.VerifySearchedCardName("One Piece");
		
		mangaSearchPage.SearchManga("Seven Deadly Sins");
		mangaSearchPage.VerifySearchedCardName("Seven Deadly Sins");
		
	}
	
	@Test(priority = 6)
	public void VerifyMangaSearchWithNoResult()
			throws Throwable {
		extentTest = extent.startTest("VerifyMangaSearchWithNoResult");
		
		mangaSearchPage.SearchManga("No manga found");
		mangaSearchPage.VerifyNoMangaFound();
		
	}
	
	@Test(priority = 7)
	public void VerifyMangaSearchWithAndWithoutResult()
			throws Throwable {
		extentTest = extent.startTest("VerifyMangaSearchWithAndWithoutResult");
		
		mangaSearchPage.searchManga(searchItems);
		
	}
	
	

	@AfterMethod(alwaysRun = true)
    public void getResult(ITestResult result) throws IOException{
		if (result.getStatus() == ITestResult.FAILURE) {
			extentTest.log(LogStatus.FAIL, "TEST CASE FAILED IS " + result.getName()); // to add name in extent report
			extentTest.log(LogStatus.FAIL, "TEST CASE FAILED IS " + result.getThrowable()); // to add error/exception in
			// extent report

			String screenshotPath = EndToEndTests.getScreenshot(driver, result.getName());
			extentTest.log(LogStatus.FAIL, extentTest.addScreenCapture(screenshotPath)); // to add screenshot in extent

		} else if (result.getStatus() == ITestResult.SKIP) {
			extentTest.log(LogStatus.SKIP, "Test Case SKIPPED IS " + result.getName());
		} else if (result.getStatus() == ITestResult.SUCCESS) {
			String screenshotPath = EndToEndTests.getScreenshot(driver, result.getName());
			extentTest.log(LogStatus.PASS, extentTest.addScreenCapture(screenshotPath)); // to add screenshot in extent
			extentTest.log(LogStatus.PASS, "Test Case PASSED IS " + result.getName());

		}
    }
	
	@AfterSuite
	public void tearDown() throws IOException {
		
		driver.quit();

		CloseWebDriver();
	}

	
}


