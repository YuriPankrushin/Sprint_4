package ChromeTest;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import sprint4.MainPage;
import sprint4.OrderStatusPage;

import static org.junit.Assert.assertEquals;


@RunWith(Parameterized.class)
public class WrongOrderStatusTest {

    private WebDriver driver;
    private final String orderNumber;
    private final boolean notFoundPageDisplayed;


    public WrongOrderStatusTest(String orderNumber, boolean notFoundPageDisplayed) {
        this.orderNumber = orderNumber;
        this.notFoundPageDisplayed = notFoundPageDisplayed;
    }

    @Parameterized.Parameters
    public static Object[][] getOrderNumber() {
        return new Object[][] {
                {"0", true},
                {"test", true},
                {"345860", false},
        };
    }

    @Before
    public void startUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        //Открываем страницу
        driver.get("https://qa-scooter.praktikum-services.ru/");
    }

    @Test
    public void checkThatWrongOrderPageDisplayed() {
        //Нажимаем на кнопку согласия с куки
        MainPage mainPage = new MainPage(driver);
        mainPage.closeCookieWarning();

        //Нажимаем на кнопку "Статус заказа"
        mainPage.pressOrderStatusButton();

        //Вводим номер заказа
        mainPage.checkOrderNumber(orderNumber);
        //Проверяем, что на странице отобразилась картинка с надрисью "Такого заказа нет"
        OrderStatusPage orderStatusPage = new OrderStatusPage(driver);
        assertEquals("Заказ должен отсутствовать", notFoundPageDisplayed, orderStatusPage.returnOrderNotFoundPic().size() != 0);
    }

    @After
    public void teardown() {
        driver.quit();
    }
}