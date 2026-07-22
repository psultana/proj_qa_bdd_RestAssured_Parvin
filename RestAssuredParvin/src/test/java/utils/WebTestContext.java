package utils;

import org.openqa.selenium.WebDriver;
import pages.LoginPage;
import pages.DashboardPage;
import pages.CustomersPage;
import pages.EmployeesPage;

public class WebTestContext {
    private WebDriver driver;
    private String baseUrl;
    private LoginPage loginPage;
    private DashboardPage dashboardPage;
    private CustomersPage customersPage;
    private EmployeesPage employeesPage;

    public WebTestContext(WebDriver driver, String baseUrl) {
        this.driver = driver;
        this.baseUrl = baseUrl;
        initPages();
    }

    private void initPages() {
        this.loginPage = new LoginPage(driver);
        this.dashboardPage = new DashboardPage(driver);
        this.customersPage = new CustomersPage(driver);
        this.employeesPage = new EmployeesPage(driver);
    }

    public WebDriver getDriver() { return driver; }
    public String getBaseUrl() { return baseUrl; }
    public LoginPage getLoginPage() { return loginPage; }
    public DashboardPage getDashboardPage() { return dashboardPage; }
    public CustomersPage getCustomersPage() { return customersPage; }
    public EmployeesPage getEmployeesPage() { return employeesPage; }
}
