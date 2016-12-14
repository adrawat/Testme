package lmsScenariosAll;


import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class registration {
	private static WebDriver driver;
	private static Wait<WebDriver> wait;
	
	@BeforeMethod
	 public  void preSetup(){
		
				System.setProperty("webdriver.chrome.driver","/Users/cleriston/Documents/Eclipse/chromedriver");
			 	 driver = new ChromeDriver();
			 	wait = new FluentWait<WebDriver>(driver)
			            .withTimeout(5, TimeUnit.SECONDS)
			            .pollingEvery(1, TimeUnit.SECONDS)
			            .ignoring(NoSuchElementException.class);
		}
	
	 @Test	//A new user register on the LMS and see start of the tutorial
	 public void testRegisterNewandDemo() {
		driver.get("https://admin.edapp.com");
		new registration().registerNew();
		new registration().demoAfterRegister();
		System.out.println("New User Registration and Demo complete with user");
		driver.quit();
		
		
	}
	
	 @Test	//An existing user logs in and edit a slide in one lesson
	 @Parameters({"Email", "Password"})
	 public void testSlideEditLessonwithExistingUser(String email, String password ) {
		driver.get("https://admin.edapp.com");
		new registration().existingUserLogin(email, password);
		new registration().editLessonAfterlogIn();
		System.out.println("Existing user is succesfully able to edit slide in a Lesson");
		driver.quit();
		
		
	}
	 
	public void registerNew(){ //This method will Register the account
		driver.findElement(By.xpath("//a[@href='/register']")).click();
		int randomPIN = (int)(Math.random()*9000)+1000;
		String PINString = String.valueOf(randomPIN);
		driver.findElement(By.xpath("//input[@name='email']")).sendKeys("auto" + PINString + "@gmail.com");
		driver.findElement(By.xpath("//input[@name='password']")).sendKeys("Creative456");
		driver.findElement(By.xpath("//input[@name='confirm-password']")).sendKeys("Creative456");
		driver.findElement(By.xpath("//span[.='Create your free account']")).click();
		try {
			wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//h3[@class='tight tight-top block-v-md']")));
	    } catch (TimeoutException te) {
	    }
	   
		
		if (driver.findElements(By.xpath("//h3[@class='tight tight-top block-v-md']")).size()>0)
		{
			driver.findElement(By.xpath("//div[@class='btn btn-default btn-close']")).click();
			System.out.println(driver.findElement(By.className("modal-body")).getText().substring(8));
			driver.quit();
			System.exit(0);
			
		}
	}
	
	public void demoAfterRegister(){//this method runs demo with newly registered account
		driver.findElement(By.xpath("//a[@href='/tutorial']")).click();
		driver.findElement(By.xpath("//div[@class='tutorial-action btn btn-lg btn-highlight']")).click(); 
		driver.findElement(By.xpath("//textarea[@id='ipt-title-title']")).sendKeys("Test");
		
		wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//div[@class='add-slide btn btn-default btn-block highlighted']")));
		driver.findElement(By.xpath("//div[@class='add-slide btn btn-default btn-block highlighted']")).click();
		
		
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='card-construct-sentence']/a/div")));
		driver.findElement(By.xpath("//*[@id='card-construct-sentence']/a/div")).click(); 
		
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@class='tutorial-done tutorial-action text-muted']")));
		driver.findElement(By.xpath("//a[@class='tutorial-done tutorial-action text-muted']")).click();
	}
	
	public void existingUserLogin(String email, String password){
		
		driver.findElement(By.xpath("//input[@name='nameOrEmail']")).sendKeys(email);
		driver.findElement(By.xpath("//input[@name='password']")).sendKeys(password);
		driver.findElement(By.xpath("//span[.='Sign in']")).click();
		
		
	}
	
	public void editLessonAfterlogIn(){
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='courses-menu-item']/a")));
		driver.findElement(By.xpath("//*[@id='courses-menu-item']/a")).click();
		//driver.findElement(By.xpath("//a[@href='/course/new']")).click();
		driver.findElement(By.xpath("//*[@id='57674f77638f7d090626c8d6']")).click();
		
		wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//textarea[@name='course.title']")));
		driver.findElement(By.xpath("//textarea[@name='course.title']")).sendKeys("Test");
		
		driver.findElement(By.xpath("//*[contains(@title, 'Test Lesson')]")).click();
		
		//driver.findElement(By.xpath("//*[@id='edit-lesson']")).click();
		
		wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//textarea[@name='lesson.title']")));
		driver.findElement(By.xpath("//textarea[@name='lesson.title']")).sendKeys("newTest");
		
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='edit-content']")));
		driver.findElement(By.xpath("//*[@id='edit-content']")).click();
		
		wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//div[@class='add-slide btn btn-default btn-block']")));
		driver.findElement(By.xpath("//div[@class='add-slide btn btn-default btn-block']")).click();
		
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[.='Multiple Choice']")));
		driver.findElement(By.xpath("//a[.='Multiple Choice']")).click();
		
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='card-multiple-choice-game']/a/div/h4")));
		driver.findElement(By.xpath("//*[@id='card-multiple-choice-game']/a/div/h4")).click();
	    WebElement savebutton = driver.findElement(By.id("save"));
	    Actions action = new Actions(driver);
	    action.moveToElement(savebutton).click().perform();
		
	}

}
