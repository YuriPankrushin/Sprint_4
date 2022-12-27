import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.safari.SafariDriver;
import sprint4.CustomerPage;
import sprint4.MainPage;
import sprint4.RentInfoPage;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class CreateOrderWrongCustomerInfoNegativeTest {
    private final String customerName;
    private final String customerLastName;
    private final String customerAddress;
    private final String customerPhone;
    private final boolean customerInfoPageWasOpened;
    private final boolean rentInfoPageWasOpened;
    private WebDriver driver;

    public CreateOrderWrongCustomerInfoNegativeTest(String customerName, String customerLastName, String customerAddress, String customerPhone,
                                   boolean customerInfoPageWasOpened, boolean rentInfoPageWasOpened) {
        this.customerName = customerName;
        this.customerLastName = customerLastName;
        this.customerAddress = customerAddress;
        this.customerPhone = customerPhone;
        this.customerInfoPageWasOpened = customerInfoPageWasOpened;
        this.rentInfoPageWasOpened = rentInfoPageWasOpened;
    }

    @Parameterized.Parameters
    public static Object[][] getTestData() {
        return new Object[][] {
                {"Ю", "П", "М", "8926", true, false},
                {"Yuri", "Pankrushin", "Moscow", " ", true, false},
                {"ЮрийЮрийЮрийЮрийЮрийЮрийЮрийЮрийЮрий",
                        "ПанкрушинПанкрушинПанкрушинПанкрушинПанкрушинПанкрушинПанкрушинПанкрушинПанкрушин",
                        "МоскваМоскваМоскваМоскваМоскваМоскваМосква",
                        "8926759604489267596044892675960448926759604489267596044", true, false},
        };
    }

    @Test
    public void makeOrderWithTopButton() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.get("https://qa-scooter.praktikum-services.ru/");

        MainPage mainPage = new MainPage(driver);

        //Нажимаем на кнопку согласия с куки
        mainPage.closeCookieWarning();

        //Нажимаем верхнюю кнопку Заказать
        mainPage.clickTopOrderButton();

        CustomerPage customerPage = new CustomerPage(driver);

        //Проверяем, что открылась форма "Для кого самокат"
        assertEquals("Страница 'Для кого самокат' должна была открыться", customerInfoPageWasOpened, customerPage.returnHeader().size() != 0);
        //Заполняем данные из параметров
        customerPage.sendDataToField("Имя", customerName);
        customerPage.sendDataToField("Фамилия", customerLastName);
        customerPage.sendDataToField("Адрес", customerAddress);
        customerPage.selectMetroStation();
        customerPage.sendDataToField("Телефон", customerPhone);

        //Нажимаем кнопку Далее
        customerPage.pressNextButton();

        //Проверяем, что открылась форма "Про аренду"
        RentInfoPage rentInfoPage = new RentInfoPage(driver);
        assertEquals("Страница 'Про аренду' должна была открыться", rentInfoPageWasOpened, rentInfoPage.returnHeader().size() != 0);
    }


    @Test
    public void makeOrderWithBottomButton() {
        driver = new SafariDriver();
        driver.get("https://qa-scooter.praktikum-services.ru/");

        MainPage mainPage = new MainPage(driver);

        //Нажимаем на кнопку согласия с куки
        mainPage.closeCookieWarning();

        //Нажимаем верхнюю кнопку Заказать
        mainPage.clickTopOrderButton();

        CustomerPage customerPage = new CustomerPage(driver);

        //Проверяем, что открылась форма "Для кого самокат"
        assertEquals("Страница 'Для кого самокат' должна была открыться", customerInfoPageWasOpened, customerPage.returnHeader().size() != 0);
        //Заполняем данные из параметров
        customerPage.sendDataToField("Имя", customerName);
        customerPage.sendDataToField("Фамилия", customerLastName);
        customerPage.sendDataToField("Адрес", customerAddress);
        customerPage.selectMetroStation();
        customerPage.sendDataToField("Телефон", customerPhone);

        //Нажимаем кнопку Далее
        customerPage.pressNextButton();

        //Проверяем, что открылась форма "Про аренду"
        RentInfoPage rentInfoPage = new RentInfoPage(driver);
        assertEquals("Страница 'Про аренду' должна была открыться", rentInfoPageWasOpened, rentInfoPage.returnHeader().size() != 0);
    }

    @After
    public void teardown() {
        driver.quit();
    }
}
