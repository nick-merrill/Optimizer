package exceptions;

@SuppressWarnings("serial")
public class PositiveNumberInputException extends InputException {
    public PositiveNumberInputException(String variableName) {
        super(variableName, "must be a positive real number");
    }
}