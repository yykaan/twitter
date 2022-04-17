package org.example;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;

import java.time.Duration;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        WebDriver driver;

        System.setProperty("webdriver.chrome.driver","Drivers\\chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        //options.addArguments("--headless");
        driver = new ChromeDriver(options);
        Actions action = new Actions(driver);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://twitter.com/search-advanced?lang=tr");
        driver.manage().window().maximize();
        driver.findElement(By.xpath("//*[@id=\"layers\"]/div[2]/div/div/div/div/div/div[2]/div[2]/div/div/div/div[2]/div/div[5]/div[1]/div/label/div/div[2]/div/input")).sendKeys("@ibrahim_ozkan61");
        driver.findElement(By.xpath("//*[@id=\"layers\"]/div[2]/div/div/div/div/div/div[2]/div[2]/div/div/div/div[1]/div/div/div/div/div/div[3]/div")).click();

        JavascriptExecutor js = (JavascriptExecutor) driver;
            for (int i = 1; i < 100; i++) {
                System.out.println(driver.findElement(By.xpath("/html/body/div[1]/div/div/div[2]/main/div/div/div/div[1]/div/div[2]/div/section/div/div/div/")).getText());
                System.out.println("---------"+i+"-------------");
                js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
             }
    }
}
