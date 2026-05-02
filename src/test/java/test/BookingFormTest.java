package test;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.collections.Texts;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import page.BookingFormPage;
import page.MainPage;

import static com.codeborne.selenide.Condition.text;
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
        var bookingFormPage= mainPage.choiceReservation();
        bookingFormPage.setName("kaka");

    }
}
