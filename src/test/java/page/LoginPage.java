package page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class LoginPage {
    private final SelenideElement headingLoginPage = $$("h2").findBy(Condition.text("Авторизация"));
    private final SelenideElement loginField = $("input[name='username']");
    private final SelenideElement passwordField = $("input[name='password']");
    private final SelenideElement enterButton = $$("button").findBy(text("Войти"));

    public void LoginPage() {
        headingLoginPage.shouldBe(visible);
    }

    public AdminPage successLogIn() {                       // успешная авторизация
        MainPage mainPage = new MainPage();
        //var loginPage = mainPage.choiceAdmin();
        setLogin(" ");
        setPassword(" ");
        clickEnterButton();

        return new AdminPage();
    }

    public void setLogin(String login) {
        loginField.shouldBe(visible).sendKeys(login);
    }

    public void setPassword(String password) {
        passwordField.shouldBe(visible).sendKeys(password);
    }

    public AdminPage clickEnterButton() {
        enterButton.shouldBe(visible).click();
        return new AdminPage();
    }


}
