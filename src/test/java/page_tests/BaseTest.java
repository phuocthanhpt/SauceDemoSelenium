package page_tests;

import base.AppConstants;
import base.BasePage;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;
import utils.ExtentReportHelper;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;

public class BaseTest {

    protected WebDriver driver;
    protected String browser;
    private ChromeOptions chromeOptions;
    private FirefoxOptions firefoxOptions;

    protected static ThreadLocal<ExtentTest> testLogger = new ThreadLocal<>();
    private static final ExtentReports reports = ExtentReportHelper.getReportObject();
    private static final Logger logger = LogManager.getLogger(BasePage.class);

    @BeforeMethod
    public void setUp(ITestResult iTestResult) {
        browser = AppConstants.browsername;
        firefoxOptions = new FirefoxOptions();
        chromeOptions = new ChromeOptions();
        if (browser.equalsIgnoreCase("chrome")) {
            if (AppConstants.platform.equalsIgnoreCase("local")) {
                logger.info("Browser name: Chrome");
//                co.addArguments("--remote-allow-origins=*");
                WebDriverManager.chromedriver().setup();
//                driver = new ChromeDriver(co);
                driver = new ChromeDriver();
            } else if (AppConstants.platform.equalsIgnoreCase("remote")) {
                chromeOptions.setPlatformName("linux");
                chromeOptions.setPageLoadStrategy(PageLoadStrategy.EAGER);
                try {
//                    driver = new RemoteWebDriver(new URL("http://localhost:4444"), chromeOptions);
                    driver = new RemoteWebDriver(new URL("http://172.19.0.1:4444"), chromeOptions);
                } catch (MalformedURLException e) {
                    throw new RuntimeException(e);
                }
            } else if(AppConstants.platform.equalsIgnoreCase("remote_git")){
                chromeOptions.addArguments("--headless");
                chromeOptions.addArguments("--disable-gpu");
                chromeOptions.addArguments("--no-sandbox");
                chromeOptions.addArguments("--remote-allow-origins=*");
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver(chromeOptions);
            }
            else {
                logger.error("Platform is not supported");
            }
        } else if (browser.equalsIgnoreCase("firefox")) {
            if (AppConstants.platform.equalsIgnoreCase("local")) {
                logger.info("Browser name: Firefox");
//                fo.addArguments("--remote-allow-origins=*");
                WebDriverManager.firefoxdriver().setup();
//                driver = new FirefoxDriver(fo);
                driver = new FirefoxDriver();
            } else if (AppConstants.platform.equalsIgnoreCase("remote")) {
                firefoxOptions.setPlatformName("linux");
                firefoxOptions.setPageLoadStrategy(PageLoadStrategy.EAGER);
                try {
//                    driver = new RemoteWebDriver(new URL("http://localhost:4444"), firefoxOptions);
                    driver = new RemoteWebDriver(new URL("http://172.19.0.1:4444"), firefoxOptions);
                } catch (MalformedURLException e) {
                    throw new RuntimeException(e);
                }
            } else {
                logger.error("Platform is not supported");
            }
        } else {
            System.out.println("Browser name entered is not supported!");
        }

        driver.manage().window().maximize();

        ExtentTest test = reports.createTest(iTestResult.getMethod().getMethodName());
        testLogger.set(test);
        testLogger.get().log(Status.INFO, AppConstants.platform.toUpperCase() + " WebDriver");
        testLogger.get().log(Status.INFO, "Driver start time: " + LocalDateTime.now());
    }

    @AfterMethod
    public void tearDown(ITestResult iTestResult) {
        if (iTestResult.isSuccess()) {
            testLogger.get().log(Status.PASS, MarkupHelper.createLabel(iTestResult.getMethod().getMethodName() + " is successful", ExtentColor.GREEN));
            logger.info("test passed");
        } else {
            testLogger.get().log(Status.FAIL, "Test failed due to: " + iTestResult.getThrowable());
            String screenshot = BasePage.getScreenshot(iTestResult.getMethod().getMethodName() + ".jpg", driver);
            testLogger.get().fail(MediaEntityBuilder.createScreenCaptureFromBase64String(BasePage.convertImg_Base64(screenshot)).build());
            logger.info("test failed");
        }
        testLogger.get().log(Status.INFO, "Driver End Time: " + LocalDateTime.now());
        driver.quit();
    }

    @AfterClass
    public void reportFlush() {
        logger.info("Extent report generated");
        reports.flush();
    }
}
