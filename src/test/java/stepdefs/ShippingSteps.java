package stepdefs;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ShippingSteps {

    private static final String EMPTY_STRING = "";
    public WebDriver driver;


    public ShippingSteps() {
        driver = ServiceHooks.driver;
    }


    @Given("^I open the website in a browser and go to all shipping services")
    public void i_open_website_and_shipping_section() {

        WebDriverWait wait = new WebDriverWait(driver, 5);
        driver.manage().deleteAllCookies();
        driver.manage().window().maximize();
        // Go to fedex website
        driver.get("https://www.fedex.com/en-gb/home.html");
        try {
            WebElement close = driver.findElement(By.cssSelector("a[title='close']"));
            wait.until(ExpectedConditions.elementToBeClickable(close));
            close.click();
        } catch (NoSuchElementException e) {
            System.out.println("No language selection");
        }
        try {

            WebElement cookies = driver.findElement(By.xpath("//button[contains(text(),'ACCEPT ALL COOKIES')]"));
            wait.until(ExpectedConditions.elementToBeClickable(cookies));
            cookies.click();
        } catch (NoSuchElementException e) {
            System.out.println("No cookies");
        }

        WebElement shippingWraper = driver.findElement(By.cssSelector("a[data-analytics='hdr|tab|1||Shipping']"));
        shippingWraper.click();
        WebElement allShipping = driver.findElement(By.cssSelector("a[title='ALL SHIPPING SERVICES']"));
        wait.until(ExpectedConditions.elementToBeClickable(allShipping));
        allShipping.click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("a[data-analytics='hero|Ship Now']")));
    }

    @When("^I login with credentials \"([^\"]*)\" and \"([^\"]*)\" to SHIP NOW feature")
    public void i_login_with_credentials_and(String arg1, String arg2) {
        WebDriverWait wait = new WebDriverWait(driver, 5);
        WebElement shipNow = driver.findElement(By.cssSelector("a[data-analytics='hero|Ship Now']"));
        wait.until(ExpectedConditions.elementToBeClickable(shipNow));
        shipNow.click();

        WebElement fedexLogin = driver.findElement(By.cssSelector("h1"));
        assert fedexLogin.getText().equals("fedex.com Login");
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("content")));

        WebElement userId = driver.findElement(By.cssSelector("input[name='username']"));
        wait.until(ExpectedConditions.elementToBeClickable(userId));
        userId.sendKeys(arg1);

        WebElement password = driver.findElement(By.cssSelector(("input[name='password']")));
        password.sendKeys(arg2);

        WebElement logIn = driver.findElement(By.cssSelector(("input[name='login']")));
        wait.until(ExpectedConditions.elementToBeClickable(logIn));
        logIn.click();
    }

    @Then("^Sufficient error message is for inside Login presented \"([^\"]*)\" and \"([^\"]*)\"$")
    public void inside_error_displayed(String arg1, String arg2) {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        WebElement error = driver.findElement(By.id("usernameerror"));
        if (arg1.equals(EMPTY_STRING) && !arg2.equals(EMPTY_STRING)) {
            wait.until(ExpectedConditions.textToBePresentInElement(error, "\n" +
                    "Login incorrect. User IDs and passwords are case sensitive. Please try again.\n" +
                    "\n"));
        } else if (!arg1.equals(EMPTY_STRING) && arg2.equals(EMPTY_STRING)) {
            wait.until(ExpectedConditions.textToBePresentInElement(error, "\n" +
                    "Login incorrect. User IDs and passwords are case sensitive. Please try again.\n" +
                    "\n"));
        } else {
            WebElement userError = driver.findElement(By.id("invalidusererror"));
            assert userError.getText().contains("Login incorrect. Either the user ID or password combination is incorrect or the account has been locked");
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("a[name='forgotUidPwd']")));
        }
    }

}
