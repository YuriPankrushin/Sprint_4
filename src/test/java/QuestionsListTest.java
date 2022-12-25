import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import sprint4.MainPage;

import static org.junit.Assert.assertEquals;

public class QuestionsListTest {
    private WebDriver driver;

    @Before
    public void startUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
    }

    @Test
    public void checkTextInQuestionsList() {
        MainPage mainPage = new MainPage(driver);

        //Открываем страницу
        driver.get("https://qa-scooter.praktikum-services.ru/");

        //Нажимаем на кнопку согласия с куки
        mainPage.closeCookieWarning();

        //Проматываем страницу до списка вопросов
        mainPage.scrollToQuestionsList();

        /** цикл по элементам списка вопросов,
         * проверка соответствия заголовков элементов (вопросов) и их описания (ответов) заданному тексту */
        for (int i = 0; i < mainPage.checkSizeOfTheList(); i++) {
            //Получаем текст вопроса
            String actualQuestionText = mainPage.getQuestionText(i);
            //Нажимаем на вопрос, чтобы открылся ответ
            mainPage.pressTheQuestion(i);
            //Сравниваем заданный текст вопроса с полученным текстом со страницы
            assertEquals("Текст вопроса в списке не соответствует ожидаемому", mainPage.getQuestionTextFromTestData(i), actualQuestionText);

            //Получаем текст ответа
            String actualAnswerText = mainPage.getAnswerText(i);
            //Сравниваем заданный текст ответа с полученным текстом со страницы
            assertEquals("Текст ответа на вопрос из списка не соответствует ожидаемому", mainPage.getAnswerTextFromTestData(i), actualAnswerText);
        }
    }

    @After
    public void teardown() {
        driver.quit();
    }
}