package cs580.exception_handling;

import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

// Abstract validation factory using Factory Method + small DSL helpers
abstract class ValidationFactory<E extends RuntimeException> {

    private final Function<String, E> exceptionBuilder;

    protected ValidationFactory(Function<String, E> exceptionBuilder) {
        this.exceptionBuilder = exceptionBuilder;
    }

    // --- Core creators used by all helpers ---

    protected ValidationStrategy<E> createValidation(
            boolean condition,
            Supplier<E> exceptionSupplier) {
        return () -> {
            if (!condition) {
                throw exceptionSupplier.get();
            }
        };
    }

    protected <T> ValidationStrategy<E> createValidation(
            Predicate<T> predicate,
            T value,
            Supplier<E> exceptionSupplier) {
        return () -> {
            if (!predicate.test(value)) {
                throw exceptionSupplier.get();
            }
        };
    }

    // --- Common helpers available to subclasses ---

    protected ValidationStrategy<E> require(boolean condition, String message) {
        return createValidation(condition, () -> exceptionBuilder.apply(message));
    }

    protected <T> ValidationStrategy<E> require(Predicate<T> predicate, T value, String message) {
        return createValidation(predicate, value, () -> exceptionBuilder.apply(message));
    }

    // Reusable standard validations (minimize re-implementation)

    public ValidationStrategy<E> requireNonNull(Object obj, String message) {
        return require(obj != null, message);
    }

    public ValidationStrategy<E> requireNull(Object obj, String message) {
        return require(obj == null, message);
    }

    public ValidationStrategy<E> requireNonEmpty(String str, String message) {
        return require(s -> s != null && !s.trim().isEmpty(), str, message);
    }

    public ValidationStrategy<E> requireNonNegative(int value, String message) {
        return require(value >= 0, message);
    }

    public ValidationStrategy<E> requirePositive(int value, String message) {
        return require(value > 0, message);
    }

    public ValidationStrategy<E> requireInRange(int value, int min, int max, String message) {
        return require(value >= min && value <= max, message);
    }

    public ValidationStrategy<E> requirePattern(String str, String regex, String message) {
        return require(s -> s != null && s.matches(regex), str, message);
    }

    public ValidationStrategy<E> requireCondition(boolean condition, String message) {
        return require(condition, message);
    }
}
