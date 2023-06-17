package base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLOutput;
import java.time.Duration;
import java.util.Properties;

public abstract class TestBase {

    public WebDriver driver;

    public Properties  prop;
    @BeforeMethod
    public void setup()
    {
        driver = initializeBrowser();
        driver.get("https://ca.indeed.com/");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

    }

    @AfterMethod
    public void tearDown()
    {
        driver.close();
    }

    @BeforeSuite
    public void loadPropertiesFile()
    {
        prop = new Properties();
        File file = new File("C:\\Users\\anoop\\IdeaProjects\\QAGURU\\Indeed-WebScrapping\\src\\main\\java\\indeed\\Properties\\cofig.properties");
        FileInputStream fis;
        try {
            fis = new FileInputStream(file);
        } catch (Exception e) {
            System.out.println("File not found");
            e.printStackTrace();
            throw new RuntimeException();
        }
        try {
            prop.load(fis);
        } catch (IOException e) {
            System.out.println("Prop file not loaded");
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }

    public WebDriver initializeBrowser()
    {
        String browser = prop.getProperty("browser");
        if(browser.equalsIgnoreCase("chrome"))
        {
            driver = new ChromeDriver();
        } else if (browser.equalsIgnoreCase("firefox"))
        {
            driver = new FirefoxDriver();
        } else if (browser.equalsIgnoreCase("edge"))
        {
            driver = new EdgeDriver();
        }
        return driver;

    }



}
