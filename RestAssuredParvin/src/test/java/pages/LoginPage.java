package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {
    private final WebDriver driver;

    @FindBy(name = "username")
    private WebElement usernameField;

    @FindBy(name = "password")
    private WebElement passwordField;

    @FindBy(xpath = "//button[@type='submit'] | //input[@type='submit']")
    private WebElement loginButton;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void open(String baseUrl) {
        driver.get(baseUrl + "/login");
    }

    public void enterUsername(String username) {
        usernameField.clear();
        usernameField.sendKeys(username);
    }

    public void enterPassword(String password) {
        passwordField.clear();
        passwordField.sendKeys(password);
    }

    public void clickLogin() {
        loginButton.click();
    }

    public boolean isErrorDisplayed() {
        try {
            String pageSource = driver.getPageSource().toLowerCase();
            return pageSource.contains("error") || pageSource.contains("invalid");
        } catch (Exception e) {
            return false;
        }
    }

    public String getErrorText() {
        String pageSource = driver.getPageSource();
        if (pageSource.contains("Invalid")) {
            return "Invalid username or password";
        }
        return "";
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }
}
