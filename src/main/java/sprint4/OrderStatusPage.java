package sprint4;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class OrderStatusPage extends AbstractPage {

    private WebDriver driver;

    public OrderStatusPage(WebDriver driver){
        super(driver);
        this.driver = driver;
    }

    private final By orderNotFound = By.xpath(".//img[@alt='Not found']");

    public boolean checkThatOrderNotFoundPageDisplayed() {
        new WebDriverWait(driver, 2)
                .until(ExpectedConditions.visibilityOfElementLocated(orderNotFound));
        System.out.println(driver.findElement(orderNotFound).isDisplayed());
        return driver.findElement(orderNotFound).isDisplayed();
    }
}