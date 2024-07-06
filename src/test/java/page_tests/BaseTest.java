package page_tests;

import base.AppConstants;
import base.BasePage;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.*;
import utils.ExtentReportHelper;

import java.time.Duration;
import java.time.LocalDateTime;

public class BaseTest {

    protected WebDriver driver;
    protected String browser;
    private ChromeOptions co;
    private FirefoxOptions fo;

    protected static ThreadLocal<ExtentTest> testLogger = new ThreadLocal<>();
    private static final ExtentReports reports = ExtentReportHelper.getReportObject();

    @BeforeMethod
    public void setUp(ITestResult iTestResult) {
        browser = AppConstants.browsername;
        fo = new FirefoxOptions();
        co = new ChromeOptions();
        if (browser.equalsIgnoreCase("chrome")) {
            if (AppConstants.platform.equalsIgnoreCase("local")) {
                co.addArguments("--remote-allow-origins=*");
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver(co);
            }
        } else if (browser.equalsIgnoreCase("firefox")) {
            if (AppConstants.platform.equalsIgnoreCase("local")) {
                fo.addArguments("--remote-allow-origins=*");
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver(fo);
            }
        }else{
            System.out.println("Browser name entered is not supported!");
        }

        driver.manage().window().maximize();

        ExtentTest test = reports.createTest(iTestResult.getMethod().getMethodName());
        testLogger.set(test);
        testLogger.get().log(Status.INFO, "Driver start time: " + LocalDateTime.now());
    }

    @AfterMethod
    public void tearDown(ITestResult iTestResult){
        if(iTestResult.isSuccess()){
            testLogger.get().log(Status.PASS, MarkupHelper.createLabel(iTestResult.getMethod().getMethodName() + " is successful", ExtentColor.GREEN));
        }else{
            testLogger.get().log(Status.FAIL, "Test failed due to: " + iTestResult.getThrowable());
            String screenshot = BasePage.getScreenshot(iTestResult.getMethod().getMethodName()+".jpg", driver);
            testLogger.get().fail( MediaEntityBuilder.createScreenCaptureFromBase64String(BasePage.convertImg_Base64(screenshot)).build());

        }
        testLogger.get().log(Status.INFO, "Driver End Time: " + LocalDateTime.now());
        driver.quit();
    }

    @AfterClass
    public void reportFlush(){
        reports.flush();
    }
}
