package cs580.exception_handling;

import java.util.Date;

// Public validation utility class - extensible and reusable as a library
public final class Validators {
    private static final IllegalArgumentValidationFactory ILLEGAL_ARGUMENT = new IllegalArgumentValidationFactory();
    private static final IllegalStateValidationFactory ILLEGAL_STATE = new IllegalStateValidationFactory();
    private static final NullPointerValidationFactory NULL_POINTER = new NullPointerValidationFactory();

    private Validators() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    // --- Factory accessors (choose the exception type you want to throw) ---

    public static IllegalArgumentValidationFactory illegalArgument() {
        return ILLEGAL_ARGUMENT;
    }

    public static IllegalStateValidationFactory illegalState() {
        return ILLEGAL_STATE;
    }

    public static NullPointerValidationFactory nullPointer() {
        return NULL_POINTER;
    }

    // --- Small commonly useful helpers ---

    public static String sanitizeString(String input) {
        return input != null ? input.trim() : null;
    }

    // --- Date helpers surface DateValidations as convenient methods ---

    public static final class DateValidator {
        private DateValidator() {
        }

        public static void requireRange(Date start, Date end, String message) {
            ValidationExecutor.execute(DateValidations.validateDateRange(start, end, message));
        }

        public static void requireFuture(Date date, String message) {
            ValidationExecutor.execute(DateValidations.requireFutureDate(date, message));
        }

        public static void requirePast(Date date, String message) {
            ValidationExecutor.execute(DateValidations.requirePastDate(date, message));
        }

        public static void requireNonNull(Date date, String message) {
            ValidationExecutor.execute(DateValidations.requireNonNull(date, message));
        }

        public static void requireImmutable(Date original, Date current, String message) {
            ValidationExecutor.execute(DateValidations.requireImmutableDate(original, current, message));
        }
    }
}
