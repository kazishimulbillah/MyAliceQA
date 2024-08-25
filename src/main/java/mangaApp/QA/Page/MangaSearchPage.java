package mangaApp.QA.Page;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import org.testng.Assert;

import mangaApp.Helper.Action.ActionHelper;
import mangaApp.Helper.Alert.AlertHelper;
import mangaApp.Helper.JavaScript.JavaScriptHelper;
import mangaApp.QA.Base.TestBase;
import mangaApp.QA.ConfigFileReader.FileReaderManager;

public class MangaSearchPage extends TestBase {

	@FindBy(xpath = "//*[@id=\"root\"]/div/h1")
	WebElement MangaSearchPageTitle;

	@FindBy(xpath = "//*[@id=\"manga-search\"]")
	WebElement SearchBox;

	@FindBy(xpath = "//button[text()=\"Search\"]")
	WebElement SearchBtn;

	@FindBy(xpath = "//*[@id=\"manga-name\"]")
	WebElement CardName;

	@FindBy(xpath = "//*[@id=\"root\"]/div/div[2]/p")
	WebElement NoMangaFoundText;

	@FindBy(xpath = "(//*[@id=\"root\"]/div/div[2]/div)")
	List<WebElement> mangaCards;

	@FindBy(xpath = "//button[text()=\"Close\"]")
	WebElement CloseBtn;

	JavaScriptHelper javaScript = new JavaScriptHelper(driver);

	ActionHelper action = new ActionHelper(driver);
	AlertHelper alert = new AlertHelper(driver);

	public MangaSearchPage() {
		PageFactory.initElements(driver, this);
	}

	public void VerifySearchedCardName(String searchItem) throws Exception {

		Thread.sleep(1000);
		Assert.assertEquals(CardName.getText(), searchItem);

	}

	public void VerifyNoMangaFound() throws Exception {

		Thread.sleep(1000);
		Assert.assertEquals(NoMangaFoundText.getText(), "No manga found");

	}

	public void VerifyMangaSearchPageTitle() throws Exception {

		Thread.sleep(1000);
		Assert.assertEquals(MangaSearchPageTitle.getText(), "Manga You Should Read");

	}

	public void SearchManga(String searchItem) throws Exception {
		Thread.sleep(500);
		SearchBox.clear();
		SearchBox.sendKeys(searchItem);

		Thread.sleep(500);
		SearchBtn.click();
	}

	public void searchManga(List<String> searchItems) throws Exception {

		for (String searchItem : searchItems) {
			Thread.sleep(500);
			SearchBox.clear();
			SearchBox.sendKeys(searchItem);

			Thread.sleep(500);
			SearchBtn.click();

			List<WebElement> mangaCards = driver.findElements(By.xpath("(//*[@id=\"manga-name\"])"));
			if (!mangaCards.isEmpty()) {
				Thread.sleep(500);
				Assert.assertEquals(mangaCards.get(0).getText(), searchItem, "Manga card name match search term.");
				System.out.println("Manga found: " + searchItem);
			}

			else if (!mangaCards.isEmpty()) {
				Thread.sleep(500);
				Assert.assertNotEquals(mangaCards.get(0).getText(), searchItem,
						"Manga card name does match with the search term.");
				System.out.println("Manga card not found ");
			} else {
				Thread.sleep(500);
				Assert.assertEquals(NoMangaFoundText.getText(), "No manga found");
				System.out.println("No manga found for: " + searchItem);
			}
		}
	}

	public void ClearSearchField() throws Exception {
		Thread.sleep(1000);
		SearchBox.clear();
		SearchBox.sendKeys("");
		Thread.sleep(1000);
		SearchBtn.click();

		Thread.sleep(1000);
	}

	public void verifyMangaDetailsModal() throws Exception {

		for (WebElement mangaCard : mangaCards) {

			Thread.sleep(500);
			String mangaName = mangaCard.findElement(By.id("manga-name")).getText();
			String mangaSummary = mangaCard.findElement(By.xpath(".//p[@class='text-gray-600 text-sm mb-4']")).getText()
					.replace("... Details", "");
			String mangaCardImageSrc = mangaCard.findElement(By.tagName("img")).getAttribute("src");

			List<WebElement> detailsLinks = mangaCard.findElements(By.xpath("(.//span[contains(text(), 'Details')])"));

			if (!detailsLinks.isEmpty()) {
				WebElement detailsLink = detailsLinks.get(0);

				detailsLink.click();
				Thread.sleep(1000);
				WebElement modal = driver.findElement(By.xpath("//*[@id=\"root\"]/div/div[3]/div"));
				Assert.assertTrue(modal.isDisplayed(), "Modal is not displayed.");

				String modalName = modal.findElement(By.xpath("//*[@id=\"root\"]/div/div[3]/div/h3")).getText();
				String modalSummary = modal.findElement(By.xpath("//*[@id=\"root\"]/div/div[3]/div/p")).getText();
				Assert.assertEquals(modalName, mangaName, "Manga name does not match.");
				Assert.assertTrue(modalSummary.contains(mangaSummary),
						"Manga summary in the modal does not match the expected content.");

				Thread.sleep(500);
				String modalImageSrc = modal.findElement(By.tagName("img")).getAttribute("src");
				Assert.assertEquals(modalImageSrc, mangaCardImageSrc,
						"Image in the modal does not match the image in the card.");

				modal.findElement(By.xpath("//button[text()=\"Close\"]")).click();

				Thread.sleep(1000);
				WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
				wait.until(
						ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@id=\"root\"]/div/div[3]/div")));

				// Assert.assertFalse(modal.isDisplayed(), "Modal is still visible.");
				break;
			} else {
				System.out.println("Details link not available for manga: " + mangaName);
			}
		}
	}

}
