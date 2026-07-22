package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class DashboardPage {
    private final WebDriver driver;

    @FindBy(xpath = "//a[contains(text(), 'Logout')]")
    private WebElement logoutButton;

    @FindBy(xpath = "//a[contains(@href, '/customers')]")
    private WebElement customersLink;

    @FindBy(xpath = "//a[contains(@href, '/employees')]")
    private WebElement employeesLink;

    public DashboardPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void clickLogout() {
        logoutButton.click();
    }

    public boolean isCustomerCountVisible() {
        try {
            String pageSource = driver.getPageSource();
            return pageSource.contains("Customer") || pageSource.contains("customer");
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isEmployeeCountVisible() {
        try {
            String pageSource = driver.getPageSource();
            return pageSource.contains("Employee") || pageSource.contains("employee");
        } catch (Exception e) {
            return false;
        }
    }

    public void clickCustomersLink() {
        customersLink.click();
    }

    public void clickEmployeesLink() {
        employeesLink.click();
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    public String getPageSource() {
        return driver.getPageSource();
    }
}
