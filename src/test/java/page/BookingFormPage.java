package page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import java.net.HttpCookie;
import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.logevents.SelenideLogger.step;
import static java.time.temporal.ChronoUnit.SECONDS;
import static org.openqa.selenium.remote.tracing.EventAttribute.setValue;

public class BookingFormPage {
    private final SelenideElement heading = $(withText("Заявка на бронирование стола"));
    //private  final SelenideElement nameField = $$("label").findBy(text("Ваше имя")).parent().find("input");
    private final SelenideElement nameField = $("label")
            .shouldHave(text("Ваше имя"))  // ждём, пока label будет содержать текст
            .closest("div")          // поднимаемся до ближайшего родительского div
            .find("input");          // ищем input внутри этого div

    public void BookingFormPage() {
        heading.shouldBe(visible);
    }

    public void setName(String name) {
        nameField.shouldBe(visible, Duration.ofSeconds(10)).shouldBe(enabled).setValue(name);
    }


}
