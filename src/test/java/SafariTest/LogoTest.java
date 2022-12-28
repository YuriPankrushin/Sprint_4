package SafariTest;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.safari.SafariDriver;
import sprint4.CustomerPage;
import sprint4.MainPage;

import static org.junit.Assert.assertTrue;
import static sprint4.MainPage.orderButtonTop;

public class LogoTest {

    private WebDriver driver;

    @Before
    public void startUp() {
        driver = new SafariDriver();
        //Открываем страницу
        driver.get("https://qa-scooter.praktikum-services.ru/");
    }

    @Test
    public void checkSamokatLogoFunction(){
        //Нажимаем на кнопку согласия с куки
        MainPage mainPage = new MainPage(driver);
        mainPage.closeCookieWarning();

        //Нажимаем на кнопку заказа, чтобы перейти на страницу заказа
        mainPage.clickOrderButton(orderButtonTop);
        CustomerPage customerPage = new CustomerPage(driver);

        assertTrue("Страница 'Для кого самокат' должна была открыться", customerPage.returnHeader().size() != 0);

        //Нажимаем на локо Самокат
        mainPage.pressSamokatLogo();

        //Проверяем, что мы на главной странице (должна быть видна картинка самоката)
        assertTrue("Должна быть показана главная страница", mainPage.checkThatScooterPicDisplayed());
    }


    @Test
    public void checkYandexLogoFunction(){
        //Нажимаем на кнопку согласия с куки
        MainPage mainPage = new MainPage(driver);
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
