    package stepDefinitions;

    import org.openqa.selenium.By;
    import org.openqa.selenium.WebDriver;
    import org.openqa.selenium.WebElement;
    import org.openqa.selenium.support.ui.ExpectedConditions;
    import org.openqa.selenium.support.ui.WebDriverWait;
    import org.testng.Assert;

    import java.time.Duration;
    import java.util.List;

    public class ViewCartPageObject {

        private WebDriver driver;
        By viewCartJS = By.xpath("//div[12]/div/p/div[2]/div[2]/a[1]");

        By search_empty_button = By.xpath("//div[1]/div[1]/div/div[1]/div/button");

        By view_cart_web = By.xpath("//div/div[1]/div[4]/a/span[1]");

        public ViewCartPageObject(WebDriver driver) {
            this.driver = driver;
        }

        public WebElement viewCart(){
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60)); // just defaulted to 1 minute as it will not consume it all when present
            wait.until(ExpectedConditions.visibilityOfElementLocated(viewCartJS));
            WebElement confirmViewCartJS = driver.findElement(viewCartJS);
            WebElement emptyCart = null;
            if (confirmViewCartJS != null){
                confirmViewCartJS.click();
                // wait for Empty Cart to be present - means there is product in the cart
                wait.until(ExpectedConditions.visibilityOfElementLocated(search_empty_button));
                // verify empty cart button is present - means there is a product in the cart
                emptyCart = driver.findElement(search_empty_button);
            }
            else{

                // click view cart from the web icon, if JS view cart is not present anymore
                driver.findElement(view_cart_web).click();
                wait.until(ExpectedConditions.visibilityOfElementLocated(search_empty_button));
                emptyCart = driver.findElement(search_empty_button);
            }
            return emptyCart;
        }


    }


