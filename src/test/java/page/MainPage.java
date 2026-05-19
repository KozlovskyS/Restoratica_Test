package page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$$;

public class MainPage {
    private  final SelenideElement choiceReservationButton = $$("a").findBy(text("Создать заявку"));
    private  final SelenideElement choicePortalButton = $$("a").findBy(text("Войти в портал"));

    public BookingFormPage choiceReservation() {
        choiceReservationButton.click();
        return new BookingFormPage();
    }

    public LoginPage choiceAdmin() {
        choicePortalButton.click();
        return new LoginPage();
    }
}
