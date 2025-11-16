package cs580.exception_handling;

// Generic validation strategy interface - supports any exception type
@FunctionalInterface
public interface ValidationStrategy<E extends Exception> {
    void validate() throws E;
}
