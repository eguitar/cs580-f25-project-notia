package cs580.exception_handling;

// Public validation utility class using Strategy pattern - reusable across project
public class Validator {
    // Private constructor to prevent instantiation
    private Validator() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    /**
     * Validates that an object is not null
     *
     * @param obj     the object to validate
     * @param message the error message if validation fails
     * @throws IllegalArgumentException if obj is null
     */
    public static void requireNonNull(Object obj, String message) {
        if (obj == null) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Validates that a string is not null or empty (after trimming)
     *
     * @param str     the string to validate
     * @param message the error message if validation fails
     * @throws IllegalArgumentException if str is null or empty
     */
    public static void requireNonEmpty(String str, String message) {
        if (str == null || str.trim().isEmpty()) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Validates that a value is non-negative
     *
     * @param value   the value to validate
     * @param message the error message if validation fails
     * @throws IllegalArgumentException if value is negative
     */
    public static void requireNonNegative(int value, String message) {
        if (value < 0) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Validates that a condition is true
     *
     * @param condition the condition to check
     * @param message   the error message if validation fails
     * @throws IllegalArgumentException if condition is false
     */
    public static void requireCondition(boolean condition, String message) {
        if (!condition) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Sanitizes a string by trimming whitespace
     *
     * @param input the string to sanitize
     * @return trimmed string or null if input is null
     */
    public static String sanitizeString(String input) {
        return input != null ? input.trim() : null;
    }

    /**
     * Validates that a value is within a specified range (inclusive)
     *
     * @param value   the value to validate
     * @param min     minimum allowed value
     * @param max     maximum allowed value
     * @param message the error message if validation fails
     * @throws IllegalArgumentException if value is outside range
     */
    public static void requireInRange(int value, int min, int max, String message) {
        if (value < min || value > max) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Validates that a string matches a specific pattern
     *
     * @param str     the string to validate
     * @param regex   the regex pattern to match
     * @param message the error message if validation fails
     * @throws IllegalArgumentException if string doesn't match pattern
     */
    public static void requirePattern(String str, String regex, String message) {
        if (str == null || !str.matches(regex)) {
            throw new IllegalArgumentException(message);
        }
    }
}
