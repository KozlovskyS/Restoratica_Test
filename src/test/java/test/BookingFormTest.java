package test;

import com.codeborne.selenide.logevents.SelenideLogger;
import data.DataHelper;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import page.MainPage;

import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.*;
import static data.DataHelper.*;

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
        closeWebDriver();
    }

    @Test
    void successBooking() {                       // успешная отправка заявки с валидными данными
        MainPage mainPage = new MainPage();
        DataHelper dataHelper = new DataHelper();
        var bookingFormPage = mainPage.choiceReservation();
        switchTo().frame($("iframe"));

        bookingFormPage.setName("Тестовое Имя");
        bookingFormPage.setPhone("9991112233");
        bookingFormPage.setGuest("4");
        bookingFormPage.setWishes("очень много пожеланий werv vghhh 2345 %^^^");
        bookingFormPage.setDate(1); // установить дату на Х дней вперед
        targetTime = DataHelper.generateRandomTime(10,0,23,30); //выбрать рандомное время из диапазона
        //targetTime = DataHelper.setOffsetTime(-2); //выбрать время текущее + Х
        DataHelper.getTargetTime(targetTime);
        bookingFormPage.setTime(targetHour, targetMinute);
        bookingFormPage.setCheckBox();
        bookingFormPage.verifyCheckBox();
        bookingFormPage.clickSendButton();
        bookingFormPage.verifyAlertSuccess();
        switchTo().defaultContent();
    }
}
