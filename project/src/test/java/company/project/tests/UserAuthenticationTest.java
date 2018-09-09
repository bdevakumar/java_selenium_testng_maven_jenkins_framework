package company.project.tests;

import java.io.File;

import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import company.project.pages.LoginPage;
import company.project.utils.Config; 

public class UserAuthenticationTest extends WebDriverTestBase {

 
	@Test(priority=1)
 	public void testUserIsAbleToAuthenticateSuccessfullyUsingValidUsernameAndPasswordTestId1a() throws InterruptedException {
		System.out.println("\ntest UserIsAbleToAuthenticateSuccessfullyUsingValidUsernameAndPasswordTestId1a started\n");
		System.out.println("Description: A user is able to login to the application successfully when using valid credentials");
						
		LoginPage loginPage = new LoginPage(driver);
		loginPage.open();
		loginPage.login(Config.username, Config.password); 
		
		System.out.println("- Verify the logout button on the home page is visible");
		Object pass = null;
		try {
			HomePage homePage = new HomePage(driver);
			homePage.waitForElementToBeClickable(homePage.logoutButton);
			pass = true;
		} catch(Exception e) {
			pass = false;
			System.out.println(" - Failure: logout button not visible within 30 seconds");
		} finally {
			Assert.assertTrue((boolean) pass);
		}
		
		System.out.println("\ntest UserIsAbleToAuthenticateSuccessfullyUsingValidUsernameAndPasswordTestId1a passed\n");
	}

}
