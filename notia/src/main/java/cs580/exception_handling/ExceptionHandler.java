package cs580.exception_handling;

// Exception handler interface using Template Method pattern
@FunctionalInterface
interface ExceptionHandler<E extends Exception> {
    void handle(E exception) throws E;
}
