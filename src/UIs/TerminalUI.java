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

public class TerminalUI extends OptimizationUI {

    protected Scanner scanner;
    
    public TerminalUI() {
        scanner = new Scanner(System.in);
    }
    
    /* ************ PROMPTS *************/
    
    public void displayPrompt() {
        System.out.printf(" --> ");
    }
    
    @Override
    public String getVariableInput(String varName) {
        System.out.printf("Enter the following variable: "+varName+"\n");
        displayPrompt();
        return scanner.nextLine();
    }
    
    @Override
    public String getFile(String request) {
        System.out.printf(request + "\nPlease enter a file path from the current location (e.g. \"../file.csv\").\n");
        displayPrompt();
        return scanner.nextLine();
    }
    
    public int getOptionChoice(String prompt, ArrayList<String> options) {
        
        while (true) {
            System.out.println(prompt + " (select by typing number)");
            
            int len = options.size();
            for (int i = 0; i < len; i++) {
                System.out.printf("  %d. %s\n", i+1, options.get(i));
            }
            displayPrompt();
            int choice = Integer.parseInt(scanner.nextLine());
            if (choice >= 1 && choice <= len) {
                return choice - 1;
            }
            System.out.println("Invalid choice! Try again.");
        }
    }
    
    public int getOptionChoice(String prompt, String[] options) {
        return getOptionChoice(prompt, new ArrayList<String>(Arrays.asList(options)));
    }
    
    /*------------ END PROMPTS -----------*/
    
    @Override
    public void printSolution(Solution solution) {
        solution.print();
    }
    
    public void display(String output) {
        System.out.println(output);
    }
    
//    private TimerTask displayUpdater;
//    
//    public void activateDisplayUpdater() {
//        if (displayUpdater != null) displayUpdater.cancel();
//        displayUpdater = new TimerTask();
//        
//        
//         
//    }
//    
//    public void deactivateDisplayUpdater() {
//
//@Override
//public void run() {
//    // TODO Auto-generated method stub
//    
//}
//        
//    }

}
