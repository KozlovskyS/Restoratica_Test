package data;

import page.BookingFormPage;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
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
}
