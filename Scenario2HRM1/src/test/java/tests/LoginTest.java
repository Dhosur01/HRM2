package tests;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.LoginPage;
import utils.BaseTest;

import java.time.Duration;

public class LoginTest extends BaseTest {
    private WebDriver driver;
    private LoginPage loginPage;

    @BeforeClass
    public void setUp() {
        System.setProperty("webdriver.edge.driver", "C:\\Users\\klaks\\Downloads\\edgedriver_win64\\msedgedriver.exe");

        EdgeOptions options = new EdgeOptions();
        options.addArguments("--remote-allow-origins=*");

        driver = new EdgeDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));

        loginPage = new LoginPage(driver);
        driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("username")));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("password")));
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(@class, 'oxd-button')]")));

        // Perform login once before all tests
        loginPage.enterUsername("Admin");
        loginPage.enterPassword("admin123");
        loginPage.clickLoginButton();

        // Log information
        System.out.println("Setup complete. Ready to start the tests.");
    }

    @BeforeMethod
    public void refreshPage() {
        // Refresh the page before each test without navigating away
        driver.navigate().refresh();
        System.out.println("Page refreshed before test method.");
    }

    @Test
    public void testValidLogin() {
        System.out.println("Starting testValidLogin test.");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".oxd-sidepanel-body")));
        } catch (org.openqa.selenium.TimeoutException e) {
            System.out.println("Left side menu not found, trying alternative locator.");
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@class, 'oxd-sidepanel-body')]")));
        }

        System.out.println("Left side menu is visible.");
        Assert.assertTrue(driver.findElement(By.cssSelector(".oxd-sidepanel-body")).isDisplayed(), "Login failed for valid credentials");
    }

    @Test
    public void testSecondScenario() {
        System.out.println("Starting testSecondScenario test.");

        // Example: Add another test scenario here
        Assert.assertTrue(true, "Dummy test passed.");
    }

    @AfterMethod
    public void tearDownMethod() {
        System.out.println("AfterMethod executed.");
        // Here you can add any steps needed after each test method, but no driver.quit()
    }

    @AfterClass
    public void tearDown() {
        System.out.println("Tearing down class.");
        if (driver != null) {
            driver.quit();
        }
    }
}
