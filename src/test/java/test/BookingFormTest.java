package test;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.collections.Texts;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import page.BookingFormPage;
import page.MainPage;

import java.time.Duration;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

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

    @Test
    void successBooking() {                       // успешная отправка заявки
        MainPage mainPage = new MainPage();
        var bookingFormPage = mainPage.choiceReservation();
        switchTo().frame($("iframe"));

        bookingFormPage.setName("kakaka");
        bookingFormPage.setPhone("9991112233");
        bookingFormPage.setGuest("4");
        bookingFormPage.setWishes("werv vghhh 2345 %^^^");
        bookingFormPage.setDate("15.05.2026");
        bookingFormPage.clickSend();

        switchTo().defaultContent();
    }
}
