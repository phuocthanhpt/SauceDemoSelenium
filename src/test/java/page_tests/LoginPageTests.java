package page_tests;

import lombok.SneakyThrows;
import org.testng.annotations.Test;
import page_objects.LoginPageObject;
import page_objects.ProductsPageObject;

public class LoginPageTests extends BaseTest{
    LoginPageObject loginPageObject;
    ProductsPageObject productsPageObject;

    @SneakyThrows
    @Test
    public void userLoginTest(){
        loginPageObject =new LoginPageObject(driver);
        productsPageObject = loginPageObject.userLogin("standard_user", "secret_sauce");
        Thread.sleep(5000);
    }
}
