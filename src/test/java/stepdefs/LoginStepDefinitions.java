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


public class LoginStepDefinitions {

    private static final String EMPTY_STRING = "";
    public WebDriver driver;


    public LoginStepDefinitions() {
        driver = ServiceHooks.driver;
    }


    @Given("^I open the website in a browser and close language window if exists")
    public void i_open_the_website_in_a_browser() {

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

        WebElement loginWrapper = driver.findElement(By.id("fxg-dropdown-signIn"));
        loginWrapper.click();
        WebElement loginButton = driver.findElement(By.cssSelector("a[title='Log In']"));
        wait.until(ExpectedConditions.elementToBeClickable(loginButton));
        loginButton.click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("createUserId")));
    }

    @When("^I login with credentials \"([^\"]*)\" and \"([^\"]*)\"$")
    public void i_login_with_credentials_and(String arg1, String arg2) {
        WebDriverWait wait = new WebDriverWait(driver, 5);
        WebElement user = driver.findElement(By.id("userId"));
        wait.until(ExpectedConditions.elementToBeClickable(user));
        user.sendKeys(arg1);

        WebElement password = driver.findElement(By.id("password"));
        wait.until(ExpectedConditions.elementToBeClickable(password));
        password.sendKeys(arg2);

        WebElement loginForm = driver.findElement(By.id("login-btn"));
        loginForm.click();

    }

    @Then("^Sufficient error message is presented \"([^\"]*)\" and \"([^\"]*)\"$")
    public void sufficient_error_displayed(String arg1, String arg2) {
        WebDriverWait wait = new WebDriverWait(driver, 5);
        if (arg1.equals(EMPTY_STRING) && !arg2.equals(EMPTY_STRING)) {
            wait.until(ExpectedConditions.presenceOfElementLocated(By.id("numCharError")));
        } else if (!arg1.equals(EMPTY_STRING) && arg2.equals(EMPTY_STRING)) {
            wait.until(ExpectedConditions.presenceOfElementLocated(By.id("numCharError")));
        } else {
            wait.until(ExpectedConditions.presenceOfElementLocated(By.id("invalidCredentials")));
        }
    }
}



