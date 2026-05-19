package test;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.logevents.SelenideLogger;
import data.DataHelper;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Description;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import page.AdminPage;
import page.LoginPage;
import page.MainPage;

import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;

public class AdminPageTest {

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
            closeWebDriver();
        }

    @Test
    @DisplayName("Переход на страницу Входящие заявки")
    @Description("Переход на страницу Входящие заявки")
    void successBooking() {                       // успешная отправка заявки с валидными данными
        MainPage mainPage = new MainPage();
        LoginPage loginPage = mainPage.choiceAdmin();
        AdminPage adminPage = loginPage.successLogIn();

        adminPage.choiceIncomingRequest();
        $(withText("Входящие заявки")).shouldBe(Condition.visible);
    }
}
