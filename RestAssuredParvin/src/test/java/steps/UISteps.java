package steps;

import io.cucumber.java.Before;
import io.cucumber.java.After;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.CustomersPage;
import pages.DashboardPage;
import pages.EmployeesPage;
import pages.LoginPage;
import utils.WebTestContext;

import java.time.Duration;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class UISteps {
    private WebDriver driver;
    private WebTestContext context;
    private WebDriverWait wait;
    private String appUrl = "http://192.168.1.47:8085";
    private LoginPage loginPage;
    private DashboardPage dashboardPage;
    private CustomersPage customersPage;
    private EmployeesPage employeesPage;

    @Before
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-blink-features=AutomationControlled");
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        context = new WebTestContext(driver, appUrl);
        loginPage = context.getLoginPage();
        dashboardPage = context.getDashboardPage();
        customersPage = context.getCustomersPage();
        employeesPage = context.getEmployeesPage();
        System.out.println("Browser initialized for: " + appUrl);
    }

    @After
    public void tearDown() {
        if (driver != null) {
            try {
                driver.quit();
                System.out.println("Browser closed successfully");
            } catch (Exception e) {
                System.out.println("Error closing browser: " + e.getMessage());
            }
        }
    }

    // Login Steps
    @Given("the application URL is {string}")
    public void setAppUrl(String url) {
        this.appUrl = url;
        System.out.println("Application URL set to: " + url);
    }

    @Given("I am on the login page")
    public void openLoginPage() {
        loginPage.open(appUrl);
        System.out.println("Navigated to login page: " + driver.getCurrentUrl());
    }

    @When("I enter username {string}")
    public void enterUsername(String username) {
        loginPage.enterUsername(username);
        System.out.println("Entered username: " + username);
    }

    @When("I enter password {string}")
    public void enterPassword(String password) {
        loginPage.enterPassword(password);
        System.out.println("Entered password: ****");
    }

    @When("I click the login button")
    public void clickLogin() {
        loginPage.clickLogin();
        System.out.println("Login button clicked");
        wait.until(driver -> driver.getCurrentUrl().contains("dashboard") || driver.getCurrentUrl().contains("login"));
    }

    @Then("I should be redirected to the dashboard")
    public void verifyDashboardRedirect() {
        String currentUrl = driver.getCurrentUrl();
        System.out.println("Current URL after login: " + currentUrl);
        assertThat("Should be on dashboard", currentUrl, containsString("/dashboard"));
    }

    @Then("I should see an error message")
    public void verifyErrorMessage() {
        boolean errorDisplayed = loginPage.isErrorDisplayed();
        System.out.println("Error message displayed: " + errorDisplayed);
        assertThat("Error message should be displayed", errorDisplayed, is(true));
    }

    // Logout Steps
    @When("I click the logout button")
    public void clickLogout() {
        dashboardPage.clickLogout();
        System.out.println("Logout button clicked");
        wait.until(driver -> driver.getCurrentUrl().contains("/login"));
    }

    @Then("I should be redirected to the login page")
    public void verifyLoginPageRedirect() {
        String currentUrl = driver.getCurrentUrl();
        System.out.println("Current URL after logout: " + currentUrl);
        assertThat("Should be on login page", currentUrl, containsString("/login"));
    }

    // Dashboard Steps
    @Given("I am logged in with username {string} and password {string}")
    public void loginWithCredentials(String username, String password) {
        openLoginPage();
        enterUsername(username);
        enterPassword(password);
        clickLogin();
        wait.until(driver -> driver.getCurrentUrl().contains("/dashboard"));
        System.out.println("Successfully logged in as: " + username);
    }

    @Then("I should see customer count")
    public void verifyCustomerCount() {
        boolean visible = dashboardPage.isCustomerCountVisible();
        System.out.println("Customer count visible: " + visible);
        assertThat("Customer count should be visible", visible, is(true));
    }

    @Then("I should see employee count")
    public void verifyEmployeeCount() {
        boolean visible = dashboardPage.isEmployeeCountVisible();
        System.out.println("Employee count visible: " + visible);
        assertThat("Employee count should be visible", visible, is(true));
    }

    // Protected Page Access Steps
    @When("I try to access the customers page directly")
    public void accessCustomersDirectly() {
        driver.get(appUrl + "/customers");
        System.out.println("Attempted to access customers page directly");
        wait.until(driver -> driver.getCurrentUrl().contains("/login") || driver.getCurrentUrl().contains("/customers"));
    }

    @When("I try to access the employees page directly")
    public void accessEmployeesDirectly() {
        driver.get(appUrl + "/employees");
        System.out.println("Attempted to access employees page directly");
        wait.until(driver -> driver.getCurrentUrl().contains("/login") || driver.getCurrentUrl().contains("/employees"));
    }

    // Customers Page Steps
    @When("I navigate to the customers page")
    public void navigateToCustomers() {
        dashboardPage.clickCustomersLink();
        wait.until(driver -> driver.getCurrentUrl().contains("/customers"));
        System.out.println("Navigated to customers page: " + driver.getCurrentUrl());
    }

    @Then("I should see the customers list")
    public void verifyCustomersListVisible() {
        boolean hasCustomers = customersPage.hasCustomers();
        System.out.println("Customers list visible: " + hasCustomers);
        assertThat("Customers list should be visible", hasCustomers, is(true));
    }

    @When("I search for a customer")
    public void searchCustomer() {
        customersPage.search("test");
        customersPage.clickSearch();
        System.out.println("Searched for customer: test");
    }

    @When("I search for a customer with {string}")
    public void searchCustomerWith(String searchTerm) {
        customersPage.search(searchTerm);
        customersPage.clickSearch();
        System.out.println("Searched for customer: " + searchTerm);
    }

    @Then("the customer list should be filtered")
    public void verifyCustomersFiltered() {
        String url = driver.getCurrentUrl();
        boolean isFiltered = url.contains("search=") || url.contains("status=") || url.contains("sort=");
        System.out.println("Customers filtered (URL contains filter params): " + isFiltered);
        assertThat("Customer list should be filtered", isFiltered, is(true));
    }

    @When("I filter customers by status {string}")
    public void filterCustomersByStatus(String status) {
        customersPage.filterByStatus(status);
        customersPage.clickSearch();
        System.out.println("Filtered customers by status: " + status);
    }

    @When("I sort customers by {string}")
    public void sortCustomersBy(String sortOption) {
        customersPage.sortBy(sortOption);
        customersPage.clickSearch();
        System.out.println("Sorted customers by: " + sortOption);
    }

    // Employees Page Steps
    @When("I navigate to the employees page")
    public void navigateToEmployees() {
        dashboardPage.clickEmployeesLink();
        wait.until(driver -> driver.getCurrentUrl().contains("/employees"));
        System.out.println("Navigated to employees page: " + driver.getCurrentUrl());
    }

    @Then("I should see the employees list")
    public void verifyEmployeesListVisible() {
        boolean hasEmployees = employeesPage.hasEmployees();
        System.out.println("Employees list visible: " + hasEmployees);
        assertThat("Employees list should be visible", hasEmployees, is(true));
    }

    @When("I search for an employee with {string}")
    public void searchEmployeeWith(String searchTerm) {
        employeesPage.search(searchTerm);
        employeesPage.clickSearch();
        System.out.println("Searched for employee: " + searchTerm);
    }

    @When("I filter by department")
    public void filterByDepartment() {
        String pageSource = employeesPage.getPageSource();
        if (pageSource.contains("department") || pageSource.contains("Department")) {
            employeesPage.filterByDepartment("IT");
            employeesPage.clickSearch();
            System.out.println("Filtered employees by department: IT");
        } else {
            System.out.println("No department filter available on page");
        }
    }

    @When("I filter employees by department {string}")
    public void filterEmployeesByDepartment(String department) {
        employeesPage.filterByDepartment(department);
        employeesPage.clickSearch();
        System.out.println("Filtered employees by department: " + department);
    }

    @When("I sort employees by {string}")
    public void sortEmployeesBy(String sortOption) {
        employeesPage.sortBy(sortOption);
        employeesPage.clickSearch();
        System.out.println("Sorted employees by: " + sortOption);
    }

    @Then("the employee list should be filtered")
    public void verifyEmployeesFiltered() {
        String url = driver.getCurrentUrl();
        boolean isFiltered = url.contains("search=") || url.contains("department=") || url.contains("sort=");
        System.out.println("Employees filtered (URL contains filter params): " + isFiltered);
        assertThat("Employee list should be filtered", isFiltered, is(true));
    }

    // Generic verification steps
    @Then("the page opens")
    public void pageOpens() {
        String currentUrl = driver.getCurrentUrl();
        System.out.println("Page opened: " + currentUrl);
        assertThat("Page should be from app domain", currentUrl, containsString(appUrl.replace("http://", "").replace("https://", "")));
    }

    @Then("I should see {string}")
    public void shouldSeeText(String text) {
        String pageSource = driver.getPageSource();
        boolean found = pageSource.contains(text);
        System.out.println("Text '" + text + "' found on page: " + found);
        assertThat("Text should be visible on page", found, is(true));
    }

    @When("I wait {int} seconds")
    public void waitSeconds(int seconds) {
        try {
            Thread.sleep(seconds * 1000L);
            System.out.println("Waited " + seconds + " seconds");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
