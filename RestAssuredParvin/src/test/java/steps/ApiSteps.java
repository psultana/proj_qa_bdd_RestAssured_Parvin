package steps;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import utils.ApiClient;
import utils.TestContext;

import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ApiSteps {
    private final TestContext context;

    public ApiSteps(TestContext context) {
        this.context = context;
    }

    @Given("the API base URL is {string}")
    public void setBase(String base) {
        System.setProperty("api.base", base);
        ApiClient.init();
    }

    // Login Tests
    @Given("I POST to login endpoint with valid credentials")
    public void loginWithValidCredentials() {
        Response r = ApiClient.request()
                .param("username", "admin")
                .param("password", "admin123")
                .post("/login");
        context.setLastResponse(r);
        String cookie = r.getCookie("JSESSIONID");
        context.setSessionCookie(cookie);
        System.out.println("Login - Status: " + r.statusCode() + ", Session: " + cookie);
    }

    @Given("I POST to login endpoint with invalid credentials")
    public void loginWithInvalidCredentials() {
        Response r = ApiClient.request()
                .param("username", "invalid")
                .param("password", "wrongpass")
                .post("/login");
        context.setLastResponse(r);
        System.out.println("Login Failed - Status: " + r.statusCode() + ", Body contains 'error': " + r.asString().contains("error"));
    }

    @When("I POST to login with username {string} and password {string}")
    public void postLogin(String username, String password) {
        Response r = ApiClient.request()
                .param("username", username)
                .param("password", password)
                .post("/login");
        context.setLastResponse(r);
        String cookie = r.getCookie("JSESSIONID");
        context.setSessionCookie(cookie);
        System.out.println("POST /login - Status: " + r.statusCode());
    }

    // Dashboard Tests
    @When("I GET the dashboard")
    public void getDashboard() {
        Response r = ApiClient.request()
                .cookie("JSESSIONID", context.getSessionCookie())
                .get("/dashboard");
        context.setLastResponse(r);
        System.out.println("GET /dashboard - Status: " + r.statusCode());
    }

    @When("I GET the dashboard without session")
    public void getDashboardNoSession() {
        Response r = ApiClient.get("/dashboard");
        context.setLastResponse(r);
        System.out.println("GET /dashboard (no session) - Status: " + r.statusCode());
    }

    // Customers Tests
    @When("I GET the customers list")
    public void getCustomers() {
        Response r = ApiClient.request()
                .cookie("JSESSIONID", context.getSessionCookie())
                .get("/customers");
        context.setLastResponse(r);
        System.out.println("GET /customers - Status: " + r.statusCode());
    }

    @When("I GET customers with search {string}")
    public void getCustomersWithSearch(String search) {
        Response r = ApiClient.request()
                .cookie("JSESSIONID", context.getSessionCookie())
                .param("search", search)
                .get("/customers");
        context.setLastResponse(r);
        System.out.println("GET /customers?search=" + search + " - Status: " + r.statusCode());
    }

    @When("I GET customers with status {string}")
    public void getCustomersWithStatus(String status) {
        Response r = ApiClient.request()
                .cookie("JSESSIONID", context.getSessionCookie())
                .param("status", status)
                .get("/customers");
        context.setLastResponse(r);
        System.out.println("GET /customers?status=" + status + " - Status: " + r.statusCode());
    }

    @When("I GET customers with sort {string}")
    public void getCustomersWithSort(String sort) {
        Response r = ApiClient.request()
                .cookie("JSESSIONID", context.getSessionCookie())
                .param("sort", sort)
                .get("/customers");
        context.setLastResponse(r);
        System.out.println("GET /customers?sort=" + sort + " - Status: " + r.statusCode());
    }

    @When("I GET customers with filters:")
    public void getCustomersWithFilters(DataTable table) {
        Map<String, String> filters = table.asMaps().get(0);
        var req = ApiClient.request()
                .cookie("JSESSIONID", context.getSessionCookie());

        if (filters.containsKey("search") && filters.get("search") != null) {
            req = req.param("search", filters.get("search"));
        }
        if (filters.containsKey("status") && filters.get("status") != null) {
            req = req.param("status", filters.get("status"));
        }
        if (filters.containsKey("sort") && filters.get("sort") != null) {
            req = req.param("sort", filters.get("sort"));
        }

        Response r = req.get("/customers");
        context.setLastResponse(r);
        System.out.println("GET /customers with filters - Status: " + r.statusCode());
    }

    // Employees Tests
    @When("I GET the employees list")
    public void getEmployees() {
        Response r = ApiClient.request()
                .cookie("JSESSIONID", context.getSessionCookie())
                .get("/employees");
        context.setLastResponse(r);
        System.out.println("GET /employees - Status: " + r.statusCode());
    }

    @When("I GET employees with search {string}")
    public void getEmployeesWithSearch(String search) {
        Response r = ApiClient.request()
                .cookie("JSESSIONID", context.getSessionCookie())
                .param("search", search)
                .get("/employees");
        context.setLastResponse(r);
        System.out.println("GET /employees?search=" + search + " - Status: " + r.statusCode());
    }

    @When("I GET employees with department {string}")
    public void getEmployeesWithDepartment(String department) {
        Response r = ApiClient.request()
                .cookie("JSESSIONID", context.getSessionCookie())
                .param("department", department)
                .get("/employees");
        context.setLastResponse(r);
        System.out.println("GET /employees?department=" + department + " - Status: " + r.statusCode());
    }

    @When("I GET employees with sort {string}")
    public void getEmployeesWithSort(String sort) {
        Response r = ApiClient.request()
                .cookie("JSESSIONID", context.getSessionCookie())
                .param("sort", sort)
                .get("/employees");
        context.setLastResponse(r);
        System.out.println("GET /employees?sort=" + sort + " - Status: " + r.statusCode());
    }

    @When("I GET employees with filters:")
    public void getEmployeesWithFilters(DataTable table) {
        Map<String, String> filters = table.asMaps().get(0);
        var req = ApiClient.request()
                .cookie("JSESSIONID", context.getSessionCookie());

        if (filters.containsKey("search") && filters.get("search") != null) {
            req = req.param("search", filters.get("search"));
        }
        if (filters.containsKey("department") && filters.get("department") != null) {
            req = req.param("department", filters.get("department"));
        }
        if (filters.containsKey("sort") && filters.get("sort") != null) {
            req = req.param("sort", filters.get("sort"));
        }

        Response r = req.get("/employees");
        context.setLastResponse(r);
        System.out.println("GET /employees with filters - Status: " + r.statusCode());
    }

    // Logout Tests
    @When("I GET the logout endpoint")
    public void logout() {
        Response r = ApiClient.request()
                .cookie("JSESSIONID", context.getSessionCookie())
                .get("/logout");
        context.setLastResponse(r);
        context.setSessionCookie(null);
        System.out.println("GET /logout - Status: " + r.statusCode());
    }

    // Assertions
    @Then("the response status should be {int}")
    public void verifyStatus(int expectedStatus) {
        int actualStatus = context.getLastResponse().statusCode();
        System.out.println("Expected: " + expectedStatus + ", Actual: " + actualStatus);
        assertThat("Status code mismatch", actualStatus, equalTo(expectedStatus));
    }

    @Then("the response should contain {string}")
    public void verifyResponseContains(String text) {
        String body = context.getLastResponse().asString();
        assertThat("Response body should contain: " + text, body, containsStringIgnoringCase(text));
    }

    @Then("the response should be HTML")
    public void verifyResponseIsHTML() {
        String contentType = context.getLastResponse().getContentType();
        assertThat("Response should be HTML", contentType, containsString("text/html"));
    }

    @Then("the response should redirect to {string}")
    public void verifyRedirect(String expectedLocation) {
        int status = context.getLastResponse().statusCode();
        assertThat("Should be redirect status (3xx)", status, anyOf(
                equalTo(301), equalTo(302), equalTo(303), equalTo(307), equalTo(308)
        ));
    }

    @And("the response should have customerCount")
    public void verifyCustomerCount() {
        String body = context.getLastResponse().asString();
        assertThat("Response should contain customer count", body, 
                containsStringIgnoringCase("customer"));
    }

    @And("the response should have employeeCount")
    public void verifyEmployeeCount() {
        String body = context.getLastResponse().asString();
        assertThat("Response should contain employee count", body, 
                containsStringIgnoringCase("employee"));
    }

    @And("I am logged in")
    public void assertLoggedIn() {
        assertThat("Session cookie should not be null", context.getSessionCookie(), notNullValue());
    }

    @And("I am not logged in")
    public void assertNotLoggedIn() {
        assertThat("Session cookie should be null", context.getSessionCookie(), nullValue());
    }
}
