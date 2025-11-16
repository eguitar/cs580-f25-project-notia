package cs580.exception_handling;

// Strategy interface for validation
@FunctionalInterface
public interface ValidationStrategy {
    void validate() throws IllegalArgumentException;
}
