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

    private  final SelenideElement nameField = $$("input")
            .findBy(Condition.attribute("placeholder","например, Иван"));

//    private final SelenideElement nameField = $("label")
//            .shouldHave(text("Ваше имя"))  // ждём, пока label будет содержать текст
//            .closest("div")          // поднимаемся до ближайшего родительского div
//            .find("input");          // ищем input внутри этого div

    //SelenideElement nameField = $("input [placeholder='например, Иван']");

    public void BookingFormPage() {
        heading.shouldBe(visible);
    }

    public void setName(String name) {
        nameField.shouldBe(visible, Duration.ofSeconds(10));
        nameField.sendKeys(name);
    }


}
