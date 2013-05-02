package exceptions;

@SuppressWarnings("serial")
public class InputException extends Exception {
    
    private static String makeSentence(String s) {
        return s.substring(0,1).toUpperCase()+s.substring(1) +".";
    }
    
    private static String genMessage(String inputName, String msg) {
        return "Invalid input: " + makeSentence(inputName + " " + msg);
    }
    
    private static String genMessage(String inputName, String msg, String suggestion) {
        return "Invalid input: " + makeSentence(inputName + " " + msg) + " " +
                makeSentence(suggestion);
    }
    
    public InputException(String inputName, String msg) {
        super(genMessage(inputName, msg));
    }
    
    public InputException(String inputName, String msg, String suggestion) {
        super(genMessage(inputName, msg, suggestion));
    }
}