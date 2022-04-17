package org.example;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) {
        ArrayList<Tweet> tweets = new ArrayList<>();

        List<String> years = new ArrayList<>();
        List<String> months = new ArrayList<>();
        List<String> days = new ArrayList<>();
        addNumbersToList(years, 2006, 2022);
        addNumbersToList(months, 1, 12);
        addNumbersToList(days, 1, 31);

        WebDriver driver;

        System.setProperty("webdriver.chrome.driver","/Users/kaan.yilmaz/Documents/GitHub/twitter/twitter/Drivers/chromedriver");
        ChromeOptions options = new ChromeOptions();
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));


        for (String year : years) {
            driver.get("https://twitter.com/search-advanced?lang=tr");
            driver.manage().window().maximize();
            driver.findElement(By.xpath("//*[@id=\"layers\"]/div[2]/div/div/div/div/div/div[2]/div[2]/div/div/div/div[2]/div/div[5]/div[1]/div/label/div/div[2]/div/input"))
                    .sendKeys("@bloombergHT");

            Select selectFromYear = new Select(driver.findElement(By.xpath("/html/body/div[1]/div/div/div[1]/div[2]/div/div/div/div/div/div[2]/div[2]/div/div/div/div[2]/div/div[16]/div/div[2]/div/div[3]/select")));
            Select selectToYear = new Select(driver.findElement(By.xpath("/html/body/div[1]/div/div/div[1]/div[2]/div/div/div/div/div/div[2]/div[2]/div/div/div/div[2]/div/div[16]/div/div[4]/div/div[3]/select")));
            selectFromYear.selectByValue(year);
            selectToYear.selectByValue(year);
            for (String month : months) {
                Select selectFromMonth = new Select(driver.findElement(By.xpath("/html/body/div[1]/div/div/div[1]/div[2]/div/div/div/div/div/div[2]/div[2]/div/div/div/div[2]/div/div[16]/div/div[2]/div/div[1]/select")));
                Select selectToMonth = new Select(driver.findElement(By.xpath("/html/body/div[1]/div/div/div[1]/div[2]/div/div/div/div/div/div[2]/div[2]/div/div/div/div[2]/div/div[16]/div/div[4]/div/div[1]/select")));
                selectFromMonth.selectByValue(month);
                selectToMonth.selectByValue(String.valueOf(Integer.parseInt(month)+1));

                Select selectFromDay = new Select(driver.findElement(By.xpath("/html/body/div[1]/div/div/div[1]/div[2]/div/div/div/div/div/div[2]/div[2]/div/div/div/div[2]/div/div[16]/div/div[2]/div/div[2]/select")));
                Select selectToDay = new Select(driver.findElement(By.xpath("/html/body/div[1]/div/div/div[1]/div[2]/div/div/div/div/div/div[2]/div[2]/div/div/div/div[2]/div/div[16]/div/div[4]/div/div[2]/select")));
                selectFromDay.selectByValue("1");
                selectToDay.selectByValue("1");

                driver.findElement(By.xpath("//*[@id=\"layers\"]/div[2]/div/div/div/div/div/div[2]/div[2]/div/div/div/div[1]/div/div/div/div/div/div[3]/div")).click();

                JavascriptExecutor js = (JavascriptExecutor) driver;
                for (int i = 1; i < 50000000; i++) {
                    try {
                        Thread.sleep(2500);
                        List<WebElement> article = driver.findElements(By.tagName("article"));
                        for (WebElement webElement : article) {
                            String tweet = webElement.getText();
                            String[] tweetDecomposed = tweet.split("\n");
                            String displayName = tweetDecomposed[0];
                            String username = tweetDecomposed[1];
                            String date = tweetDecomposed[3];
                            StringBuilder tweetBuilder = new StringBuilder();
                            if (tweetDecomposed.length > 8) {
                                for (int k = 4; k + 3 <= tweetDecomposed.length - 4; k++) {
                                    tweetBuilder.append(tweetDecomposed[k]).append("\n");
                                }
                            }else {
                                tweetBuilder.append(tweetDecomposed[4]);
                            }

                            String tweetText = tweetBuilder.toString();
                            Tweet tweetObject = new Tweet(displayName, username, date, tweetText);
                            tweets.add(tweetObject);
                        }
                        js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
            }
        }

        driver.quit();
        for (Tweet tweet : tweets) {
            System.out.println(tweet.toString());
        }
    }

    private static void addNumbersToList(List<String> list , int start, int end){
        for (int i = start; i <= end; i++) {
            list.add(String.valueOf(i));
        }
    }

    private static class Tweet{
        private final String userDisplayName;
        private final String username;
        private final String date;
        private final String tweet;

        public Tweet(String userDisplayName, String username, String date, String tweet) {
            this.userDisplayName = userDisplayName;
            this.username = username;
            this.date = date;
            this.tweet = tweet;
        }

        @Override
        public String toString() {
            return "Tweet{" +
                    "userDisplayName='" + userDisplayName + '\'' +
                    ", username='" + username + '\'' +
                    ", date='" + date + '\'' +
                    ", tweet='" + tweet + '\'' +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Tweet tweet1 = (Tweet) o;
            return Objects.equals(userDisplayName, tweet1.userDisplayName) &&
                    Objects.equals(username, tweet1.username) &&
                    Objects.equals(date, tweet1.date) &&
                    Objects.equals(tweet, tweet1.tweet);
        }

        @Override
        public int hashCode() {
            return Objects.hash(userDisplayName, username, date, tweet);
        }
    }
}
