package stepDefinitions;

import org.openqa.selenium.By;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class TestSearchPage {

    static WebDriver driver = null;

    public static void main(String[] args) throws InterruptedException
    {
        // adding this here as I am getting 403 async error
        System.setProperty("webdriver.http.factory", "jdk-http-client");
        // Mention here location of downloaded chrome driver path - tested this using Mac
        System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")
                + "/src/main/java/drivers/chromedriver");

        // setting up chrome and getting url
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--remote-allow-origins=*");
        chromeOptions.setPageLoadStrategy(PageLoadStrategy.NORMAL);
        driver = new ChromeDriver(chromeOptions);
        driver.manage().window().maximize();

        // searching for stainless work table
        SearchPageObject searchPageObject = new SearchPageObject(driver);
        driver.get("https://www.webstaurantstore.com/");
        searchPageObject.setTextBox("stainless work table");
        searchPageObject.clickSearchButton();


        //
        driver.quit();
    }


}
