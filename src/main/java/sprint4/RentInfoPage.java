package sprint4;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class RentInfoPage extends AbstractPage {

    private final WebDriver driver;

    public RentInfoPage(WebDriver driver){
        super(driver);
        this.driver = driver;
    }

    private final By pageHeader = By.xpath(".//div[text()='Про аренду']");
    private final By rentTimeField = By.xpath(".//span[@class='Dropdown-arrow']");

    private final By orderConfirmModalHeader = By.xpath(".//div[text()='Хотите оформить заказ?']");

    private final By orderButton = By.xpath(".//div[contains(@class, 'Order')]/button[text()='Заказать']");
    private final By yesButton = By.xpath(".//button[text()='Да']");

    private final By orderConfirmed = By.xpath(".//div[text()='Заказ оформлен']");

    public By getInputFieldByParam(String param) {
        return By.xpath(String.format(".//input[contains(@placeholder, '%s')]", param));
    }

    public List<WebElement> returnHeader() {
        return driver.findElements(pageHeader);
    }

    public void chooseDateOfDelivery(String year) {
        new WebDriverWait(driver, 3)
                .until(ExpectedConditions.elementToBeClickable(getInputFieldByParam("Когда привезти самокат")));
        driver.findElement(getInputFieldByParam("Когда привезти самокат")).click();
        driver.findElement(getInputFieldByParam("Когда привезти самокат")).sendKeys(year);
    }

    public void chooseRentTime(String rentTime) {
        driver.findElement(rentTimeField).click();
        driver.findElement(By.xpath(String.format(".//div[@class='Dropdown-menu']/div[text()='%s']", rentTime))).click();
    }

    public void selectScooterColor(String color) {
        driver.findElement(By.xpath(String.format(".//label[contains(text(), '%s')]", color))).click();
    }

    public void leaveTheCommentForCourier(String comment) {
        driver.findElement(getInputFieldByParam("Комментарий")).sendKeys(comment);
    }

    public List<WebElement> returnModalAppeared() {
        return driver.findElements(orderConfirmModalHeader);
    }

    public void checkModalHeaderIsCorrect() {
        driver.findElement(orderConfirmModalHeader).isDisplayed();
    }

    public void pressOrderButton() {
        driver.findElement(orderButton).click();
    }

    public void pressYesButton() {
        driver.findElement(yesButton).click();
    }

    public List<WebElement> returnOrderConfirmationText() {
        return driver.findElements(orderConfirmed);
    }
}
