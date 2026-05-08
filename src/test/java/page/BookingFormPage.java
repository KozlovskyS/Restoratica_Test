package page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import data.DataHelper;

import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byClassName;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.files.DownloadActions.click;
import static data.DataHelper.*;

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
    private final SelenideElement phoneField = $$("label")
            .findBy(text("Номер телефона"))
            .closest("div")
            .find("input");

    private final SelenideElement guestField = $$("label")
            .findBy(text("Количество гостей"))
            .closest("div")
            .find("input");
    private final SelenideElement wishesField = $$("label")
            .findBy(text("Ваши Пожелания"))
            .closest("div")
            .find("textarea");
    private final SelenideElement dateField = $$("label")
            .findBy(text("Дата посещения"))
            .closest("div")
            .find("input");

    SelenideElement calendar = $(".dp__calendar");
    SelenideElement calendarHeader = $(".dp__month_year_wrap");
    private final SelenideElement timeField = $$("label")
            .findBy(text("Время посещения"))
            .closest("div")
            .find("input");
    private final SelenideElement incrementHourButton = $(".dp__inc_dec_button[aria-label='Increment hours']"); //увеличить часы
    private final SelenideElement decrementHourButton = $$(".dp__inc_dec_button").get(1); // [aria-label='aria-label'] //уменьшить часы
    private final SelenideElement incrementMinuteButton = $(".dp__inc_dec_button[aria-label='Increment minutes']");
    private final SelenideElement decrementMinuteButton = $(".dp__inc_dec_button[aria-label='Decrement minutes']");
    private final SelenideElement checkBox = $(".checkbox-wrapper");
    private final SelenideElement checkBoxChecked = $(".checked");

    private final SelenideElement buttonSend = $$("button").get(0);
    private final SelenideElement alertSuccessText = $$(".alert-item-text").findBy(text("Заявка успешно создана"));

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

    public void clickSendButton() {
        buttonSend.shouldBe(visible);
        buttonSend.shouldBe(enabled);
        buttonSend.click();
    }


    public void setDate(int daysToTarget) {
        DataHelper.setTargetDate(daysToTarget);
        dateField.shouldBe(visible);
        dateField.click();
        waitForCalendar();
        //String currentHeaderText = calendarHeader.getText();
        navigateToTargetMonthAndYear();
        selectDateInCalendar();
        verifyDateSelected(dateField);
    }

    private void waitForCalendar() {
        calendar.shouldBe(visible, Duration.ofSeconds(5));
    }

    private void clickNextMonthButton() {
        $$(".dp--arrow-btn-nav")
                .get(1)
                .click();
    }

    private void clickPreviousMonthButton() {
        $$(".dp--arrow-btn-nav")
                .get(0)
                .click();
    }

    private int parseCurrentMonth(String text) {
        String[] parts = text.split("\n"); //разделитель
        String monthName = parts[0].toLowerCase();

        switch (monthName) {
            case "янв.":
                return 1;
            case "февр.":
                return 2;
            case "март":
                return 3;
            case "апр.":
                return 4;
            case "май":
                return 5;
            case "июнь":
                return 6;
            case "июль":
                return 7;
            case "авг.":
                return 8;
            case "сент.":
                return 9;
            case "окт.":
                return 10;
            case "нояб.":
                return 11;
            case "дек.":
                return 12;
            default:
                return 1; // Заглушка
        }
    }

    private int parseCurrentYear(String text) {
        String[] parts = text.split("\n");
        return Integer.parseInt(parts[1]);
    }

    private void navigateToTargetMonthAndYear() {
        String currentHeaderText = calendarHeader.getText();
        int currentMonth = parseCurrentMonth(currentHeaderText);
        int currentYear = parseCurrentYear(currentHeaderText);

        while (currentYear < targetYear || (currentYear == targetYear && currentMonth < targetMonth)) {
            clickNextMonthButton();
            //updateCalendarState(calendarHeader);
            currentMonth = parseCurrentMonth(calendarHeader.getText());
            currentYear = parseCurrentYear(calendarHeader.getText());
        }

        while (currentYear > targetYear || (currentYear == targetYear && currentMonth > targetMonth)) {
            clickPreviousMonthButton();
            //updateCalendarState(calendarHeader);
            currentMonth = parseCurrentMonth(calendarHeader.getText());
            currentYear = parseCurrentYear(calendarHeader.getText());
        }
    }

    private void selectDateInCalendar() {
        $$(".dp__date_hover")
                .findBy(Condition.exactText(dayToSelect))
                .shouldBe(enabled)
                .click();
    }

    private void verifyDateSelected(SelenideElement dateField) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String expectedDateString = targetDate.format(formatter);

        dateField.shouldHave(value(expectedDateString), Duration.ofSeconds(5));
        // String reserveDate = dateField.getValue();
        System.out.println("Дата успешно выбрана: " + expectedDateString);
    }

    /**
     * Устанавка времени через стрелки увеличения/уменьшения
     *
     * @param targetHour   целевой час (0–23)
     * @param targetMinute целевая минута (0–59)
     */
    public void setTime(int targetHour, int targetMinute) {
        timeField.click();
        waitForTimePickerToAppear();
        incrementMinuteButton.click(); //активация отображения времени
        String currentTime = timeField.getValue().trim();
        // Устанавливаем часы
        adjustTimeComponent(incrementHourButton, decrementHourButton, getHour(currentTime), targetHour, 24);
        // Устанавливаем минуты
        adjustTimeComponent(incrementMinuteButton, decrementMinuteButton, getMinute(currentTime), targetMinute, 60);
        //String currentTime1 = timeField.getValue();

        verifyTimeSelected(timeField, targetTime.format(DateTimeFormatter.ofPattern("HH:mm")));

    }

    private void waitForTimePickerToAppear() {
        $(".dp__time_input")
                //.shouldHaveSizeGreaterThan(0)
                //.first()
                .shouldBe(visible, Duration.ofSeconds(10));
    }

    /**
     * Корректировка компонента времени (часы или минуты) до целевого значения
     */
    private void adjustTimeComponent(SelenideElement incrementButton,
                                     SelenideElement decrementButton,
                                     int currentValue,
                                     int targetValue,
                                     int range) {
        int steps = calculateSteps(currentValue, targetValue, range);

        if (steps > 0) {
            // Нужно увеличить значение
            for (int i = 0; i < steps; i++) {
                incrementButton.click();
            }
        } else if (steps < 0) {
            // Нужно уменьшить значение
            for (int i = 0; i < Math.abs(steps); i++) {
                decrementButton.click();
            }
        }
    }

    /**
     * Расчет количество шагов для достижения целевого значения
     */
    private int calculateSteps(int current, int target, int range) {
        // Валидация входных данных
        if (current < 0 || current >= range) {
            throw new IllegalArgumentException("Текущее значение должно быть в диапазоне 0–" + (range - 1));
        }
        if (target < 0 || target >= range) {
            throw new IllegalArgumentException("Целевое значение должно быть в диапазоне 0–" + (range - 1));
        }

        int forward = (target - current + range) % range;
        int backward = (current - target + range) % range;

        return forward <= backward ? forward : -backward;
    }

    /* считывание текущих значений */
    public static int getHour(String currentTime) {
        // Парсим текущий час из формата «ЧЧ:ММ»
        try {
            return Integer.parseInt(currentTime.split(":")[0]);
        } catch (Exception e) {
            return 12; // Значение по умолчанию при ошибке парсинга
        }
    }

    public static int getMinute(String currentTime) {
        try {
            return Integer.parseInt(currentTime.split(":")[1]);
        } catch (Exception e) {
            return 0; // Значение по умолчанию
        }
    }

    private void verifyTimeSelected(SelenideElement timeField, String expectedTime) {
        timeField.shouldHave(value(expectedTime), Duration.ofSeconds(5));
        System.out.println("Время успешно установлено: " + expectedTime);
    }

    public void setCheckBox() {
        checkBox.shouldBe(visible).click();
    }
    public void verifyCheckBox(){
        checkBoxChecked.shouldBe(visible);
    }
    public void verifyAlertSuccess() {
        alertSuccessText.shouldBe(visible, Duration.ofSeconds(3));
    }
}
