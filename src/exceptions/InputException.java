package exceptions;

@SuppressWarnings("serial")
public class InputException extends Exception {
    public InputException(String inputName, String msg) {
        super("Invalid input: " +
            inputName.substring(0,1).toUpperCase()+inputName.substring(1) +
            " " + msg +".");
    }
}