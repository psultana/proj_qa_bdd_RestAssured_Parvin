# RestAssured + Selenium + Cucumber BDD Test Suite

Complete test automation framework using **BDD (Cucumber)**, **Selenium WebDriver**, **TestNG**, and **Page Object Model (POM)** for REST APIs and web applications.

## Project Stack
- **Framework**: Cucumber BDD
- **Test Runner**: TestNG
- **API Testing**: RestAssured (framework included)
- **Web Testing**: Selenium WebDriver 4.11.0
- **Dependency Injection**: PicoContainer
- **Build Tool**: Maven
- **Language**: Java 17

## Project Structure

```
src/test/
├── java/
│   ├── base/
│   │   └── BaseTest.java                 # WebDriver setup/teardown
│   ├── pages/
│   │   ├── LoginPage.java               # Login page object
│   │   ├── DashboardPage.java           # Dashboard page object
│   │   ├── CustomersPage.java           # Customers list page object
│   │   ├── EmployeesPage.java           # Employees list page object
│   │   └── HomePage.java                # Home page placeholder
│   ├── steps/
│   │   ├── WebSteps.java                # Cucumber step definitions for web tests
│   │   ├── ApiSteps.java                # Cucumber step definitions for API tests
│   │   └── UISteps.java                 # Additional UI steps
│   ├── runners/
│   │   └── TestRunner.java              # Cucumber + TestNG runner
│   └── utils/
│       ├── ApiClient.java               # RestAssured client
│       ├── TestContext.java             # Context for API tests
│       └── WebTestContext.java          # Context for web tests (manages page objects)
└── resources/
    └── features/
        ├── api_crud.feature             # Web app test scenarios (login, navigation, filtering)
        └── ui.feature                   # UI smoke test

```

## Test Scenarios

### Web App Tests (api_crud.feature)
- ✅ Successful login with valid credentials
- ✅ Failed login with invalid credentials  
- ✅ Logout functionality
- ✅ View dashboard after login
- ✅ Access customers page requires login
- ✅ View customers list
- ✅ Search customers (filters by search param)
- ✅ View employees list
- ✅ Filter employees by department

### API Tests (API skeleton included)
RestAssured client is ready for JSON API testing (GET, POST, PUT endpoints).

## Prerequisites
- Java 17+
- Maven 3.8.1+
- Chrome/Chromium browser (for Selenium tests)

## Running Tests

### Run all tests
```bash
mvn test
```

### Run with custom base URL
```bash
mvn test -Dapi.base=http://your-api-url:port
```

### Run specific test
```bash
mvn test -Dtest=TestRunner
```

### Skip tests (compile only)
```bash
mvn -DskipTests=true test-compile
```

### View Cucumber HTML report
```
target/cucumber-report.html
```

## Project Configuration

- **Default URL**: `http://192.168.1.47:8085`
- **Headless Mode**: Enabled by default (set `headless=false` in BaseTest to disable)
- **Implicit Wait**: 10 seconds (WebDriverWait)
- **Page Object Model**: All UI elements managed via Page classes
- **Dependency Injection**: Cucumber PicoContainer handles step definition injection

## Key Features

✅ **BDD Framework** - Gherkin feature files for business-readable tests  
✅ **Page Object Model** - Clean separation of selectors and step logic  
✅ **TestNG Integration** - Parallel execution capable  
✅ **WebDriver Management** - WebDriverManager auto-downloads drivers  
✅ **Cucumber Reporting** - HTML test reports generated in target/  
✅ **Headless Browser** - Fast, CI/CD friendly test execution  
✅ **RestAssured Ready** - API test framework included (JSON/REST support)  

## Customization

### Add a new feature
1. Create `.feature` file in `src/test/resources/features/`
2. Implement step definitions in `src/test/java/steps/`
3. Create Page Object in `src/test/java/pages/` if needed
4. Run: `mvn test`

### Add a new Page Object
```java
@FindBy(css = "selector")
private WebElement element;

public void doAction() {
    element.click();
}
```

### Update selectors
XPath and CSS selectors in Page classes. Match your actual HTML structure.

## Notes

- Tests run headless (no UI visible) by default - faster execution
- Cucumber PicoContainer auto-injects dependencies into step classes
- All steps are keyword-driven (Given/When/Then format)
- Error messages include response body for debugging
