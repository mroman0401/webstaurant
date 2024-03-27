package stepDefinitions;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.List;

public class TestAddToCartThenEmpty_TestNG {

    WebDriver driver = null;

    @BeforeTest
    public void setUpDriver(){
        // adding this here as I am getting 403 async error
        System.setProperty("webdriver.http.factory", "jdk-http-client");
        // Mention here location of downloaded chrome driver path - tested this using Mac
        System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")
                + "/src/main/java/drivers/chromedriver");

        // setting up chrome and getting url
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @Test (priority = 1)
    public void testSearchPage()
    {
        // searching for stainless work table
        SearchPageObject searchPageObject = new SearchPageObject(driver);
        driver.get("https://www.webstaurantstore.com/");

        Assert.assertNotNull(searchPageObject.verifySearchTextBoxIsPresent(), "Search box is not present!");
        searchPageObject.setTextBox("stainless work table");

        searchPageObject.clickSearchButton();
        List<WebElement> allSearchResults = searchPageObject.verifySearchResults();
        Assert.assertNotNull(allSearchResults, "There are no search results!");

    }

    @Test (priority = 2)
    public void testTableSearchResults(){
        SearchPageObject searchPageObject = new SearchPageObject(driver);
        String table = "Table";
        for(WebElement eachResult : searchPageObject.verifySearchResults()) {
            //verify each result has Table in the title
            Assert.assertTrue(eachResult.getText().contains(table), "Table not found!");
        }
    }

    @Test (priority = 3)
    public void testAddLastItem(){
        SearchPageObject searchPageObject = new SearchPageObject(driver);
        Assert.assertNotNull(searchPageObject.getLastItemToBeAdded());
        searchPageObject.getLastItemToBeAdded().click();
    }

    @Test (priority = 4)
    public void testViewCart(){
        ViewCartPageObject viewCartPageObject = new ViewCartPageObject(driver);
        Assert.assertNotNull(viewCartPageObject.viewCart(), "There is no product in the cart!");
    }

    @Test (priority = 5)
    public void testEmptyCart(){
        EmptyCartPageObject emptyCartPageObject = new EmptyCartPageObject(driver);
        Assert.assertNotNull(emptyCartPageObject.emptyCart(), "Your cart is empty.");
    }

    @AfterTest
    public void tearDown(){
        driver.quit();
    }

}
