package UIs;

import java.awt.List;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.concurrent.*;
import java.util.Arrays;
import java.util.Scanner;
import java.util.TimerTask;

import solutions.Solution;

public class ArgumentUI extends TerminalUI {

    private int currentArg;
    private final String[] args;
    
    public ArgumentUI(String[] args) {
        super();
        currentArg = 0;
        this.args = args.clone();
    }
    
    @Override
    public String getVariableInput(String varName) {
        return getNextArg();
    }
    
    @Override
    public String getFile(String request) {
        return getVariableInput(request);
    }
    
    private String getNextArg() {
        try {
            String input = args[currentArg];
            currentArg++;
            return input;
        }
        catch (ArrayIndexOutOfBoundsException e) {
            display("You didn't provide enough variables.");
            System.exit(8);
            return null;
        }
    }
    
    public int getOptionChoice(String prompt, ArrayList<String> options) {
        
        int len = options.size();
        int choice = Integer.parseInt(getNextArg());
        if (choice >= 1 && choice <= len) {
            return choice - 1;
        } else {
            System.exit(8);
            return -1;
        }
    }
    
    /*------------ END PROMPTS -----------*/
    
    @Override
    public void printSolution(Solution solution) {
        solution.print();
    }
    
    public void display(String output) {
        System.out.println(output);
    }

}
