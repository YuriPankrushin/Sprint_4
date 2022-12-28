package SafariTest;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.safari.SafariDriver;
import sprint4.CustomerPage;
import sprint4.MainPage;
import sprint4.RentInfoPage;

import static org.junit.Assert.assertEquals;
import static sprint4.MainPage.orderButtonBottom;
import static sprint4.MainPage.orderButtonTop;

@RunWith(Parameterized.class)
public class CreateOrderWrongCustomerInfoNegativeTest {
    private final By orderButton;
    private final String customerName;
    private final String customerLastName;
    private final String customerAddress;
    private final String customerPhone;
    private final boolean customerInfoPageWasOpened;
    private final boolean rentInfoPageWasOpened;
    private WebDriver driver;

    public CreateOrderWrongCustomerInfoNegativeTest(By orderButton, String customerName, String customerLastName, String customerAddress, String customerPhone,
                                                    boolean customerInfoPageWasOpened, boolean rentInfoPageWasOpened) {
        this.orderButton = orderButton;
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
                {orderButtonTop, "Ю", "П", "М", "8926", true, false},
                {orderButtonTop, "Yuri", "Pankrushin", "Moscow", " ", true, false},
                {orderButtonTop, "ЮрийЮрийЮрийЮрийЮрийЮрийЮрийЮрийЮрий",
                        "ПанкрушинПанкрушинПанкрушинПанкрушинПанкрушинПанкрушинПанкрушинПанкрушинПанкрушин",
                        "МоскваМоскваМоскваМоскваМоскваМоскваМосква",
                        "8926759604489267596044892675960448926759604489267596044", true, false},
                {orderButtonBottom, "Ю", "П", "М", "8926", true, false},
                {orderButtonBottom, "Yuri", "Pankrushin", "Moscow", " ", true, false},
                {orderButtonBottom, "ЮрийЮрийЮрийЮрийЮрийЮрийЮрийЮрийЮрий",
                        "ПанкрушинПанкрушинПанкрушинПанкрушинПанкрушинПанкрушинПанкрушинПанкрушинПанкрушин",
                        "МоскваМоскваМоскваМоскваМоскваМоскваМосква",
                        "8926759604489267596044892675960448926759604489267596044", true, false},
        };
    }

    @Before
    public void startUp() {
        driver = new SafariDriver();
        //Открываем страницу
        driver.get("https://qa-scooter.praktikum-services.ru/");
    }

    @Test
    public void checkCustomerInfoValidation() {
        //Нажимаем на кнопку согласия с куки
        MainPage mainPage = new MainPage(driver);
        mainPage.closeCookieWarning();

        //Нажимаем кнопку Заказать
        mainPage.clickOrderButton(orderButton);

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
