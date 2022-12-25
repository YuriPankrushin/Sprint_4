package sprint4;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class AbstractPage {
    private final WebDriver driver;

    public AbstractPage(WebDriver driver) {
        this.driver = driver;
    }


    //Лого Самокат
    private final By scooterlogo = By.xpath(".//img[@alt='Scooter']");

    //Картинка самоката (схема)
    private final By scooterPictureBluePrint = By.xpath(".//img[@alt='Scooter blueprint']/parent::div[contains(@class, 'BluePrint')]");

    //Лого Яндекс
    private final By yandexLogo = By.xpath(".//img[@alt='Yandex']");

    //Дзен логотоп
    private final By dzenLogo = By.xpath(".//span[@aria-label='Логотип Дзен']");

    public void pressSamokatLogo() {
        driver.findElement(scooterlogo).click();
    }

    public boolean checkThatScooterPicDisplayed(){
        return driver.findElement(scooterPictureBluePrint).isDisplayed();
    }

    public void pressYandexLogo() {
        driver.findElement(yandexLogo).click();
    }

    public boolean checkThatDzenPageDisplayed(){
        return driver.findElement(dzenLogo).isDisplayed();
    }

    public void switchToWindow(int numberWindow) {
        String handle = driver.getWindowHandles().toArray()[numberWindow]
                .toString();
        driver.switchTo().window(handle);
    }

}
