package tests;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pages.AdminPage;
import pages.LoginPage;
import utils.BaseTest;

public class AdminTest extends BaseTest {
    private WebDriver driver;
    private LoginPage loginPage;
    private AdminPage adminPage;

    @BeforeClass
    public void setUp() {
        System.setProperty("webdriver.edge.driver", "C:\\Users\\klaks\\Downloads\\edgedriver_win64\\msedgedriver.exe");

        EdgeOptions options = new EdgeOptions();
        options.addArguments("--remote-allow-origins=*");

        driver = new EdgeDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));

        loginPage = new LoginPage(driver);
        adminPage = new AdminPage(driver);
        driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");

        // Perform login once before all tests
        loginPage.enterUsername("Admin");
        loginPage.enterPassword("admin123");
        loginPage.clickLoginButton();
    }

    @BeforeMethod
    public void refreshPage() {
        // Refresh the page before each test without navigating away
        driver.navigate().refresh();
    }

    @Test
    public void testLeftMenuOptionsCountAndClickAdmin() {
        int menuOptionsCount = adminPage.getLeftMenuOptionsCount();
        System.out.println("Left menu options count: " + menuOptionsCount);

        Assert.assertEquals(menuOptionsCount, 12, "Left menu options count is not 12");

        adminPage.clickAdminMenu();
        Assert.assertTrue(adminPage.isAdminPageDisplayed(), "Admin page did not open successfully");
    }

    @Test
    public void testSearchByUserName() {
        adminPage.clickAdminMenu();
        adminPage.searchByUserName("Admin");

        String totalRecordsText = adminPage.getTotalRecordsText();
        System.out.println("Total records found: " + totalRecordsText);

        Assert.assertTrue(totalRecordsText.length() > 0, "No records found");

        adminPage.resetSearch();
    }

    @Test
    public void testSearchByUserRole() {
        adminPage.clickAdminMenu();
        adminPage.searchByUserRole("Admin");

        String totalRecordsText = adminPage.getTotalRecordsText();
        System.out.println("Total records found: " + totalRecordsText);

        Assert.assertTrue(totalRecordsText.length() > 0, "No records found");

        adminPage.resetSearch();
    }

    @Test
    public void testSearchByUserStatus() {
        adminPage.clickAdminMenu();
        adminPage.searchByUserStatus("Enabled");

        String totalRecordsText = adminPage.getTotalRecordsText();
        System.out.println("Total records found: " + totalRecordsText);

        Assert.assertTrue(totalRecordsText.length() > 0, "No records found");

        adminPage.resetSearch();
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
