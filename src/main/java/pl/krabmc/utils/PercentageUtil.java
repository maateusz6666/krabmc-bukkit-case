package pl.krabmc.utils;

public class PercentageUtil {
    public static double calculatePercentage(int itemCount) {
        if (itemCount <= 0) {
            throw new IllegalArgumentException("Liczba elementów musi być większa niż zero.");
        }

        return Math.round((100.0 / itemCount) * 100.0) / 100.0;
    }
}
