import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import sprint4.CustomerPage;
import sprint4.MainPage;

import static org.junit.Assert.assertTrue;

public class LogoTest {

    private WebDriver driver;

    @Before
    public void startUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
    }

    @Test
    public void checkSamokatLogoFuction(){
        MainPage mainPage = new MainPage(driver);

        //Открываем страницу
        driver.get("https://qa-scooter.praktikum-services.ru/");

        //Нажимаем на кнопку согласия с куки
        mainPage.closeCookieWarning();

        //Нажимаем на кнопку заказа, чтобы перейти на страницу заказа
        mainPage.clickTopOrderButton();
        CustomerPage customerPage = new CustomerPage(driver);

        assertTrue("Страница 'Для кого самокат' должна была открыться", customerPage.observeHeader());

        //Нажимаем на локо Самокат
        mainPage.pressSamokatLogo();

        //Проверяем, что мы на главной странице (должна быть видна картинка самоката)
        assertTrue("Должна быть показана главная страница", mainPage.checkThatScooterPicDisplayed());
    }


    @Test
    public void checkYandexLogoFuction(){
        MainPage mainPage = new MainPage(driver);

        //Открываем страницу
        driver.get("https://qa-scooter.praktikum-services.ru/");

        //Нажимаем на кнопку согласия с куки
        mainPage.closeCookieWarning();

        //Нажимаем на кнопку заказа, чтобы перейти на страницу заказа
        mainPage.pressYandexLogo();

        //Переходим на открвшуюся страницу
        mainPage.switchToWindow(1);

        //Проверяем наличие логотипа Дзен
        assertTrue("Должна быть показана главная страница", mainPage.checkThatDzenPageDisplayed());
    }

    @After
    public void teardown() {
        driver.quit();
    }
}
