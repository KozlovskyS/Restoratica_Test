package test;

import com.codeborne.selenide.logevents.SelenideLogger;
import data.DataHelper;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import page.MainPage;

import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.*;
import static data.DataHelper.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BookingFormTest {

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @BeforeEach
    public void setUp() {
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

        bookingFormPage.setName("Тестовое Имя");
        bookingFormPage.setPhone("9991112233");
        bookingFormPage.setGuest("4");
        bookingFormPage.setWishes("очень много пожеланий werv vghhh 2345 %^^^");
        bookingFormPage.setDate(1); // установить дату на Х дней вперед (- назад)
        targetTime = DataHelper.generateRandomTime(10, 0, 23, 30); //выбрать рандомное время из диапазона
        //targetTime = DataHelper.setOffsetTime(-2); //выбрать время текущее + Х
        DataHelper.getTargetTime(targetTime);
        bookingFormPage.setTime(targetHour, targetMinute);
        bookingFormPage.setCheckBox();
        bookingFormPage.verifyCheckBox();
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

        bookingFormPage.setName("");
        bookingFormPage.setPhone("9991112233");
        bookingFormPage.setGuest("4");
        bookingFormPage.setWishes("очень много пожеланий werv vghhh 2345 %^^^");
        bookingFormPage.setDate(2); // установить дату на Х дней вперед (- назад)
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
    @DisplayName("Отправка заявки с именем на латинице")
    @Description("Отправка заявки с именем на латинице")
    void testLatinName() {
        MainPage mainPage = new MainPage();
        DataHelper dataHelper = new DataHelper();
        var bookingFormPage = mainPage.choiceReservation();
        switchTo().frame($("iframe"));

        bookingFormPage.setName("Vasiliy");
        bookingFormPage.setPhone("9991112233");
        bookingFormPage.setGuest("4");
        bookingFormPage.setWishes("очень много пожеланий werv vghhh 2345 %^^^");
        bookingFormPage.setDate(2); // установить дату на Х дней вперед (- назад)
        targetTime = DataHelper.generateRandomTime(10, 0, 23, 30); //выбрать рандомное время из диапазона
        //targetTime = DataHelper.setOffsetTime(-2); //выбрать время текущее + Х
        DataHelper.getTargetTime(targetTime);
        bookingFormPage.setTime(targetHour, targetMinute);
        bookingFormPage.setCheckBox();
        bookingFormPage.verifyCheckBox();
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

        bookingFormPage.setName("Анна-Мария");
        bookingFormPage.setPhone("9991112233");
        bookingFormPage.setGuest("4");
        bookingFormPage.setWishes("очень много пожеланий werv vghhh 2345 %^^^");
        bookingFormPage.setDate(2); // установить дату на Х дней вперед (- назад)
        targetTime = DataHelper.generateRandomTime(10, 0, 23, 30); //выбрать рандомное время из диапазона
        //targetTime = DataHelper.setOffsetTime(-2); //выбрать время текущее + Х
        DataHelper.getTargetTime(targetTime);
        bookingFormPage.setTime(targetHour, targetMinute);
        bookingFormPage.setCheckBox();
        bookingFormPage.verifyCheckBox();
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

        bookingFormPage.setName("Ян");
        bookingFormPage.setPhone("9991112233");
        bookingFormPage.setGuest("4");
        bookingFormPage.setWishes("много пожеланий werv vghhh 2345 %^^^");
        bookingFormPage.setDate(2); // установить дату на Х дней вперед (- назад)
        targetTime = DataHelper.generateRandomTime(10, 0, 23, 30); //выбрать рандомное время из диапазона
        //targetTime = DataHelper.setOffsetTime(-2); //выбрать время текущее + Х
        DataHelper.getTargetTime(targetTime);
        bookingFormPage.setTime(targetHour, targetMinute);
        bookingFormPage.setCheckBox();
        bookingFormPage.verifyCheckBox();
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

        bookingFormPage.setName("ЙФоврариаь йцычсвакен кепаьтмрапвй QWe");
        bookingFormPage.setPhone("9991112233");
        bookingFormPage.setGuest("4");
        bookingFormPage.setWishes("много пожеланий werv vghhh 2345 %^^^");
        bookingFormPage.setDate(2); // установить дату на Х дней вперед (- назад)
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
    @DisplayName("Отправка заявки с цифрами в имени")
    @Description("Отправка заявки с цифрами в имени")
    void testNameWithNumbers() {
        MainPage mainPage = new MainPage();
        DataHelper dataHelper = new DataHelper();
        var bookingFormPage = mainPage.choiceReservation();
        switchTo().frame($("iframe"));

        bookingFormPage.setName("Ян");
        bookingFormPage.setPhone("9991112233");
        bookingFormPage.setGuest("4");
        bookingFormPage.setWishes("много пожеланий werv vghhh 2345 %^^^");
        bookingFormPage.setDate(2); // установить дату на Х дней вперед (- назад)
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
    @DisplayName("Отправка заявки с спецсимволами в имени")
    @Description("Отправка заявки с спецсимволами в имени")
    void testNameWithSpecsymbols() {
        MainPage mainPage = new MainPage();
        DataHelper dataHelper = new DataHelper();
        var bookingFormPage = mainPage.choiceReservation();
        switchTo().frame($("iframe"));

        bookingFormPage.setName("№;%::?");
        bookingFormPage.setPhone("9991112233");
        bookingFormPage.setGuest("4");
        bookingFormPage.setWishes("много пожеланий werv vghhh 2345 %^^^");
        bookingFormPage.setDate(2); // установить дату на Х дней вперед (- назад)
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
    @DisplayName("Отправка заявки с именем из пробелов")
    @Description("Отправка заявки с именем из пробелов")
    void testNameSpace() {
        MainPage mainPage = new MainPage();
        DataHelper dataHelper = new DataHelper();
        var bookingFormPage = mainPage.choiceReservation();
        switchTo().frame($("iframe"));

        bookingFormPage.setName(" ");
        bookingFormPage.setPhone("9991112233");
        bookingFormPage.setGuest("4");
        bookingFormPage.setWishes("много пожеланий werv vghhh 2345 %^^^");
        bookingFormPage.setDate(2); // установить дату на Х дней вперед (- назад)
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
    @DisplayName("Отправка заявки с без номера телефона")
    @Description("Отправка заявки с пустым полем номера телефона")
    void testPhoneIsEmpty() {
        MainPage mainPage = new MainPage();
        DataHelper dataHelper = new DataHelper();
        var bookingFormPage = mainPage.choiceReservation();
        switchTo().frame($("iframe"));

        bookingFormPage.setName("Телефона нет");
        bookingFormPage.setPhone("");
        bookingFormPage.setGuest("4");
        bookingFormPage.setWishes("много пожеланий werv vghhh 2345 %^^^");
        bookingFormPage.setDate(2); // установить дату на Х дней вперед (- назад)
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
    @DisplayName("Буквы в номере телефона")
    @Description("Буквы в номере телефона")
    void testPhoneIsABC() {
        MainPage mainPage = new MainPage();
        DataHelper dataHelper = new DataHelper();
        var bookingFormPage = mainPage.choiceReservation();
        switchTo().frame($("iframe"));

        bookingFormPage.setName("Телефон as");
        String phone = "as";   //вводим символы
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

        bookingFormPage.setName("Телефон #$");
        String phone = "#$";
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

        bookingFormPage.setName("Телефон котроткий");
        String phone = "999123";
        bookingFormPage.setPhone(phone);
        bookingFormPage.setGuest("4");
        bookingFormPage.setWishes("много пожеланий werv vghhh 2345 %^^^");
        bookingFormPage.setDate(2); // установить дату на Х дней вперед (- назад)
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
    @DisplayName("Длинный номер телефона")
    @Description("Более 10 цифр в номере телефона")
    void testPhoneIsLong() {
        MainPage mainPage = new MainPage();
        DataHelper dataHelper = new DataHelper();
        var bookingFormPage = mainPage.choiceReservation();
        switchTo().frame($("iframe"));

        bookingFormPage.setName("Телефон длинный");
        String phone = "9998885522";
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

        bookingFormPage.setName("Тестовое Имя");
        bookingFormPage.setPhone("9991112233");
        bookingFormPage.setGuest("4");
        bookingFormPage.setWishes("очень много пожеланий werv vghhh 2345 %^^^");
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

        bookingFormPage.setName("Тестовое Имя");
        bookingFormPage.setPhone("9991112233");
        bookingFormPage.setGuest("4");
        bookingFormPage.setWishes("очень много пожеланий werv vghhh 2345 %^^^");
        bookingFormPage.setDate(-2); // установить дату на Х дней вперед (- назад)
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
    @DisplayName("Отсутствие согласия на обработку данных")
    @Description("Неуспешная отправка - Отсутствие согласия")
    void testNoCheckBoxSelection() {
        MainPage mainPage = new MainPage();
        DataHelper dataHelper = new DataHelper();
        var bookingFormPage = mainPage.choiceReservation();
        switchTo().frame($("iframe"));

        bookingFormPage.setName("Имя Нет согласия");
        bookingFormPage.setPhone("9991112233");
        bookingFormPage.setGuest("4");
        bookingFormPage.setWishes("очень много пожеланий werv vghhh 2345 %^^^");
        bookingFormPage.setDate(1); // установить дату на Х дней вперед (- назад)
        targetTime = DataHelper.generateRandomTime(10, 0, 23, 30); //выбрать рандомное время из диапазона
        //targetTime = DataHelper.setOffsetTime(-2); //выбрать время текущее + Х
        DataHelper.getTargetTime(targetTime);
        bookingFormPage.setTime(targetHour, targetMinute);
        bookingFormPage.setCheckBox(); //поставить отметку
        bookingFormPage.setCheckBox();  //снять отметку
        //bookingFormPage.verifyCheckBox();
        bookingFormPage.isSendButtonDisabled();
        //bookingFormPage.clickSendButton();
        //bookingFormPage.verifyAlertSuccess();
        switchTo().defaultContent();
    }
}
