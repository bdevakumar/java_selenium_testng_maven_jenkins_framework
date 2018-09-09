package company.project.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import company.project.utils.Config;

public class LoginPage extends BasePage {
	
	public String url;
	
	public By usernameField = By.id("username");
	public By passwordField = By.id("password");
	public By loginButton = By.name("Submit");
	
	public LoginPage(WebDriver driver) {
		this.driver = driver; 
		this.url = Config.baseUrl;
	}
	
	public void open() {
		System.out.println("- navigate to login page: " + this.url);
		driver.get(this.url);
	}

	public void enterAccountNumber(String username) {
		System.out.println("- enter account number: " + username);
		this.waitForElementToBeVisible(usernameField);
		driver.findElement(usernameField).clear();
		driver.findElement(usernameField).sendKeys(username);
	}
	
	public void enterPassword(String password) {
		System.out.println("- enter password: " + password);
		driver.findElement(passwordField).clear();
		driver.findElement(passwordField).sendKeys(password);
	}
	
	public void clickLoginButton() {
		System.out.println("- click login button");
		driver.findElement(loginButton).click();
	}
	
	public void login(String faxNumber, String password) {
		this.enterAccountNumber(faxNumber);
		this.enterPassword(password);
		this.clickLoginButton();
	}

}
