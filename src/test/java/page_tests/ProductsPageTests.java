package page_tests;

import org.apache.logging.log4j.LogManager;
import org.testng.Assert;
import org.testng.annotations.Test;
import page_objects.LoginPageObject;
import page_objects.ProductsPageObject;

import java.util.logging.Logger;

public class ProductsPageTests extends BaseTest{
    LoginPageObject loginPageObject;
    ProductsPageObject productsPageObject;

    @Test
    public void testItemName()
    {
        String username = "performance_glitch_user";
        String password = "secret_sauce";
        loginPageObject = new LoginPageObject(driver);
        productsPageObject =  loginPageObject.userLogin("performance_glitch_user", "secret_sauce");
        System.out.println(productsPageObject.getTitleOfPage());
        System.out.println(productsPageObject.getItemName());
    }
}
