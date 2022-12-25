package sprint4;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CustomerPage extends AbstractPage{
    private WebDriver driver;

    public CustomerPage(WebDriver driver){
        super(driver);
        this.driver = driver;
    }

    private By pageHeader = By.xpath(".//div[text()='Для кого самокат']");
    private By metroStation = By.xpath(".//button[@value='1']");

    private By buttonNext = By.xpath(".//button[text()='Далее']");

    public boolean observeHeader() {
        return driver.findElement(pageHeader).isDisplayed();
    }

    public By getInputFieldByParam(String param) {
        return By.xpath(String.format(".//input[contains(@placeholder, '%s')]", param));
    }

    public void sendDataToField(String field, String stringData) {
        driver.findElement(getInputFieldByParam(field)).sendKeys(stringData);
    }

    public void selectMetroStation() {
        driver.findElement(getInputFieldByParam("Станция метро")).click();
        driver.findElement(metroStation).click();
    }

    public void pressNextButton() {
        driver.findElement(buttonNext).click();
    }

}
