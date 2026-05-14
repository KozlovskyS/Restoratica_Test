package data;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import page.BookingFormPage;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class DataHelper {
    public static int targetYear;
    public static int targetMonth;
    public static LocalDate targetDate;
    public static String dayToSelect;
    public static LocalTime targetTime;
    public static int targetHour;
    public static int targetMinute;

    public static void setTargetDate(int dayToPlus) {
        // Целевая дата: + дней от текущей
        targetDate = LocalDate.now().plusDays(dayToPlus);
        dayToSelect = String.valueOf(targetDate.getDayOfMonth());
        targetMonth = targetDate.getMonthValue();
        targetYear = targetDate.getYear();
    }

    /**
     * Генерирует случайное время в диапазоне от minHour:minMinute до maxHour:maxMinute
     */
    public static LocalTime generateRandomTime(int minHour, int minMinute,
                                               int maxHour, int maxMinute) {
        // Проверяем корректность входных данных
        if (minHour < 0 || minHour > 23 || maxHour < 0 || maxHour > 23) {
            throw new IllegalArgumentException("Часы должны быть в диапазоне 0–23");
        }
        if (minMinute < 0 || minMinute > 59 || maxMinute < 0 || maxMinute > 59) {
            throw new IllegalArgumentException("Минуты должны быть в диапазоне 0–59");
        }
        // Преобразуем в секунды с начала дня для удобства расчётов
        int minSeconds = minHour * 3600 + minMinute * 60;
        int maxSeconds = maxHour * 3600 + maxMinute * 60;

        if (minSeconds > maxSeconds) {
            throw new IllegalArgumentException("Минимальное время не может быть больше максимального");
        }
        // Генерируем случайное количество секунд в диапазоне
        int randomSeconds = ThreadLocalRandom.current()
                .nextInt(minSeconds, maxSeconds + 1);

        return LocalTime.ofSecondOfDay(randomSeconds);
    }

    public static void getTargetTime(LocalTime time) {
        targetHour = BookingFormPage.getHour(time.format(DateTimeFormatter.ofPattern("HH:mm")));
        targetMinute = BookingFormPage.getMinute(time.format(DateTimeFormatter.ofPattern("HH:mm")));
    }

    public static LocalTime setOffsetTime(int hourAdded) {
        LocalTime reservedTime;
        reservedTime = LocalTime.now().plusHours(hourAdded);
        return reservedTime;
    }
//    public static boolean isElementActive (SelenideElement element){
//        return element.isDisplayed() && element.isEnabled();
//    }

    /**
     * Генерирует случайную фразу с заданными параметрами
     * @param language          язык: "ru" или "en"
     * @param allowSpecialChars разрешены ли спецсимволы
     * @param allowDigits       разрешены ли цифры
     * @param length            требуемая длина фразы, число больше нуля
     */
    public static String generatePhrase(String language, boolean allowSpecialChars,
                                        boolean allowDigits, int length) {
        // Валидация входных параметров
        if (length <= 0) {
            throw new IllegalArgumentException("Длина фразы должна быть положительной");
        }

        StringBuilder phrase = new StringBuilder();

        // Определяем набор допустимых символов
        String allowedChars = buildAllowedChars(allowDigits, allowSpecialChars, language);

        Random random = new Random();

        // Генерируем фразу посимвольно
        while (phrase.length() < length) {
            // Берём случайный символ из допустимого набора
            char randomChar = allowedChars.charAt(
                    random.nextInt(allowedChars.length()));
            // Добавляем его в фразу
            phrase.append(randomChar);
        }
        // Если длина превышена, обрезаем
        if (phrase.length() > length) {
            return phrase.substring(0, length);
        }
        return phrase.toString();
    }

    private static String buildAllowedChars(boolean allowDigits, boolean allowSpecialChars, String language) {
        StringBuilder chars = new StringBuilder();
        if (language.equalsIgnoreCase("en")) {
            chars.append("abcdefghijklmnopqrstuvwxyz");
        }
        if (language.equalsIgnoreCase("ru")) {
            chars.append("абвгдеёжзийклмнопрстуфхцчшщъыьэюя");
        }
        if (allowDigits) {
            chars.append("0123456789");
        }
        if (allowSpecialChars) {
            chars.append("!@#$%^&*()_+-=[]{}|;:,.<>?");
        }
        return chars.toString();
    }

    /**
     * Генерирует случайное число с заданным количеством цифр
     * @param digitCount количество цифр в числе (должно быть > 0)
     */
    public static String generateRandomNumber(int digitCount) {
        // Валидация входных параметров
        if (digitCount <= 0) {
            throw new IllegalArgumentException("Количество цифр должно быть положительным числом");
        }

        Random random = new Random();
        StringBuilder number = new StringBuilder();
        // Генерируем каждую цифру отдельно
        for (int i = 0; i < digitCount; i++) {
            int digit = random.nextInt(10); // 0..9
            number.append(digit);
        }
        return number.toString();
    }
}
