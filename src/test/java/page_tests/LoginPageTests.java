package page_tests;

import lombok.SneakyThrows;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;
import page_objects.LoginPageObject;
import page_objects.ProductsPageObject;

public class LoginPageTests extends BaseTest{
    LoginPageObject loginPageObject;
    ProductsPageObject productsPageObject;
    private static final Logger logger = LogManager.getLogger(LoginPageTests.class);

    @SneakyThrows
    @Test
    public void userLoginTest(){
        logger.info("Login test");
        loginPageObject =new LoginPageObject(driver);
        productsPageObject = loginPageObject.userLogin("standard_user", "secret_sauce");
        Thread.sleep(5000);
    }
}
