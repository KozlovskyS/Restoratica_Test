package page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;

public class AdminPage {
    private final SelenideElement headingAdminPage = $(withText("Добро пожаловать на портал управления"));
    private final SelenideElement incomingRequestsButton = $(withText("Входящие заявки"));
    private final SelenideElement bookingsButton = $(withText("Бронирования"));
    private final SelenideElement waitingListButton = $(withText("Лист ожидания"));
    private final SelenideElement clientBaseButton = $(withText("База клиентов"));
    private final SelenideElement menuButton = $(withText("Меню"));
    private final SelenideElement analyticButton = $(withText("Аналитика"));

    public void AdminPage() {
        headingAdminPage.shouldBe(visible);
    }

    public void adminPageIsVisible() {
        headingAdminPage.shouldBe(visible);
    }

    public void choiceIncomingRequest() {
        incomingRequestsButton.click();
    }
}
