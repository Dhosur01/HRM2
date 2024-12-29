package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class AdminPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Locators
    private By menuOptions = By.cssSelector(".oxd-sidepanel-body ul li");
    private By adminMenu = By.xpath("//span[text()='Admin']");
    private By searchButton = By.xpath("/html/body/div/div[1]/div[2]/div[2]/div/div[1]/div[2]/form/div[2]/button[2]");
    private By userNameField = By.xpath("/html/body/div/div[1]/div[2]/div[2]/div/div[1]/div[2]/form/div[1]/div/div[1]/div/div[2]/input");
    private By totalRecords = By.xpath("/html[1]/body[1]/div[1]/div[1]/div[2]/div[2]/div[1]/div[2]/div[2]/div[1]/span[1]");
    private By resetButton = By.xpath("/html/body/div/div[1]/div[2]/div[2]/div/div[1]/div[2]/form/div[2]/button[1]");
    private By userRoleDropdown = By.xpath("//*[@id=\"app\"]/div[1]/div[2]/div[2]/div/div[1]/div[2]/form/div[1]/div/div[2]/div/div[2]/div/div/div[2]/i");
    private By userRoleAdminOption = By.xpath("//div[@class=\"oxd-select-option\"]//span[text()=\"Admin\"]");
    private By userStatusDropdown = By.xpath("//*[@id=\"app\"]/div[1]/div[2]/div[2]/div/div[1]/div[2]/form/div[1]/div/div[4]/div/div[2]/div/div/div[2]/i");
    private By userStatusEnabledOption = By.xpath("//div[@class=\"oxd-select-option\"]//span[text()=\"Enabled\"]");
    private By userStatusDisabledOption = By.xpath("//div[@class=\"oxd-select-option\"]//span[text()=\"Disabled\"]");
    private By adminPageHeader = By.cssSelector(".oxd-topbar-header-breadcrumb");
    private By searchResults = By.cssSelector(".oxd-table-body");

    // Constructor
    public AdminPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }

    // Methods
    public int getLeftMenuOptionsCount() {
        List<WebElement> options = driver.findElements(menuOptions);
        return options.size();
    }

    public void clickAdminMenu() {
        wait.until(ExpectedConditions.elementToBeClickable(adminMenu)).click();
    }

    public void searchByUserName(String username) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(userNameField)).sendKeys(username);
        wait.until(ExpectedConditions.elementToBeClickable(searchButton)).click();
    }

    public void searchByUserRole(String role) {
        wait.until(ExpectedConditions.elementToBeClickable(userRoleDropdown)).click();
        wait.until(ExpectedConditions.elementToBeClickable(userRoleAdminOption)).click();
        wait.until(ExpectedConditions.elementToBeClickable(searchButton)).click();
    }

    public void searchByUserStatus(String status) {
        wait.until(ExpectedConditions.elementToBeClickable(userStatusDropdown)).click();
        if (status.equalsIgnoreCase("Enabled")) {
            wait.until(ExpectedConditions.elementToBeClickable(userStatusEnabledOption)).click();
        } else {
            wait.until(ExpectedConditions.elementToBeClickable(userStatusDisabledOption)).click();
        }
        wait.until(ExpectedConditions.elementToBeClickable(searchButton)).click();
    }

    public String getTotalRecordsText() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(searchResults));
        WebElement totalRecordsElement = wait.until(ExpectedConditions.visibilityOfElementLocated(totalRecords));
        System.out.println("Total records element found: " + totalRecordsElement.getText());
        return totalRecordsElement.getText();
    }
    /*public String getTotalRecordsText() {
    wait.until(ExpectedConditions.visibilityOfElementLocated(searchResults));
    WebElement totalRecordsElement = wait.until(ExpectedConditions.visibilityOfElementLocated(totalRecords));
    return totalRecordsElement.getText();
}
*/

    public void resetSearch() {
        wait.until(ExpectedConditions.elementToBeClickable(resetButton)).click();
    }

    public boolean isAdminPageDisplayed() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(adminPageHeader)).isDisplayed();
    }
}
