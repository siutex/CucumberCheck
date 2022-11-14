package stepdefs;


import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.File;


public class ServiceHooks {

    public static WebDriver driver;

    @Before
    public void initializeTest() {
        System.out.println("Opening browser");
        System.setProperty("webdriver.chrome.driver", "path\\src\\test\\resources\\chromedriver.exe");
        driver = new ChromeDriver();
    }

    @After
    public void embedScreenshot(Scenario scenario) {
        if (scenario.isFailed()) {
            try {

                TakesScreenshot scrShot = ((TakesScreenshot) driver);
                File SrcFile = scrShot.getScreenshotAs(OutputType.FILE);
                File DestFile = new File("projectpath");
                FileUtils.copyFile(SrcFile, DestFile);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        driver.quit();
    }
}