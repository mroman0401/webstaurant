    package stepDefinitions;
    import org.openqa.selenium.By;
    import org.openqa.selenium.WebDriver;
    import org.openqa.selenium.WebElement;
    import org.testng.Assert;

    import java.util.List;

    public class SearchPageObject {

        WebDriver driver = null;
        By textbox_search = By.cssSelector("[id=\"searchval\"]");
        By button_search = By.xpath("//button[@value='Search']");

        By search_results = By.xpath("//span[@data-testid='itemDescription']");

        public SearchPageObject(WebDriver driver){
            this.driver = driver;
        }

        public WebDriver getDriver(){
            WebDriver driver1 = SearchPageObject.this.driver;
            return driver1;
        }

        public WebElement verifySearchTextBoxIsPresent(){
            WebElement verifySearchBox = driver.findElement(textbox_search);
            return verifySearchBox;
        }
        public void  setTextBox(String text){
            driver.findElement(textbox_search).sendKeys(text);
        }

        public void  clickSearchButton(){
            driver.findElement(button_search).click();
        }

        public List<WebElement> verifySearchResults(){
            List<WebElement> allSearchResults = driver.findElements(search_results);
            return allSearchResults;
        }

        public int maxSizePerPage(){
            //assumed getting only the first page which has 60 items
            int iMaxSize = verifySearchResults().size();
            return  iMaxSize;
        }

        public WebElement getLastItemToBeAdded(){
            // last item of the first page in the cart
            WebElement last_item = driver.findElement(By.xpath("//div["+maxSizePerPage()+"]/div/form/div/div/input[2][@data-testid='itemAddCart']"));
            return last_item;
        }

    }


