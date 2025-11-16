package cs580.exception_handling;

// Validation executor with extensible exception handling
public class ValidationExecutor {
    /**
     * Execute validation with specific exception type
     *
     * @param strategy the validation strategy to execute
     * @param <E>      the exception type
     * @throws E if validation fails
     */
    public static <E extends Exception> void execute(ValidationStrategy<E> strategy) throws E {
        strategy.validate();
    }

    /**
     * Execute validation with a custom handler.
     * If the strategy throws, we pass the exception to the handler (typed).
     */
    public static <E extends Exception> void execute(
            ValidationStrategy<E> strategy,
            ExceptionHandler<E> handler) {
        try {
            strategy.validate();
        } catch (Exception e) {
            try {
                @SuppressWarnings("unchecked")
                E castException = (E) e;
                handler.handle(castException);
            } catch (Exception handlerException) {
                throw new RuntimeException("Exception handling failed", handlerException);
            }
        }
    }

    /**
     * Execute multiple validations with specific exception type
     *
     * @param strategies the validation strategies to execute
     * @param <E>        the exception type
     * @throws E if any validation fails
     */
    @SafeVarargs
    public static <E extends Exception> void executeAll(ValidationStrategy<E>... strategies) throws E {
        for (ValidationStrategy<E> strategy : strategies) {
            strategy.validate();
        }
    }

}
