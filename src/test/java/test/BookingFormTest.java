package test;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.logevents.SelenideLogger;
import data.DataHelper;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import page.MainPage;

import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.*;
import static data.Data.*;
import static data.DataHelper.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BookingFormTest {

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
    @DisplayName("Отправка заявки с валидными данными")
    @Description("Успешная отправка с валидными данными")
    void successBooking() {                       // успешная отправка заявки с валидными данными
        MainPage mainPage = new MainPage();
        DataHelper dataHelper = new DataHelper();
        var bookingFormPage = mainPage.choiceReservation();
        switchTo().frame($("iframe"));

        bookingFormPage.inputValidData();
        bookingFormPage.clickSendButton();
        bookingFormPage.verifyAlertSuccess();
//        switchTo().defaultContent();
    }

    @Test
    @DisplayName("Отправка заявки с пустым полем имени")
    @Description("Неуспешная попытка отправки заявки без имени клиента")
    void testEmptyNameField() {
        MainPage mainPage = new MainPage();
        DataHelper dataHelper = new DataHelper();
        var bookingFormPage = mainPage.choiceReservation();
        switchTo().frame($("iframe"));

        bookingFormPage.inputValidData();
        bookingFormPage.setName(emptyField);
        bookingFormPage.isSendButtonDisabled();
        //bookingFormPage.clickSendButton();
        //bookingFormPage.verifyAlertSuccess();
        switchTo().defaultContent();
    }

    @Test
    @DisplayName("Отправка заявки с именем на латинице")
    @Description("Отправка заявки с именем на латинице")
    void testLatinName() {
        MainPage mainPage = new MainPage();
        DataHelper dataHelper = new DataHelper();
        var bookingFormPage = mainPage.choiceReservation();
        switchTo().frame($("iframe"));

        bookingFormPage.inputValidData();
        bookingFormPage.setName(DataHelper.generatePhrase("en", false, false, 8));
        //bookingFormPage.isSendButtonDisabled();
        bookingFormPage.clickSendButton();
        bookingFormPage.verifyAlertSuccess();
        switchTo().defaultContent();
    }

    @Test
    @DisplayName("Отправка заявки с именем с дефисом")
    @Description("Отправка заявки с именем с дефисом")
    void testNameWithHyphen() {
        MainPage mainPage = new MainPage();
        DataHelper dataHelper = new DataHelper();
        var bookingFormPage = mainPage.choiceReservation();
        switchTo().frame($("iframe"));

        bookingFormPage.inputValidData();
        bookingFormPage.setName("Анна-Мария");
        //bookingFormPage.isSendButtonDisabled();
        bookingFormPage.clickSendButton();
        bookingFormPage.verifyAlertSuccess();
        switchTo().defaultContent();
    }

    @Test
    @DisplayName("Отправка заявки с коротким именем")
    @Description("Отправка заявки с коротким именем из двух букв")
    void testNameTwoSymbols() {
        MainPage mainPage = new MainPage();
        DataHelper dataHelper = new DataHelper();
        var bookingFormPage = mainPage.choiceReservation();
        switchTo().frame($("iframe"));

        bookingFormPage.inputValidData();
        bookingFormPage.setName(DataHelper.generatePhrase("ru", false, false, 2));
        //bookingFormPage.isSendButtonDisabled();
        bookingFormPage.clickSendButton();
        bookingFormPage.verifyAlertSuccess();
        switchTo().defaultContent();
    }

    @Test
    @DisplayName("Отправка заявки с длинным именем ")
    @Description("Отправка заявки с  именем из более 30 символов")
    void testNameToLong() {
        MainPage mainPage = new MainPage();
        DataHelper dataHelper = new DataHelper();
        var bookingFormPage = mainPage.choiceReservation();
        switchTo().frame($("iframe"));

        bookingFormPage.inputValidData();
        bookingFormPage.setName(DataHelper.generatePhrase("ru", false, false, 31));

        bookingFormPage.isSendButtonDisabled();
        //bookingFormPage.clickSendButton();
        //bookingFormPage.verifyAlertSuccess();
        switchTo().defaultContent();
    }


    @Test
    @DisplayName("Отправка заявки с цифрами в имени")
    @Description("Отправка заявки с цифрами в имени")
    void testNameWithNumbers() {
        MainPage mainPage = new MainPage();
        DataHelper dataHelper = new DataHelper();
        var bookingFormPage = mainPage.choiceReservation();
        switchTo().frame($("iframe"));

        bookingFormPage.inputValidData();
        bookingFormPage.setName(DataHelper.generatePhrase("ru", false, true, 10));

        bookingFormPage.isSendButtonDisabled();
        //bookingFormPage.clickSendButton();
        //bookingFormPage.verifyAlertSuccess();
        switchTo().defaultContent();
    }

    @Test
    @DisplayName("Отправка заявки с спецсимволами в имени")
    @Description("Отправка заявки с спецсимволами в имени")
    void testNameWithSpecsymbols() {
        MainPage mainPage = new MainPage();
        DataHelper dataHelper = new DataHelper();
        var bookingFormPage = mainPage.choiceReservation();
        switchTo().frame($("iframe"));

        bookingFormPage.inputValidData();
        bookingFormPage.setName(DataHelper.generatePhrase("ru", true, false, 12));

        bookingFormPage.isSendButtonDisabled();
        //bookingFormPage.clickSendButton();
        //bookingFormPage.verifyAlertSuccess();
        switchTo().defaultContent();
    }

    @Test
    @DisplayName("Отправка заявки с именем из пробелов")
    @Description("Отправка заявки с именем из пробелов")
    void testNameSpace() {
        MainPage mainPage = new MainPage();
        DataHelper dataHelper = new DataHelper();
        var bookingFormPage = mainPage.choiceReservation();
        switchTo().frame($("iframe"));

        bookingFormPage.inputValidData();
        bookingFormPage.setName(" ");

        bookingFormPage.isSendButtonDisabled();
        //bookingFormPage.clickSendButton();
        //bookingFormPage.verifyAlertSuccess();
        switchTo().defaultContent();
    }

    @Test
    @DisplayName("Отправка заявки  без номера телефона")
    @Description("Отправка заявки с пустым полем номера телефона")
    void testPhoneIsEmpty() {
        MainPage mainPage = new MainPage();
        DataHelper dataHelper = new DataHelper();
        var bookingFormPage = mainPage.choiceReservation();
        switchTo().frame($("iframe"));

        bookingFormPage.inputValidData();
        bookingFormPage.setPhone("");

        bookingFormPage.isSendButtonDisabled();
        //bookingFormPage.clickSendButton();
        //bookingFormPage.verifyAlertSuccess();
        switchTo().defaultContent();
    }

    @Test
    @DisplayName("Буквы в номере телефона")
    @Description("Буквы в номере телефона")
    void testPhoneIsABC() {
        MainPage mainPage = new MainPage();
        DataHelper dataHelper = new DataHelper();
        var bookingFormPage = mainPage.choiceReservation();
        switchTo().frame($("iframe"));

        bookingFormPage.inputValidData();
        String phone = DataHelper.generatePhrase("en", false, false, 3);   //вводим символы
        bookingFormPage.setPhone(phone);
        String numberPhone = bookingFormPage.getPhone();
        assertFalse(numberPhone.contains(phone));
        switchTo().defaultContent();
    }

    @Test
    @DisplayName("Спецсимволы в номере телефона")
    @Description("Спецсимволы в номере телефона")
    void testPhoneIsSpecsymbols() {
        MainPage mainPage = new MainPage();
        DataHelper dataHelper = new DataHelper();
        var bookingFormPage = mainPage.choiceReservation();
        switchTo().frame($("iframe"));

        bookingFormPage.inputValidData();
        String phone = DataHelper.generatePhrase("", true, false, 4);
        bookingFormPage.setPhone(phone);
        String numberPhone = bookingFormPage.getPhone();
        assertFalse(numberPhone.contains(phone));
        switchTo().defaultContent();
    }

    @Test
    @DisplayName("Короткий номер телефона")
    @Description("Короткий номер телефона")
    void testPhoneIsShort() {
        MainPage mainPage = new MainPage();
        DataHelper dataHelper = new DataHelper();
        var bookingFormPage = mainPage.choiceReservation();
        switchTo().frame($("iframe"));

        bookingFormPage.inputValidData();
        String phone = DataHelper.generateRandomNumber(8);
        bookingFormPage.setPhone(phone);
        bookingFormPage.isSendButtonDisabled();
        //bookingFormPage.clickSendButton();
        //bookingFormPage.verifyAlertSuccess();
        switchTo().defaultContent();
    }

    @Test
    @DisplayName("Длинный номер телефона")
    @Description("Более 10 цифр в номере телефона")
    void testPhoneIsLong() {
        MainPage mainPage = new MainPage();
        DataHelper dataHelper = new DataHelper();
        var bookingFormPage = mainPage.choiceReservation();
        switchTo().frame($("iframe"));

        bookingFormPage.inputValidData();
        String phone = DataHelper.generateRandomNumber(10);
        bookingFormPage.setPhone(phone + "333");
        String numberPhone = bookingFormPage.getPhone().replaceAll("\\D", "");
        assertTrue(numberPhone.contains(phone));
        switchTo().defaultContent();
    }

    @Test
    @DisplayName("Пустое поле  даты")
    @Description("Неуспешная отправка - Пустое поле даты")
    void testDateFieldIsEmpty() {
        MainPage mainPage = new MainPage();
        DataHelper dataHelper = new DataHelper();
        var bookingFormPage = mainPage.choiceReservation();
        switchTo().frame($("iframe"));

        bookingFormPage.setName(DataHelper.generatePhrase("ru", false, false, 12));
        bookingFormPage.setPhone(DataHelper.generateRandomNumber(10));
        bookingFormPage.setGuest("4");
        bookingFormPage.setWishes(DataHelper.generatePhrase("ru", true, true, 35));
        //bookingFormPage.setDate(-2); // установить дату на Х дней вперед (- назад)
        targetTime = DataHelper.generateRandomTime(10, 0, 23, 30); //выбрать рандомное время из диапазона
        //targetTime = DataHelper.setOffsetTime(-2); //выбрать время текущее + Х
        DataHelper.getTargetTime(targetTime);
        bookingFormPage.setTime(targetHour, targetMinute);
        bookingFormPage.setCheckBox();
        bookingFormPage.verifyCheckBox();
        bookingFormPage.isSendButtonDisabled();
        //bookingFormPage.clickSendButton();
        //bookingFormPage.verifyAlertSuccess();
        switchTo().defaultContent();
    }

    @Test
    @DisplayName("Выбор даты в прошлом")
    @Description("Неуспешная отправка - Выбор даты из прошлого")
    void invalidDateSelection() {
        MainPage mainPage = new MainPage();
        DataHelper dataHelper = new DataHelper();
        var bookingFormPage = mainPage.choiceReservation();
        switchTo().frame($("iframe"));

        bookingFormPage.inputValidData();
        bookingFormPage.clearDateField();
        bookingFormPage.setDate(-2); // установить дату на Х дней вперед (- назад)

        bookingFormPage.isSendButtonDisabled();
        //bookingFormPage.clickSendButton();
        //bookingFormPage.verifyAlertSuccess();
        switchTo().defaultContent();
    }
    @Test
    @DisplayName("Пустое поле 'Количество гостей'")
    @Description("Неуспешная отправка - пустое поле количества гостей")
    void guestFieldIsEmpty () {
        MainPage mainPage = new MainPage();
        DataHelper dataHelper = new DataHelper();
        var bookingFormPage = mainPage.choiceReservation();
        switchTo().frame($("iframe"));

        bookingFormPage.inputValidData();
        bookingFormPage.setGuest(emptyField);

        bookingFormPage.isSendButtonDisabled();
        //bookingFormPage.clickSendButton();
        //bookingFormPage.verifyAlertSuccess();
        switchTo().defaultContent();
    }
    @Test
    @DisplayName("Количество гостей равно нулю")
    @Description("Неуспешная отправка - цифра 0 в поле количества гостей")
    void numberOfGuestIsZero () {
        MainPage mainPage = new MainPage();
        DataHelper dataHelper = new DataHelper();
        var bookingFormPage = mainPage.choiceReservation();
        switchTo().frame($("iframe"));

        bookingFormPage.inputValidData();
        bookingFormPage.setGuest("0");

        bookingFormPage.isSendButtonDisabled();
        //bookingFormPage.clickSendButton();
        //bookingFormPage.verifyAlertSuccess();
        switchTo().defaultContent();
    }
    @Test
    @DisplayName("Спецсимволы в поле 'Количество гостей'")
    @Description("Неуспешная отправка - спецсимволы в поле количества гостей")
    void specSymbolInGuestField () {
        MainPage mainPage = new MainPage();
        DataHelper dataHelper = new DataHelper();
        var bookingFormPage = mainPage.choiceReservation();
        switchTo().frame($("iframe"));

        bookingFormPage.inputValidData();
        String specText = DataHelper.generatePhrase("", true,false,4);
        bookingFormPage.setGuest(specText);

        bookingFormPage.isSendButtonDisabled();
        //bookingFormPage.clickSendButton();
        //bookingFormPage.verifyAlertSuccess();
        switchTo().defaultContent();
    }
    @Test
    @DisplayName("Буквы En в поле 'Количество гостей'")
    @Description("Неуспешная отправка - буквы в поле количества гостей")
    void lettersEnInGuestField () {
        MainPage mainPage = new MainPage();
        DataHelper dataHelper = new DataHelper();
        var bookingFormPage = mainPage.choiceReservation();
        switchTo().frame($("iframe"));

        bookingFormPage.inputValidData();
        String specText = DataHelper.generatePhrase("en", false,false,4);
        bookingFormPage.setGuest(specText);

        bookingFormPage.isSendButtonDisabled();
        //bookingFormPage.clickSendButton();
        //bookingFormPage.verifyAlertSuccess();
        switchTo().defaultContent();

    }
    @Test
    @DisplayName("Буквы Ru в поле 'Количество гостей'")
    @Description("Неуспешная отправка - буквы в поле количества гостей")
    void lettersRuInGuestField () {
        MainPage mainPage = new MainPage();
        DataHelper dataHelper = new DataHelper();
        var bookingFormPage = mainPage.choiceReservation();
        switchTo().frame($("iframe"));

        bookingFormPage.inputValidData();
        String specText = DataHelper.generatePhrase("ru", false, false, 4);
        bookingFormPage.setGuest(specText);

        bookingFormPage.isSendButtonDisabled();
        //bookingFormPage.clickSendButton();
        //bookingFormPage.verifyAlertSuccess();
        switchTo().defaultContent();
    }

    @Test
    @DisplayName("Отсутствие согласия на обработку данных")
    @Description("Неуспешная отправка - Отсутствие согласия")
    void testNoCheckBoxSelection() {
        MainPage mainPage = new MainPage();
        DataHelper dataHelper = new DataHelper();
        var bookingFormPage = mainPage.choiceReservation();
        switchTo().frame($("iframe"));

        bookingFormPage.inputValidData();
        bookingFormPage.setCheckBox();  //снять отметку
        //bookingFormPage.verifyCheckBox();
        bookingFormPage.isSendButtonDisabled();
        //bookingFormPage.clickSendButton();
        //bookingFormPage.verifyAlertSuccess();
        switchTo().defaultContent();
    }

    @Test
    @DisplayName("Проверка на XSS уязвимость")
    @Description("Отправка заявки с  XSS иньекцией в поле имени")
    void testXSSInjectToLoginField() {
        MainPage mainPage = new MainPage();
        DataHelper dataHelper = new DataHelper();
        var bookingFormPage = mainPage.choiceReservation();
        switchTo().frame($("iframe"));


        bookingFormPage.inputValidData();
        bookingFormPage.setName(xssInject);

        bookingFormPage.isSendButtonDisabled();
        //bookingFormPage.clickSendButton();
        //bookingFormPage.verifyAlertSuccess();
        switchTo().defaultContent();
    }

    @Test
    @DisplayName("Проверка на SQL уязвимость")
    @Description("Отправка заявки с  SQL иньекцией в поле имени")
    void testSQLInjectToLoginField() {
        MainPage mainPage = new MainPage();
        DataHelper dataHelper = new DataHelper();
        var bookingFormPage = mainPage.choiceReservation();
        switchTo().frame($("iframe"));

        bookingFormPage.inputValidData();
        bookingFormPage.setName(sqlInject);

        bookingFormPage.isSendButtonDisabled();
        //bookingFormPage.clickSendButton();
        //bookingFormPage.verifyAlertSuccess();
        switchTo().defaultContent();
    }
}
