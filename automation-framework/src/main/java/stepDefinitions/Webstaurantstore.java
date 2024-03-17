package stepDefinitions;

import org.openqa.selenium.By;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Webstaurantstore {

    public static void main(String[] args) throws InterruptedException
    {
        // adding this here as I am getting 403 async error
        System.setProperty("webdriver.http.factory", "jdk-http-client");
        // Mention here location of downloaded chrome driver path - tested this using Mac
        System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")
                + "/src/main/java/drivers/chromedriver");

        // setting up chrome and getting url
        WebDriver driver = setupDriver();

        // wait for search bar
        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(60)); // just defaulted to 1 minute as it will not consume it all when present
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@value='Search']")));

        // searching for stainless work table
        driver.findElement(By.cssSelector("[id=\"searchval\"]")).sendKeys("stainless work table");
        driver.findElement(By.xpath
                ("//button[@value='Search']")).click();

        String table = "Table";
        // wait for item descriptions
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@data-testid='itemDescription']")));

        List<WebElement> allSearchResults = driver.findElements(By.xpath("//span[@data-testid='itemDescription']"));
        Assert.assertNotNull(allSearchResults, "Search results is null!");

        for(WebElement eachResult : allSearchResults) {
            //verify each result has Table in the title
            Assert.assertTrue(eachResult.getText().contains(table), "Table not found!");

        }
        //assumed getting only the first page which has 60 items
        int iMaxSize = allSearchResults.size();
        WebElement lastTableElement = allSearchResults.getLast();
        String lastItemAdded = lastTableElement.getText();

        // add the last item of the first page in the cart
        driver.findElement(By.xpath
                ("//div["+iMaxSize+"]/div/form/div/div/input[2][@data-testid='itemAddCart']")).click();

        // view cart
        WebElement viewCartJS = driver.findElement(By.xpath("//div[12]/div/p/div[2]/div[2]/a[1]"));

        if(viewCartJS != null){
            // click view cart from the JS window
            viewCartJS.click();
            // wait for Empty Cart to be present - means there is product in the cart
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[1]/div[1]/div/div[1]/div/button")));
            // before emptying --  verify the last item was successfully added
            WebElement verifyLastItemAdded = driver.findElement(By.xpath("//div[2]/div/div[2]/div[1]/div[1]/div/div[2]/ul/li[2]/div/div[2]/span/a"));
            verifyLastItemAdded.getText().contains(lastItemAdded);

            // verify empty cart button is present - means there is a product in the cart
            WebElement emptyCart = driver.findElement(By.xpath("//div[1]/div[1]/div/div[1]/div/button"));
            Assert.assertNotNull(emptyCart);
        }
        else {
            // click view cart from the web icon, if JS view cart is not present anymore
            driver.findElement(By.xpath("//div/div[1]/div[4]/a/span[1]")).click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[1]/div[1]/div/div[1]/div/button")));
            WebElement emptyCart = driver.findElement(By.xpath("//div[1]/div[1]/div/div[1]/div/button"));
            Assert.assertNotNull(emptyCart);
        }

        // empty cart
        WebElement emptyCartConfirm = driver.findElement(By.xpath("//div[1]/div[1]/div/div[1]/div/button")); // empty cart button from view cart


        if(emptyCartConfirm != null){
            driver.findElement(By.xpath("//div[1]/div[1]/div/div[1]/div/button")).click();
            //wait for Empty Cart Confirmation window
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[11]/div/div/div/footer/button[1]")));
            // click Empty Cart Confirmation window
            driver.findElement(By.xpath("//div[11]/div/div/div/footer/button[1]")).click();
            // wait for Cart to Empty
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[2]/div/div[2]/div[1]/div/div[1]/div[1]/div/div[2]/p[1]")));
            // verify the cart was successfully empty
            WebElement emptyCartConfirmation = driver.findElement(By.xpath("//div[2]/div/div[2]/div[1]/div/div[1]/div[1]/div/div[2]/p[1]"));
            Assert.assertNotNull(emptyCartConfirmation, "Your cart is empty.");
        }
        else {
            WebElement cartNotEmpty = driver.findElement(By.xpath("//div[2]/div/div[2]/div[1]/div/div[1]/div[1]/div/div[2]/p[1]"));
            Assert.assertNotNull(cartNotEmpty, "Your cart is empty.");
        }
    driver.close();
    }

    public static WebDriver setupDriver(){
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--remote-allow-origins=*");
        WebDriver driver;
        chromeOptions.setPageLoadStrategy(PageLoadStrategy.NORMAL);
        driver = new ChromeDriver(chromeOptions);
        driver.manage().window().maximize();
        driver.get("https://www.webstaurantstore.com/");
        return driver;
    }


}
