package cs580.exception_handling;

import java.util.Date;

// Public date validation utility - reusable across project
public class DateValidator {
    // Private constructor to prevent instantiation
    private DateValidator() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    /**
     * Validates that end date is not before start date
     *
     * @param startDate    the start date
     * @param endDate      the end date
     * @param errorMessage the error message if validation fails
     * @throws IllegalArgumentException if end date is before start date
     */
    public static void validateDateRange(Date startDate, Date endDate, String errorMessage) {
        if (startDate != null && endDate != null && endDate.before(startDate)) {
            throw new IllegalArgumentException(errorMessage);
        }
    }

    /**
     * Validates that a date is not in the past
     *
     * @param date    the date to validate
     * @param message the error message if validation fails
     * @throws IllegalArgumentException if date is in the past
     */
    public static void requireFutureDate(Date date, String message) {
        if (date != null && date.before(new Date())) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Validates that a date is not in the future
     *
     * @param date    the date to validate
     * @param message the error message if validation fails
     * @throws IllegalArgumentException if date is in the future
     */
    public static void requirePastDate(Date date, String message) {
        if (date != null && date.after(new Date())) {
            throw new IllegalArgumentException(message);
        }
    }
}
