package UIs;

import exceptions.*;

public class DoubleInputHandler extends InputHandler<Double> {
    
    public DoubleInputHandler(String inputName) {
        super(inputName);
    }

    public Double stringToValue(String s) throws InputException {
        try {
            return Double.parseDouble(s);
        }
        catch(NumberFormatException e) {
            throw new InputException(s, s);
        }
    }

}
