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
public class CreateOrderTest {
    private final String customerName;
    private final String customerLastName;
    private final String customerAddress;
    private final String customerPhone;
    private final String dateOfDelivery;
    private final String rentTime;
    private final String scooterColor;
    private final String commentForCourier;
    private final boolean customerInfoPageWasOpened;
    private final boolean rentInfoPageWasOpened;
    private final boolean confirmModalAppeared;
    private final boolean orderConfirmed;
    private WebDriver driver;

    public CreateOrderTest(String customerName, String customerLastName, String customerAddress, String customerPhone,
                           String dateOfDelivery, String rentTime, String scooterColor, String commentForCourier,
                           boolean customerInfoPageWasOpened, boolean rentInfoPageWasOpened,
                           boolean confirmModalAppeared, boolean orderConfirmed) {
        this.customerName = customerName;
        this.customerLastName = customerLastName;
        this.customerAddress = customerAddress;
        this.customerPhone = customerPhone;
        this.dateOfDelivery = dateOfDelivery;
        this.rentTime = rentTime;
        this.scooterColor = scooterColor;
        this.commentForCourier = commentForCourier;
        this.customerInfoPageWasOpened = customerInfoPageWasOpened;
        this.rentInfoPageWasOpened = rentInfoPageWasOpened;
        this.confirmModalAppeared = confirmModalAppeared;
        this.orderConfirmed = orderConfirmed;
    }

    @Parameterized.Parameters
    public static Object[][] getTestData() {
        return new Object[][] {
                {"Юрий", "Панкрушин", "Москва", "89267596044", "31-е декабря", "двое суток", "чёрный жемчуг", "Комментарий для курьера", true, true, true, true},
                {"Юрий", "Панкрушин", "Москва, ул Шарикоподшипниковая улица, 1", "89267596044", "1-е января", "семеро суток", "серая безысходность", "Комментарий для курьера", true, true, true, true},
                {"Ю", "П", "М", "8926", "31-е декабря", "двое суток", "чёрный жемчуг", "Комментарий для курьера", true, false, false, false},
                {"Юрий", "Панкрушин", "Москва", "89267596044", "31-е декабря", "2 суток", "серая безысходность", "Комментарий для курьера", true, true, false, false},
                {"ЮрийЮрийЮрийЮрийЮрийЮрийЮрийЮрийЮрий", "ПанкрушинПанкрушинПанкрушинПанкрушинПанкрушинПанкрушинПанкрушинПанкрушинПанкрушин", "МоскваМоскваМоскваМоскваМоскваМоскваМосква", "8926759604489267596044892675960448926759604489267596044", "31-е декабря", "двое суток", "чёрный жемчуг", "Не звонить в дверь!Не звонить в дверь!Не звонить в дверь!Не звонить в дверь!Не звонить в дверь!Не звонить в дверь!Не звонить в дверь!Не звонить в дверь!Не звонить в дверь!Не звонить в дверь!", true, false, false, false},
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
        assertEquals("Страница 'Для кого самокат' должна была открыться", customerInfoPageWasOpened, customerPage.observeHeader());

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
        assertEquals("Страница 'Про аренду' должна была открыться",rentInfoPageWasOpened, rentInfoPage.observeHeader());

        //Заполняем данные из параметров
        rentInfoPage.chooseDateOfDelivery(dateOfDelivery);
        rentInfoPage.chooseRentTime(rentTime);
        rentInfoPage.selectScooterColor(scooterColor);
        rentInfoPage.leaveTheCommentForCourier(commentForCourier);

        //Нажимаем кнопку Заказать
        rentInfoPage.pressOrderButton();

        //Проверяем, что открылось окно с просьбой подтвердить заказ
        assertEquals("Отображается модальное окно подтверждения заказа", confirmModalAppeared, rentInfoPage.checkModalAppeared());
        rentInfoPage.checkModalHeaderIsCorrect();

        //Нажимаем кнопку Да и проверяем, что отобразилось окно с подтверждением заказа
        rentInfoPage.pressYesButton();
        assertEquals("Отображается подтверждение заказа", orderConfirmed, rentInfoPage.checkThatOrderConfirmed());
    }


    @Test
    public void makeOrderWithBottomButton() {
        driver = new SafariDriver();
        driver.get("https://qa-scooter.praktikum-services.ru/");

        MainPage mainPage = new MainPage(driver);

        //Нажимаем на кнопку согласия с куки
        mainPage.closeCookieWarning();

        mainPage.clickTopOrderButton();

        CustomerPage customerPage = new CustomerPage(driver);

        //Проверяем, что открылась форма "Для кого самокат"
        assertEquals("Страница 'Для кого самокат' должна была открыться", customerInfoPageWasOpened, customerPage.observeHeader());

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
        assertEquals("Страница 'Про аренду' должна была открыться",rentInfoPageWasOpened, rentInfoPage.observeHeader());

        //Заполняем данные из параметров
        rentInfoPage.chooseDateOfDelivery(dateOfDelivery);
        rentInfoPage.chooseRentTime(rentTime);
        rentInfoPage.selectScooterColor(scooterColor);
        rentInfoPage.leaveTheCommentForCourier(commentForCourier);

        //Нажимаем кнопку Заказать
        rentInfoPage.pressOrderButton();

        //Проверяем, что открылось окно с просьбой подтвердить заказ
        assertEquals("Отображается модальное окно подтверждения заказа", confirmModalAppeared, rentInfoPage.checkModalAppeared());
        rentInfoPage.checkModalHeaderIsCorrect();

        //Нажимаем кнопку Да и проверяем, что отобразилось окно с подтверждением заказа
        rentInfoPage.pressYesButton();
        assertEquals("Отображается подтверждение заказа", orderConfirmed, rentInfoPage.checkThatOrderConfirmed());
    }

    @After
    public void teardown() {
        driver.quit();
    }
}
