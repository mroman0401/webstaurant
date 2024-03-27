    package stepDefinitions;

    import org.openqa.selenium.By;
    import org.openqa.selenium.WebDriver;
    import org.openqa.selenium.WebElement;
    import org.openqa.selenium.support.ui.ExpectedConditions;
    import org.openqa.selenium.support.ui.WebDriverWait;
    import org.testng.Assert;

    import java.time.Duration;

    public class EmptyCartPageObject {

        private WebDriver driver;
        By emptyCartButton = By.xpath("//div[1]/div[1]/div/div[1]/div/button");

        By emptyCartButtonConfirmation = By.xpath("//div[11]/div/div/div/footer/button[1]");

        By emptyCartMessage = By.xpath("//div[2]/div/div[2]/div[1]/div/div[1]/div[1]/div/div[2]/p[1]");
        public EmptyCartPageObject(WebDriver driver) {
            this.driver = driver;
        }

        public WebElement emptyCart(){
            WebElement emptyCart = null;
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60)); // just defaulted to 1 minute as it will not consume it all when present

            if(driver.findElement(emptyCartButton) != null){
                driver.findElement(emptyCartButton).click();
                //wait for Empty Cart Confirmation window
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[11]/div/div/div/footer/button[1]")));
                // click Empty Cart Confirmation window
                driver.findElement(emptyCartButtonConfirmation).click();
                // wait for Cart to Empty
                wait.until(ExpectedConditions.visibilityOfElementLocated(emptyCartMessage));
                // verify the cart was successfully empty
                emptyCart = driver.findElement(emptyCartMessage);
            }
            else {
                emptyCart = driver.findElement(emptyCartMessage);
            }

            return emptyCart;
        }


    }


