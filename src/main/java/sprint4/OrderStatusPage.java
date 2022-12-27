package sprint4;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class OrderStatusPage extends AbstractPage {

    private final WebDriver driver;

    public OrderStatusPage(WebDriver driver){
        super(driver);
        this.driver = driver;
    }

    private final By orderNotFound = By.xpath(".//img[@alt='Not found']");

    public List<WebElement> returnOrderNotFoundPic() {
//        new WebDriverWait(driver, 3)
//                .until(ExpectedConditions.visibilityOfElementLocated(orderNotFound));
        return driver.findElements(orderNotFound);
    }
}