package sprint4;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Arrays;
import java.util.List;

public class MainPage extends AbstractPage {
    private final WebDriver driver;

    public MainPage(WebDriver driver){
        super(driver);
        this.driver = driver;
    }

    /** Локаторы для списка вопросов */
    //Заголовок списка вопросов
    private final By questionsListHeader = By.xpath(".//div[text()='Вопросы о важном']");

    //Вопрос из списка
    private final By questionsListItem = By.xpath(".//div[@class='accordion__item']");

    //Текст вопроса
    private final By questionText = By.xpath(".//div[@class='accordion__heading']");

    //Текст ответа
    private final By answerText = By.xpath(".//div[@class='accordion__panel']/p");

    //Кнопка "все привыкли"
    private final By closeCookieWarningButton = By.id("rcc-confirm-button");

    private final List<String> questionsList = Arrays.asList("Сколько это стоит? И как оплатить?",
            "Хочу сразу несколько самокатов! Так можно?", "Как рассчитывается время аренды?",
            "Можно ли заказать самокат прямо на сегодня?", "Можно ли продлить заказ или вернуть самокат раньше?",
            "Вы привозите зарядку вместе с самокатом?", "Можно ли отменить заказ?", "Я жизу за МКАДом, привезёте?");

    private final List<String> answersList = Arrays.asList("Сутки — 400 рублей. Оплата курьеру — наличными или картой.",
            "Пока что у нас так: один заказ — один самокат. Если хотите покататься с друзьями, можете просто сделать несколько заказов — один за другим.",
            "Допустим, вы оформляете заказ на 8 мая. Мы привозим самокат 8 мая в течение дня. Отсчёт времени аренды начинается с момента, когда вы оплатите заказ курьеру. Если мы привезли самокат 8 мая в 20:30, суточная аренда закончится 9 мая в 20:30.",
            "Только начиная с завтрашнего дня. Но скоро станем расторопнее.",
            "Пока что нет! Но если что-то срочное — всегда можно позвонить в поддержку по красивому номеру 1010.",
            "Самокат приезжает к вам с полной зарядкой. Этого хватает на восемь суток — даже если будете кататься без передышек и во сне. Зарядка не понадобится.",
            "Да, пока самокат не привезли. Штрафа не будет, объяснительной записки тоже не попросим. Все же свои.",
            "Да, обязательно. Всем самокатов! И Москве, и Московской области.");

    public void scrollToQuestionsList() {
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView();",driver.findElement(questionsListHeader));
    }

    public void closeCookieWarning() {
        new WebDriverWait(driver, 3)
                .until(ExpectedConditions.elementToBeClickable(closeCookieWarningButton));
        driver.findElement(closeCookieWarningButton).click();
    }

    public int checkSizeOfTheList() {
        return driver.findElements(questionsListItem).size();
    }

    public String getQuestionText(int i) {
        return driver.findElements(questionText).get(i).getAttribute("innerText");
    }

    public void pressTheQuestion(int i) {
        driver.findElements(questionText).get(i).click();
    }

    public String getAnswerText(int i) {
        return driver.findElements(answerText).get(i).getAttribute("innerText");
    }

    public String getQuestionTextFromTestData(int i){
        return questionsList.get(i);
    }

    public String getAnswerTextFromTestData(int i){
        return answersList.get(i);
    }

    /** Локаторы для создания заказа */

    //Кнопка Заказать в заголовке страницы
    public static By orderButtonTop = By.xpath(".//div[contains(@class, 'Header')]/button[text()='Заказать']");

    //Кнопка Заказать внизу страницы
    public static By orderButtonBottom = By.xpath(".//div[contains(@class, 'Finish')]/button[text()='Заказать']");

    public void clickOrderButton(By orderButton) {
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView();",driver.findElement(orderButton));
        new WebDriverWait(driver, 3)
                .until(ExpectedConditions.elementToBeClickable(orderButton));
        driver.findElement(orderButton).click();
    }

    /** Локаторы и методы для доп заданий */

    //Поле номера заказа
    private final By orderStatusButton = By.xpath(".//button[text()='Статус заказа']");
    private final By orderNumberField = By.xpath(".//input[@placeholder='Введите номер заказа']");
    private final By goButton = By.xpath(".//button[text()='Go!']");

    public void pressOrderStatusButton() {
        new WebDriverWait(driver, 3)
                .until(ExpectedConditions.elementToBeClickable(orderStatusButton));
        driver.findElement(orderStatusButton).click();
    }

    public void checkOrderNumber(String orderNumber) {
        new WebDriverWait(driver, 3)
                .until(ExpectedConditions.elementToBeClickable(orderNumberField));
        driver.findElement(orderNumberField).sendKeys(orderNumber);
        new WebDriverWait(driver, 3)
                .until(ExpectedConditions.elementToBeClickable(goButton));
        driver.findElement(goButton).click();
    }
}