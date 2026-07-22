package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import java.util.List;

public class CustomersPage {
    private final WebDriver driver;

    @FindBy(name = "search")
    private WebElement searchField;

    @FindBy(name = "status")
    private WebElement statusDropdown;

    @FindBy(name = "sort")
    private WebElement sortDropdown;

    @FindBy(xpath = "//button[@type='submit']")
    private WebElement searchButton;

    @FindBy(xpath = "//table//tr")
    private List<WebElement> tableRows;

    public CustomersPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void search(String searchTerm) {
        searchField.clear();
        searchField.sendKeys(searchTerm);
    }

    public void filterByStatus(String status) {
        new Select(statusDropdown).selectByValue(status);
    }

    public void sortBy(String sortOption) {
        new Select(sortDropdown).selectByValue(sortOption);
    }

    public void clickSearch() {
        searchButton.click();
    }

    public int getTableRowCount() {
        return tableRows.size();
    }

    public boolean hasCustomers() {
        return tableRows.size() > 1;
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    public String getPageSource() {
        return driver.getPageSource();
    }
}
