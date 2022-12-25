package sprint4;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class RentInfoPage extends AbstractPage {

    private WebDriver driver;

    public RentInfoPage(WebDriver driver){
        super(driver);
        this.driver = driver;
    }

    private By pageHeader = By.xpath(".//div[text()='Про аренду']");
    private By rentTimeField = By.xpath(".//div[contains(text(), 'Срок аренды')]");

    private By orderConfirmModal = By.xpath(".//div[contains(@class, 'ModalHeader')]/parent::div");
    private By orderConfirmModalHeader = By.xpath(".//div[text()='Хотите оформить заказ?']");

    private By orderButton = By.xpath(".//div[contains(@class, 'Order')]/button[text()='Заказать']");
    private By yesButton = By.xpath(".//button[text()='Да']");

    private By orderConfirmed = By.xpath(".//div[text()='Заказ оформлен']");

    public By getInputFieldByParam(String param) {
        return By.xpath(String.format(".//input[contains(@placeholder, '%s')]", param));
    }

    public boolean observeHeader() {
        return driver.findElement(pageHeader).isDisplayed();
    }

    public void chooseDateOfDelivery(String dayMonth) {
        driver.findElement(getInputFieldByParam("Когда привезти самокат")).click();
        driver.findElement(By.xpath(String.format(".//div[contains(@aria-label, '%s')]", dayMonth))).click();
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

    public boolean checkModalAppeared() {
        return driver.findElement(orderConfirmModalHeader).isDisplayed();
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

    public boolean checkThatOrderConfirmed() {
        return driver.findElement(orderConfirmed).isDisplayed();
    }
}
