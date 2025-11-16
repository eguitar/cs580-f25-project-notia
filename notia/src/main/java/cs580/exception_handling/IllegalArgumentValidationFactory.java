package cs580.exception_handling;

public class IllegalArgumentValidationFactory extends ValidationFactory<IllegalArgumentException> {
    IllegalArgumentValidationFactory() {
        super(IllegalArgumentException::new);
    }
    // No extra methods needed; uses the common ones in base class.
}
