package cs580.exception_handling;

import java.util.Date;

// Date-specific validation strategies
class DateValidations {
    public static ValidationStrategy<IllegalArgumentException> validateDateRange(
            Date startDate, Date endDate, String errorMessage) {
        return () -> {
            if (startDate != null && endDate != null && endDate.before(startDate)) {
                throw new IllegalArgumentException(errorMessage);
            }
        };
    }

    public static ValidationStrategy<IllegalArgumentException> requireFutureDate(
            Date date, String message) {
        return () -> {
            if (date == null || !date.after(new Date())) {
                throw new IllegalArgumentException(message);
            }
        };
    }

    public static ValidationStrategy<IllegalArgumentException> requirePastDate(
            Date date, String message) {
        return () -> {
            if (date == null || !date.before(new Date())) {
                throw new IllegalArgumentException(message);
            }
        };
    }

    public static ValidationStrategy<IllegalArgumentException> requireNonNull(
            Date date, String message) {
        return () -> {
            if (date == null) {
                throw new IllegalArgumentException(message);
            }
        };
    }

    public static ValidationStrategy<IllegalStateException> requireImmutableDate(
            Date original, Date current, String message) {
        return () -> {
            if (original != null && current != null && !original.equals(current)) {
                throw new IllegalStateException(message);
            }
        };
    }
}
