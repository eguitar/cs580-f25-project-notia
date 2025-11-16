package cs580.exception_handling;

public class NullPointerValidationFactory extends ValidationFactory<NullPointerException> {
    NullPointerValidationFactory() {
        super(NullPointerException::new);
    }
    // Common requireNonNull(...) etc. come from the base class.
}
