package company.project.pages;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.mail.Message;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import company.project.utils.Config;
import company.project.utils.DatabaseUtility;
import company.project.utils.EmailRetriever;

public abstract class BasePage {

	public WebDriver driver;
 	

 	public void waitForElementToBeVisible(By element) {
		WebDriverWait wait = new WebDriverWait(driver, 10); // 10 seconds
		wait.until(ExpectedConditions.presenceOfElementLocated(element));
	}
	
	public void waitForElementToBeClickable(By element) {
		WebDriverWait wait = new WebDriverWait(driver, 30); // 30 seconds
		wait.until(ExpectedConditions.elementToBeClickable(element));
	}
	
	public void scrollToTheBottomOfThePage() {
		JavascriptExecutor jse = (JavascriptExecutor)driver;
		jse.executeScript("window.scrollTo(0, document.body.scrollHeight);");
	}
	
	public void scrollToTheTopOfThePage() {
		JavascriptExecutor jse = (JavascriptExecutor)driver;
		jse.executeScript("window.scrollTo(0, 0);");
	}
	
	public void uploadFile(By chooseFileButtonLocator) {
		String filePath = System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" + File.separator + "java" + File.separator + "company" + File.separator + "project" + File.separator + "utils" + File.separator + "testData" + File.separator + "attachment.pdf";
		this.driver.findElement(chooseFileButtonLocator).sendKeys(filePath);
	}
	
	public void retrieveForgotPasswordEmailContents() throws Exception {
		System.out.println("Retrieve new password from 'forgot password' email sent to user when they click 'forgot password' link and enter their email address");
		
		Message email = EmailRetriever.getEmail(Config.emailAddress, Config.emailAddressPassword, Config.forgotPasswordEmailSubjectLine); 
		System.out.println("The email with subject: " + email.getSubject() + " was received by customer");
		
		String email_content = EmailRetriever.getTextFromMessage(email);
		String[] email_contents = email_content.split(" ");
		
//      uncomment this to find the index of the element you want to extract(ex: new password) in the email_contents array using the console
//		for (int i = 0; i < email_contents.length; i++) {
//			System.out.println(i + email_contents[i]);
//		}
		
 		String newPassword = email_contents[44].replace("PASSWORD:", "").trim();
		
 		System.out.println("New password retrieved from welcome email: " + newPassword);
		
 		Config.password = newPassword;
	}
	
	public void executeSQLqueryToSetPasswordOfCurrentUserToExpired() {
		
		System.out.println("\nretrieve user's customerkey using his username");
		String query = "select t.customerkey from customer_table t where t.username = '" + Config.username + "'";
		
		ArrayList<String> arrayContainingCustomerKey = null;
		try {
			arrayContainingCustomerKey = DatabaseUtility.executeSQLQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		String customerKey = arrayContainingCustomerKey.get(0).toString().trim().replace("CUSTOMERKEY:", "");
		System.out.println("User's customer key: " + customerKey + "\n"); 
		
		System.out.println("\nChange the createDateTime fields in password history table to expire the user's password (assuming password expires in 90 days)");
	    String query2 = "update password_history_table t set t.createdatetime = t.createdatetime - 91 where t.customerkey = '" + customerKey + "'";
	    try {
			DatabaseUtility.executeSQLQuery(query2);
		} catch (SQLException e) {
		} 
	    
		System.out.println("\nCompleted setting current user's password to expired.\n");
	}

}
