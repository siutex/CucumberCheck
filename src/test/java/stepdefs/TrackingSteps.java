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

public class TrackingSteps {
    private static final String EMPTY_STRING = "";
    public WebDriver driver;


    public TrackingSteps() {
        driver = ServiceHooks.driver;
    }


    @Given("^I open the website in a browser main page")
    public void i_open_website_and_tracking_section() {

        WebDriverWait wait = new WebDriverWait(driver, 5);
        driver.manage().deleteAllCookies();
        driver.manage().window().maximize();
        // Go to fedex website
        driver.get("https://www.fedex.com/en-gb/home.html");

        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("a[title='close']")));
            WebElement close = driver.findElement(By.cssSelector("a[title='close']"));
            wait.until(ExpectedConditions.elementToBeClickable(close));
            close.click();
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("h1")));
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
    }

    @When("^I checked specified \"([^\"]*)\" from documentation")
    public void check_tracking_number(String arg1) {
        WebDriverWait wait = new WebDriverWait(driver, 5);
        WebElement trackingWraper = driver.findElement(By.cssSelector("a[data-analytics='hdr|tab|2|Tracking']"));
        trackingWraper.click();
        WebElement allTracking = driver.findElement(By.cssSelector("a[title='All Tracking Services']"));
        wait.until(ExpectedConditions.elementToBeClickable(allTracking));
        allTracking.click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("button.fdx-c-button--primary")));
        WebElement trackingButton = driver.findElement(By.cssSelector("button.fdx-c-button--primary"));
        assert trackingButton.getText().equals("Track");
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("input[id^=tracking_number_0")));
        WebElement trackingInput = driver.findElement(By.cssSelector("input[id^=tracking_number_0"));
        trackingInput.sendKeys(arg1);
        trackingButton.click();
    }

    @When("^I checked specified \"([^\"]*)\" from main page")
    public void check_tracking_main_page(String arg1) {
        WebDriverWait wait = new WebDriverWait(driver, 5);
        WebElement trackingCube = driver.findElement(By.cssSelector("a[data-analytics='hero|cube|TRACK']"));
        wait.until(ExpectedConditions.elementToBeClickable(trackingCube));
        trackingCube.click();
        WebElement trackingNumber = driver.findElement(By.id("trackingnumber"));
        trackingNumber.sendKeys(arg1);
        WebElement trackingButton = driver.findElement(By.cssSelector("a[data-analytics='hero|Track']"));
        trackingButton.click();
    }

    @When("^I checked specified \"([^\"]*)\" from tracking wraper")
    public void check_tracking_wraper(String arg1) {
        WebElement trackingWraper = driver.findElement(By.cssSelector("a[data-analytics='hdr|tab|2|Tracking']"));
        trackingWraper.click();
        WebElement trackingNumber = driver.findElement(By.id("trackingModuleTrackingNum"));
        trackingNumber.sendKeys(arg1);
        WebElement trackingButton = driver.findElement(By.cssSelector("button[data-analytics='hdr|tab2|Track']"));
        trackingButton.click();
    }

    @Then("^Fedex tracking page is presented with alert \"([^\"]*)\"")
    public void tracking_alert(String arg1) {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        if (!arg1.equals(EMPTY_STRING)) {
            wait.until(ExpectedConditions.presenceOfElementLocated(By.id("menuTitle")));
            WebElement error = driver.findElement(By.cssSelector("div[role='alert']"));
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div[role='alert']")));
            assert error.getText().equals(" Unfortunately, we are unable to retrieve your tracking results at this time. Please try again later. ");
        } else {
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("input[data-errmsg='Please enter at least one tracking number.']")));
        }
    }
}
