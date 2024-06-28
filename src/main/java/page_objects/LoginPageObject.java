package page_objects;

import generic_keywords.WebElementsInteractions;
import org.openqa.selenium.By;

public class LoginPageObject extends WebElementsInteractions {

    private final By userNameTextField = By.id("user-name");
    private final By passwordTextField = By.id("password");
    private final By loginBtn = By.id("login-button");

    public void userLogin(String userName, String password){
        sendText(userNameTextField, userName);
        sendText(passwordTextField, password);
        clickElement(loginBtn);
    }
}
