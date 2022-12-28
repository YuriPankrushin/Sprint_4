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
public class CreateOrderWrongRentInfoNegativeTest {

    private final By orderButton;
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

    public CreateOrderWrongRentInfoNegativeTest(By orderButton, String customerName, String customerLastName, String customerAddress, String customerPhone,
                                                String dateOfDelivery, String rentTime, String scooterColor, String commentForCourier,
                                                boolean customerInfoPageWasOpened, boolean rentInfoPageWasOpened,
                                                boolean confirmModalAppeared, boolean orderConfirmed) {
        this.orderButton = orderButton;
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
                {orderButtonTop, "Юрий", "Панкрушин", "Москва", "89267596044", "2021", "трое суток", "серая безысходность", "Комментарий для курьера", true, true, false, false}, // здесь год прошедший, должна сработать валидация
                {orderButtonTop, "Юрий", "Панкрушин", "Москва", "89267596044", "3021", "трое суток", "серая безысходность", "Комментарий для курьера", true, true, false, false}, // здесь год слишком большой, обычно на это тоже работает валидация
                {orderButtonTop, "Юрий", "Панкрушин", "Москва", "89267596044", "2023", "трое суток", "серая безысходность", "Комментарии Комментарии  Комментарии Комментарии " +
                        "Комментарии Комментарии Комментарии Комментарии Комментарии  Комментарии Комментарии Комментарии Комментарии Комментарии Комментарии " +
                        "Комментарии  Комментарии Комментарии Комментарии Комментарии Комментарии Комментарии Комментарии  Комментарии Комментарии Комментарии " +
                        "Комментарии Комментарии Комментарии Комментарии  Комментарии Комментарии Комментарии Комментарии Комментарии Комментарии Комментарии  " +
                        "Комментарии Комментарии Комментарии Комментарии Комментарии ", true, true, false, false}, // здесь слишком большой комментарий, должна показаться валидация
                {orderButtonBottom, "Юрий", "Панкрушин", "Москва", "89267596044", "2021", "трое суток", "серая безысходность", "Комментарий для курьера", true, true, false, false},
                {orderButtonBottom, "Юрий", "Панкрушин", "Москва", "89267596044", "3021", "трое суток", "серая безысходность", "Комментарий для курьера", true, true, false, false},
                {orderButtonBottom, "Юрий", "Панкрушин", "Москва", "89267596044", "2023", "трое суток", "серая безысходность", "Комментарии Комментарии  Комментарии Комментарии " +
                        "Комментарии Комментарии Комментарии Комментарии Комментарии  Комментарии Комментарии Комментарии Комментарии Комментарии Комментарии " +
                        "Комментарии  Комментарии Комментарии Комментарии Комментарии Комментарии Комментарии Комментарии  Комментарии Комментарии Комментарии " +
                        "Комментарии Комментарии Комментарии Комментарии  Комментарии Комментарии Комментарии Комментарии Комментарии Комментарии Комментарии  " +
                        "Комментарии Комментарии Комментарии Комментарии Комментарии ", true, true, false, false},
        };
    }

    @Before
    public void startUp() {
        driver = new SafariDriver();
        //Открываем страницу
        driver.get("https://qa-scooter.praktikum-services.ru/");
    }

    @Test
    public void checkRentInfoValidation() {
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
        //assertTrue("Страница 'Про аренду' должна была открыться", rentInfoPage.returnHeader().size() != 0);

        //Заполняем данные из параметров
        rentInfoPage.chooseDateOfDelivery(dateOfDelivery);
        rentInfoPage.chooseRentTime(rentTime);
        rentInfoPage.selectScooterColor(scooterColor);
        rentInfoPage.leaveTheCommentForCourier(commentForCourier);

        //Нажимаем кнопку Заказать
        rentInfoPage.pressOrderButton();

        //Проверяем, что открылось окно с просьбой подтвердить заказ
        assertEquals("Отображается модальное окно подтверждения заказа", confirmModalAppeared, rentInfoPage.returnModalAppeared().size() != 0);
        rentInfoPage.checkModalHeaderIsCorrect();

        //Нажимаем кнопку Да и проверяем, что отобразилось окно с подтверждением заказа
        rentInfoPage.pressYesButton();
        assertEquals("Отображается подтверждение заказа", orderConfirmed, rentInfoPage.returnOrderConfirmationText().size() != 0);
    }

    @After
    public void teardown() {
        driver.quit();
    }
}
