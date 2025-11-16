package cs580.exception_handling;

// Validation executor using Strategy pattern
public class ValidationExecutor {
    /**
     * Executes a validation strategy
     *
     * @param strategy the validation strategy to execute
     * @throws IllegalArgumentException if validation fails
     */
    public static void execute(ValidationStrategy strategy) {
        strategy.validate();
    }

    /**
     * Executes multiple validation strategies
     *
     * @param strategies the validation strategies to execute
     * @throws IllegalArgumentException if any validation fails
     */
    public static void executeAll(ValidationStrategy... strategies) {
        for (ValidationStrategy strategy : strategies) {
            strategy.validate();
        }
    }
}