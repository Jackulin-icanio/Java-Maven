package com.example;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

/**
 * LoginPage class represents a login page using Playwright for browser automation.
 * This class provides methods to interact with login form elements and perform login operations.
 */
public class LoginPage {
    private final Page page;
    
    // Locators for login page elements
    private final Locator usernameField;
    private final Locator passwordField;
    private final Locator loginButton;
    private final Locator errorMessage;
    private final Locator successMessage;

    /**
     * Constructor to initialize the LoginPage with a Playwright Page object.
     * 
     * @param page The Playwright Page object representing the browser page
     */
    public LoginPage(Page page) {
        this.page = page;
        this.usernameField = page.locator("#username");
        this.passwordField = page.locator("#password");
        this.loginButton = page.locator("#loginButton");
        this.errorMessage = page.locator("#errorMessage");
        this.successMessage = page.locator("#successMessage");
    }

    /**
     * Navigate to the login page URL.
     * 
     * @param url The URL of the login page
     */
    public void navigateTo(String url) {
        page.navigate(url);
    }

    /**
     * Enter username in the username field.
     * 
     * @param username The username to enter
     */
    public void enterUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }
        usernameField.clear();
        usernameField.fill(username);
    }

    /**
     * Enter password in the password field.
     * 
     * @param password The password to enter
     */
    public void enterPassword(String password) {
        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }
        passwordField.clear();
        passwordField.fill(password);
    }

    /**
     * Click the login button to submit the form.
     */
    public void clickLoginButton() {
        loginButton.click();
    }

    /**
     * Perform a complete login operation with username and password.
     * 
     * @param username The username for login
     * @param password The password for login
     */
    public void login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLoginButton();
    }

    /**
     * Check if the login was successful by looking for success message.
     * 
     * @return true if login was successful, false otherwise
     */
    public boolean isLoginSuccessful() {
        try {
            return successMessage.isVisible();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Check if there's an error message displayed.
     * 
     * @return true if error message is visible, false otherwise
     */
    public boolean hasErrorMessage() {
        try {
            return errorMessage.isVisible();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Get the text of the error message if present.
     * 
     * @return The error message text, or empty string if not present
     */
    public String getErrorMessage() {
        try {
            if (hasErrorMessage()) {
                return errorMessage.textContent();
            }
        } catch (Exception e) {
            // Log error if needed
        }
        return "";
    }

    /**
     * Get the text of the success message if present.
     * 
     * @return The success message text, or empty string if not present
     */
    public String getSuccessMessage() {
        try {
            if (isLoginSuccessful()) {
                return successMessage.textContent();
            }
        } catch (Exception e) {
            // Log error if needed
        }
        return "";
    }

    /**
     * Check if the username field is visible and enabled.
     * 
     * @return true if username field is ready for input, false otherwise
     */
    public boolean isUsernameFieldReady() {
        try {
            return usernameField.isVisible() && usernameField.isEnabled();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Check if the password field is visible and enabled.
     * 
     * @return true if password field is ready for input, false otherwise
     */
    public boolean isPasswordFieldReady() {
        try {
            return passwordField.isVisible() && passwordField.isEnabled();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Check if the login button is visible and enabled.
     * 
     * @return true if login button is ready to be clicked, false otherwise
     */
    public boolean isLoginButtonReady() {
        try {
            return loginButton.isVisible() && loginButton.isEnabled();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Get the current page title.
     * 
     * @return The page title
     */
    public String getPageTitle() {
        return page.title();
    }

    /**
     * Get the current page URL.
     * 
     * @return The current URL
     */
    public String getCurrentUrl() {
        return page.url();
    }

    /**
     * Wait for the page to load completely.
     * 
     * @param timeout Timeout in milliseconds
     */
    public void waitForPageLoad(int timeout) {
        page.waitForLoadState(com.microsoft.playwright.options.LoadState.NETWORKIDLE, 
                             new Page.WaitForLoadStateOptions().setTimeout(timeout));
    }

    /**
     * Clear all form fields.
     */
    public void clearFields() {
        usernameField.clear();
        passwordField.clear();
    }

    /**
     * Validate that all required form elements are present on the page.
     * 
     * @return true if all elements are present, false otherwise
     */
    public boolean validatePageElements() {
        try {
            return usernameField.isVisible() && 
                   passwordField.isVisible() && 
                   loginButton.isVisible();
        } catch (Exception e) {
            return false;
        }
    }
}