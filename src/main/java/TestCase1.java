import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.remote.tracing.opentelemetry.SeleniumSpanExporter;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.time.chrono.ThaiBuddhistEra;
import java.util.Iterator;
import java.util.List;

public class TestCase1 {
    static ChromeDriver driver = new ChromeDriver();

    public static String status = "passed";

    @BeforeAll
    public static void setup() {
        System.setProperty("webdriver.chrome.driver", "/bin/chromedriver");
        driver.get("https://demoqa.com/broken");
        System.out.println("Otvaram sajt");
        DevTools devTools = driver.getDevTools();
        devTools.createSession();

    }

    @Test()
    public void TestingSite() throws Exception {
        int iBrokenImageCount = 0;
        iBrokenImageCount = 0;
        List<WebElement> image_list = driver.findElements(By.tagName("img"));
        /* Print the total number of images on the page */
        System.out.println("The page under test has " + image_list.size() + " images");
        for (WebElement img : image_list) {
            if (img != null) {
                if (img.getAttribute("naturalWidth").equals("0")) {
                    System.out.println(img.getAttribute("outerHTML") + " is broken.");
                    iBrokenImageCount++;
                    System.out.println(iBrokenImageCount);
                }
            }
        }
        if (iBrokenImageCount > 0) {
            Assertions.assertTrue(false);
        } else {
            Assertions.assertTrue(true);
        }
    }

    @Test
    public void brokenlinkscheck() throws IOException, InterruptedException {
        int BrokenLinks = 0;

        List<WebElement> links = driver.findElements(By.tagName("a"));
        System.out.println(links.size());
        for (int i = 0; i < links.size(); i++) {
            WebElement element = links.get(i);
            String url = element.getAttribute("href");
            if (url != null) {
                URL link = new URL(url);
                HttpURLConnection httpConn = (HttpURLConnection) link.openConnection();
                httpConn.connect();
                int resCode = httpConn.getResponseCode();
                if(resCode>=400)
                {
                    BrokenLinks++;
                    System.out.println(link + " " + "is broken");
                }
            }
            if(BrokenLinks != 0){
                System.out.println("Number of broken links" + " " + BrokenLinks);
                Assertions.assertTrue(false);
            }
            else{
                Assertions.assertTrue(true);
            }

        }
    }
    @AfterAll
    public static void driverquit(){
        driver.quit();
    }

}


