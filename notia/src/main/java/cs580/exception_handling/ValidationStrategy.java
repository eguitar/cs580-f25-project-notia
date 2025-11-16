package cs580.exception_handling;

// Generic validation strategy interface - supports any exception type
@FunctionalInterface
interface ValidationStrategy<E extends Exception> {
    void validate() throws E;
}
