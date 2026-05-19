package test;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import data.DataHelper;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Description;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import page.AdminPage;
import page.MainPage;

import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.$;

public class LoginPageTest {
    @BeforeAll
    static void setUpAll() {
        //борьба за кодировку
        System.setProperty("file.encoding", "UTF-8");
        System.setProperty("sun.jnu.encoding", "UTF-8");
        System.setProperty("file.encoding", "UTF-8");
        System.setProperty("sun.jnu.encoding", "UTF-8");
        // Выводим текущие значения для проверки
        System.out.println("File encoding: " + System.getProperty("file.encoding"));
        System.out.println("Sun JNU encoding: " + System.getProperty("sun.jnu.encoding"));

        SelenideLogger.addListener("allure", new AllureSelenide());

    }

    @BeforeEach
    public void setUp() {
        // Автоматически подбирает и устанавливает chromedriver
        WebDriverManager.chromedriver().setup();
        // Настройки Selenide
        Configuration.browser = "chrome";
        Configuration.screenshots = true;
        Configuration.headless = false;
        //open("http://localhost:3000/");
        open("https://resto.skroy.ru/");
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @AfterEach
    void tearDown() {
        switchTo().defaultContent();
        closeWebDriver();
    }

    @Test
    @DisplayName("Успешная авторизация")
    @Description("Успешная авторизация")
    void successLogIn() {                       // успешная отправка заявки с валидными данными
        MainPage mainPage = new MainPage();
        DataHelper dataHelper = new DataHelper();
        var loginPage = mainPage.choiceAdmin();

        AdminPage adminPage = loginPage.successLogIn();
        adminPage.adminPageIsVisible();



    }
}
