import java.io.IOException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;

/* This the an simple java program that will access an PHP webpage 
   and check the Title and content below an clickable link*/

public class Testphp
{


/* This is main method  */

public static void main(String[] args)throws IOException,InterruptedException
{

/* Using below statements we are setting up the chromedriver */

System.setProperty("webdriver.chrome.driver","/usr/bin/chromedriver");
ChromeOptions chromeoptions = new ChromeOptions();
chromeoptions.addArguments("--headless");
chromeoptions.addArguments("--no-sandbox");

/* Using WebDriver interface we are invoking an driver object to the chrome browser  */
WebDriver d = new ChromeDriver(chromeoptions);

/* Using the get() method we are navigating to the URL */
d.get("http://localhost/");

/* Below we are assigning the expected title of the webpage to the string variable*/
String expectedTitle = ("Home | Simple PHP Website");

/* Using the getTitle() method we are reading and assigning webpage title to string variable */
String actualTitle = d.getTitle();

/* Using the Assertion assertEqual() method we are checking if both objects are equal verifying the title */
Assert.assertEquals(actualTitle,expectedTitle);

/* Using the findElement() method we are accessing the webpage element and clicking the link */
d.findElement(By.linkText("About Us")).click();

/* Using the findElement() and getText() methods we are accessing the webpage element by Id 
and reading the text and assigning to string variable */
String actualcontent = d.findElement(By.id("PID-ab2-pg")).getText();

/* Below we are assigning the expected text to the string variable */
String expectedcontent = ("This is about page. Lorem Ipsum Dipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.");

/* Using the Assertion assertEqual() method we are checking if both objects are equal verifying the text */
Assert.assertEquals(actualcontent,expectedcontent);

/* Below using if statement and getPageSource().contains() methods we are verifying if the text is found 
in the page then it will return either true or false.Based on that it will print PASS when true and FAIL 
when the condition is false. */

if(d.getPageSource().contains("Aldus PageMaker"))
System.out.println("PASS");
else
System.out.println("FAIL");

/* Using the quit() method closes the browser windows */
d.quit();
}
}

