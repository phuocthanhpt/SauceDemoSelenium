package base;

import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.util.Base64;

public class BasePage {

    @SneakyThrows
    public static String getScreenshot(String imageName, WebDriver driver) {
        TakesScreenshot ts = (TakesScreenshot) driver;
        File file = ts.getScreenshotAs(OutputType.FILE);
        String filePath = "./screenshot/" + imageName;
        FileUtils.copyFile(file, new File(filePath));

        return filePath;
    }

    public static String convertImg_Base64(String screenshotPath){
        byte[] file = new byte[0];
        try {
            file = FileUtils.readFileToByteArray(new File(screenshotPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String base64Img = Base64.getEncoder().encodeToString(file);

        return base64Img;
    }
}
