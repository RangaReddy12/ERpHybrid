package commonFunctions;

import java.io.FileInputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class FunctionLibrary {
public static WebDriver driver;
public static Properties conpro;
public static String Expected="";
public static String Actual ="";
//method for launch browser
public static WebDriver startBrowser()throws Throwable
{
	conpro = new Properties();
	//load your property file
	conpro.load(new FileInputStream("./PropertyFiles/Environment.properties"));
	if(conpro.getProperty("Browser").equalsIgnoreCase("chrome"))
	{
		driver = new ChromeDriver();
		driver.manage().window().maximize();
	}
	else if(conpro.getProperty("Browser").equalsIgnoreCase("firefox"))
	{
		driver = new FirefoxDriver();
	}
	else
	{
		System.out.println("Browser value is Not matching");
	}
	return driver;
		
}
//method for launching url
public static void openUrl()
{
	driver.get(conpro.getProperty("Url"));
}
//method for waitForElement
public static void waitForElement(WebDriver driver,String Locator_Type,String Locator_Value,String waitTime)
{
	WebDriverWait mywait = new WebDriverWait(driver, Integer.parseInt(waitTime));
	if(Locator_Type.equalsIgnoreCase("xpath"))
	{
		mywait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Locator_Value)));
	}
	else if(Locator_Type.equalsIgnoreCase("name"))
	{
		mywait.until(ExpectedConditions.visibilityOfElementLocated(By.name(Locator_Value)));
	}
	else if(Locator_Type.equalsIgnoreCase("id"))
	{
		mywait.until(ExpectedConditions.visibilityOfElementLocated(By.id(Locator_Value)));
	}
}
//type action method for textboxes
public static void typeAction(WebDriver driver,String Locator_Type,String Locator_Value,String Test_Data)
{
	if(Locator_Type.equalsIgnoreCase("xpath"))
	{
		driver.findElement(By.xpath(Locator_Value)).clear();
		driver.findElement(By.xpath(Locator_Value)).sendKeys(Test_Data);
	}
	else if(Locator_Type.equalsIgnoreCase("name"))
	{
		driver.findElement(By.name(Locator_Value)).clear();
		driver.findElement(By.name(Locator_Value)).sendKeys(Test_Data);
	}
	else if(Locator_Type.equalsIgnoreCase("id"))
	{
		driver.findElement(By.id(Locator_Value)).clear();
		driver.findElement(By.id(Locator_Value)).sendKeys(Test_Data);
	}
}
//method click action for any buttons,links,radio buttonbs,checkboxes
public static void clickAction(WebDriver driver,String Locator_type,String Locator_Value)
{
	if(Locator_type.equalsIgnoreCase("xpath"))
	{
		driver.findElement(By.xpath(Locator_Value)).click();
	}
	else if(Locator_type.equalsIgnoreCase("name"))
	{
		driver.findElement(By.name(Locator_Value)).click();
	}
	else if(Locator_type.equalsIgnoreCase("id"))
	{
		driver.findElement(By.id(Locator_Value)).sendKeys(Keys.ENTER);
	}
}
//method for validating title
public static void validateTitle(WebDriver driver,String Expected_Title)
{
	String Actual_title =driver.getTitle();
	try {
	Assert.assertEquals(Expected_Title, Actual_title,"Title is Not Matching");
	}catch(Throwable t)
	{
		System.out.println(t.getMessage());
	}
}
//close browser
public static void closeBrowser(WebDriver driver)
{
	driver.quit();
}
//java time stamp
public static String generateDate()
{
	Date date = new Date();
	DateFormat df = new SimpleDateFormat("YYYY_MM_dd hh_mm_ss");
	return df.format(date);
}
//method for mouse click
public static void mouseClick(WebDriver driver) throws Throwable
{
	Actions ac = new Actions(driver);
	ac.moveToElement(driver.findElement(By.xpath("(//a[contains(text(),'Stock Items')])[2]"))).perform();
	Thread.sleep(3000);
	ac.moveToElement(driver.findElement(By.xpath("(//a[contains(text(),'Stock Categories')])[2]"))).click().perform();
}
//method category table
public static void categoryTable(WebDriver driver,String Expected) throws Throwable
{
	if(!driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).isDisplayed())
		driver.findElement(By.xpath(conpro.getProperty("search-panel"))).click();
	driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).sendKeys(Expected);
	Thread.sleep(3000);
	driver.findElement(By.xpath(conpro.getProperty("search-button"))).click();
	Thread.sleep(3000);
	String Actual =driver.findElement(By.xpath("//table[@id='tbl_a_stock_categorieslist']/tbody/tr[1]/td[4]/div/span/span")).getText();
	System.out.println(Expected+"     "+Actual);
	Assert.assertEquals(Expected, Actual,"Category Name is Not Matching");
}
//method for capturenumber
public static void captureNumber(WebDriver driver,String Locator_type,String Locator_Value)
{
	Expected =driver.findElement(By.name(Locator_Value)).getAttribute("value");
}
//method supplier table
public static void supplierTable(WebDriver driver) throws Throwable
{
	if(!driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).isDisplayed())
		driver.findElement(By.xpath(conpro.getProperty("search-panel"))).click();
	driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).sendKeys(Expected);
	Thread.sleep(3000);
	driver.findElement(By.xpath(conpro.getProperty("search-button"))).click();
	Thread.sleep(3000);
	Actual = driver.findElement(By.xpath("//table[@id='tbl_a_supplierslist']/tbody/tr[1]/td[6]/div/span/span")).getText();
	System.out.println(Expected+"    "+Actual);
	Assert.assertEquals(Expected, Actual,"Supplier Number Not Matching");
}
//method for listboxes
public static void dropDownAction(WebDriver driver, String Locator_Type, String Locator_value, String Test_Data) throws Exception
{
	if(Locator_Type.equalsIgnoreCase("xpath"))
	{
		int value = Integer.parseInt(Test_Data);
		WebElement element = driver.findElement(By.xpath(Locator_value));
		Select select = new Select(element);
		select.selectByIndex(value);
		
	}
	if(Locator_Type.equalsIgnoreCase("id"))
	{
		int value = Integer.parseInt(Test_Data);
		WebElement element = driver.findElement(By.xpath(Locator_value));
		Select select = new Select(element);
		select.selectByIndex(value);
		
	}
	
}
//method for capture stock name
public static void captureData(WebDriver driver,String Locator_Type,String Locator_Value)
{
	Expected =driver.findElement(By.xpath(Locator_Value)).getAttribute("value");
}
//method for stocktable
public static void stocktable(WebDriver driver) throws Throwable
{
	if(!driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).isDisplayed())
		driver.findElement(By.xpath(conpro.getProperty("search-panel"))).click();
	driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).sendKeys(Expected);
	Thread.sleep(3000);
	driver.findElement(By.xpath(conpro.getProperty("search-button"))).click();
	Thread.sleep(3000);
	Actual =driver.findElement(By.xpath("//table[@id='tbl_a_stock_itemslist']/tbody/tr[1]/td[8]/div/span/span")).getText();
	System.out.println(Expected+"    "+Actual);
	Assert.assertEquals(Expected, Actual,"Stock number is Not Matching");
}
}














