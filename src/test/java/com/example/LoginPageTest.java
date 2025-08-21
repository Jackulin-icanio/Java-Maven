package com.example;

import com.microsoft.playwright.*;
import org.junit.jupiter.api.*;
import static org.assertj.core.api.Assertions.*;

/**
 * Test class for LoginPage functionality.
 * This class contains comprehensive tests to ensure code coverage for the LoginPage class.
 */
class LoginPageTest {
    
    private static Playwright playwright;
    private static Browser browser;
    private BrowserContext context;
    private Page page;
    private LoginPage loginPage;

    @BeforeAll
    static void launchBrowser() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(true));
    }

    @AfterAll
    static void closeBrowser() {
        playwright.close();
    }

    @BeforeEach
    void createContextAndPage() {
        context = browser.newContext();
        page = context.newPage();
        loginPage = new LoginPage(page);
        
        // Create a mock login page HTML for testing
        String html = """
            <!DOCTYPE html>
            <html>
            <head><title>Login Page</title></head>
            <body>
                <form id="loginForm">
                    <input type="text" id="username" placeholder="Username" />
                    <input type="password" id="password" placeholder="Password" />
                    <button type="button" id="loginButton">Login</button>
                    <div id="errorMessage" style="display:none;">Invalid credentials</div>
                    <div id="successMessage" style="display:none;">Login successful</div>
                </form>
                <script>
                    document.getElementById('loginButton').addEventListener('click', function() {
                        const username = document.getElementById('username').value;
                        const password = document.getElementById('password').value;
                        const errorMsg = document.getElementById('errorMessage');
                        const successMsg = document.getElementById('successMessage');
                        
                        if (username === 'admin' && password === 'password') {
                            errorMsg.style.display = 'none';
                            successMsg.style.display = 'block';
                        } else {
                            successMsg.style.display = 'none';
                            errorMsg.style.display = 'block';
                        }
                    });
                </script>
            </body>
            </html>
            """;
        
        page.setContent(html);
    }

    @AfterEach
    void closeContext() {
        context.close();
    }

    @Test
    @DisplayName("Test successful login with valid credentials")
    void testSuccessfulLogin() {
        // Test the complete login process
        loginPage.login("admin", "password");
        
        // Wait a bit for the JavaScript to execute
        page.waitForTimeout(100);
        
        assertThat(loginPage.isLoginSuccessful()).isTrue();
        assertThat(loginPage.getSuccessMessage()).isEqualTo("Login successful");
        assertThat(loginPage.hasErrorMessage()).isFalse();
    }

    @Test
    @DisplayName("Test failed login with invalid credentials")
    void testFailedLogin() {
        loginPage.login("invalid", "invalid");
        
        // Wait a bit for the JavaScript to execute
        page.waitForTimeout(100);
        
        assertThat(loginPage.hasErrorMessage()).isTrue();
        assertThat(loginPage.getErrorMessage()).isEqualTo("Invalid credentials");
        assertThat(loginPage.isLoginSuccessful()).isFalse();
    }

    @Test
    @DisplayName("Test individual field operations")
    void testIndividualFieldOperations() {
        // Test entering username
        loginPage.enterUsername("testuser");
        assertThat(page.locator("#username").inputValue()).isEqualTo("testuser");
        
        // Test entering password
        loginPage.enterPassword("testpass");
        assertThat(page.locator("#password").inputValue()).isEqualTo("testpass");
        
        // Test clicking login button
        loginPage.clickLoginButton();
        page.waitForTimeout(100);
        
        // Should show error for invalid credentials
        assertThat(loginPage.hasErrorMessage()).isTrue();
    }

    @Test
    @DisplayName("Test field validation with null and empty values")
    void testFieldValidation() {
        // Test null username
        assertThatThrownBy(() -> loginPage.enterUsername(null))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Username cannot be null or empty");
        
        // Test empty username
        assertThatThrownBy(() -> loginPage.enterUsername(""))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Username cannot be null or empty");
        
        // Test null password
        assertThatThrownBy(() -> loginPage.enterPassword(null))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Password cannot be null or empty");
        
        // Test empty password
        assertThatThrownBy(() -> loginPage.enterPassword(""))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Password cannot be null or empty");
    }

    @Test
    @DisplayName("Test page element readiness checks")
    void testElementReadiness() {
        assertThat(loginPage.isUsernameFieldReady()).isTrue();
        assertThat(loginPage.isPasswordFieldReady()).isTrue();
        assertThat(loginPage.isLoginButtonReady()).isTrue();
        assertThat(loginPage.validatePageElements()).isTrue();
    }

    @Test
    @DisplayName("Test page navigation and properties")
    void testPageNavigation() {
        String testUrl = "https://example.com/login";
        
        // Test navigation to a different URL
        loginPage.navigateTo(testUrl);
        assertThat(loginPage.getCurrentUrl()).isEqualTo(testUrl);
        
        // Test page title
        String title = loginPage.getPageTitle();
        assertThat(title).isNotNull();
    }

    @Test
    @DisplayName("Test clearing form fields")
    void testClearFields() {
        // Fill fields first
        loginPage.enterUsername("testuser");
        loginPage.enterPassword("testpass");
        
        // Verify fields are filled
        assertThat(page.locator("#username").inputValue()).isEqualTo("testuser");
        assertThat(page.locator("#password").inputValue()).isEqualTo("testpass");
        
        // Clear fields
        loginPage.clearFields();
        
        // Verify fields are cleared
        assertThat(page.locator("#username").inputValue()).isEmpty();
        assertThat(page.locator("#password").inputValue()).isEmpty();
    }

    @Test
    @DisplayName("Test page load waiting")
    void testPageLoadWaiting() {
        // Test waiting for page load (should not throw exception)
        assertThatCode(() -> loginPage.waitForPageLoad(5000))
            .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("Test error handling for missing elements")
    void testErrorHandlingForMissingElements() {
        // Navigate to a page without the expected elements
        page.setContent("<html><body><h1>No login form here</h1></body></html>");
        
        LoginPage pageWithoutElements = new LoginPage(page);
        
        // These should return false gracefully instead of throwing exceptions
        assertThat(pageWithoutElements.isUsernameFieldReady()).isFalse();
        assertThat(pageWithoutElements.isPasswordFieldReady()).isFalse();
        assertThat(pageWithoutElements.isLoginButtonReady()).isFalse();
        assertThat(pageWithoutElements.validatePageElements()).isFalse();
        assertThat(pageWithoutElements.hasErrorMessage()).isFalse();
        assertThat(pageWithoutElements.isLoginSuccessful()).isFalse();
        assertThat(pageWithoutElements.getErrorMessage()).isEmpty();
        assertThat(pageWithoutElements.getSuccessMessage()).isEmpty();
    }

    @Test
    @DisplayName("Test username field with whitespace")
    void testUsernameWithWhitespace() {
        // Test username with only whitespace
        assertThatThrownBy(() -> loginPage.enterUsername("   "))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Username cannot be null or empty");
    }

    @Test
    @DisplayName("Test password field with whitespace")
    void testPasswordWithWhitespace() {
        // Test password with only whitespace
        assertThatThrownBy(() -> loginPage.enterPassword("   "))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Password cannot be null or empty");
    }
}