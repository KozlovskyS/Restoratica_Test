package page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byClassName;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;

public class BookingFormPage {
    private final SelenideElement heading = $(withText("Заявка на бронирование стола"));

//    private  final SelenideElement nameField = $$("input")
//            .findBy(Condition.attribute("placeholder","например, Иван"));

//    private  final SelenideElement telefoneField = $$("input")
//            .findBy(Condition.attribute("placeholder","Телефон"));

    private final SelenideElement nameField = $$("label")
            .findBy(text("Ваше имя"))  // ждём, пока label будет содержать текст
            .closest("div")          // поднимаемся до ближайшего родительского div
            .find("input");          // ищем input внутри этого div
    private  final SelenideElement phoneField = $$("label")
            .findBy(text("Номер телефона"))  // ждём, пока label будет содержать текст
            .closest("div")          // поднимаемся до ближайшего родительского div
            .find("input");          // ищем input внутри этого div

    private  final SelenideElement guestField = $$("label")
            .findBy(text("Количество гостей"))
            .closest("div")
            .find("input");
    private  final SelenideElement wishesField = $$("label")
            .findBy(text("Ваши Пожелания"))
            .closest("div")
            .find("textarea");
    private  final SelenideElement dateField = $$("label")
            .findBy(text("Дата посещения"))
            .closest("div")
            .find("input");
    private final SelenideElement buttonSend = $$("button").get(0);

    public void BookingFormPage() {
        heading.shouldBe(visible);
    }

    public void setName(String name) {
        nameField.shouldBe(visible, Duration.ofSeconds(10));
        nameField.sendKeys(name);
    }
    public void setPhone(String telNumber) {
        phoneField.shouldBe(visible, Duration.ofSeconds(10));
        phoneField.sendKeys(telNumber);
    }
    public void setGuest(String number) {
        guestField.shouldBe(visible, Duration.ofSeconds(10));
        guestField.sendKeys(number);
    }
    public void setWishes(String wishes) {
        wishesField.shouldBe(visible, Duration.ofSeconds(10));
        wishesField.sendKeys(wishes);
    }
    public void clickSend() {
        buttonSend.shouldBe(visible);
        buttonSend.shouldBe(enabled);
        buttonSend.click();
    }
    public void setDate(String date) {
        dateField.shouldBe(visible);
        dateField.click();
        dateField.setValue(date);
    }
}
