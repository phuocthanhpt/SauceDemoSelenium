package page_objects;

import generic_keywords.WebElementsInteractions;
import org.openqa.selenium.By;

public class ProductsPageObject extends WebElementsInteractions {

    private final By getTitleOfProductPage = By.xpath("//span[contains(test(), 'Products'");

    public String getTitleOfPage(){
        return retrieveTextData(getTitleOfProductPage);
    }
}
