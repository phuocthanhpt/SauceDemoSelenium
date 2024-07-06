package page_objects;

import generic_keywords.WebElementsInteractions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPageObject extends WebElementsInteractions {

    private final By userNameTextField = By.name("user-name");
    private final By passwordTextField = By.id("password");
    private final By loginBtn = By.id("login-button");

    public LoginPageObject(WebDriver driver){
        super(driver);
    }

    public ProductsPageObject userLogin(String userName, String password){
        goToApplication("https://www.saucedemo.com/");
        sendText(userNameTextField, userName);
        sendText(passwordTextField, password);
        clickElement(loginBtn);

        return new ProductsPageObject(driver);
    }
}
