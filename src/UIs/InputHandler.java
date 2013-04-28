package UIs;

import exceptions.*;

public abstract class InputHandler<E> {
    
    protected String inputName;
    
    public InputHandler(String inputName) {
        this.inputName = inputName;
    }

    public abstract E stringToValue(String s) throws InputException;
    
}
