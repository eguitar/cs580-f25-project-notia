package cs580.exception_handling;

public class IllegalStateValidationFactory extends ValidationFactory<IllegalStateException> {
    IllegalStateValidationFactory() {
        super(IllegalStateException::new);
    }
}
