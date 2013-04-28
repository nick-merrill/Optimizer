package UIs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import solutions.Solution;

public class TerminalUI extends OptimizationUI {

    private Scanner scanner;
    
    public TerminalUI() {
        scanner = new Scanner(System.in);
    }
    
    @Override
    public String getVariableInput(String varName) {
        System.out.printf("Enter the following variable: "+varName+"\n");
        displayPrompt();
        return scanner.nextLine();
    }

    @Override
    public void printSolution(Solution solution) {
        // TODO Auto-generated method stub
        
    }
    
    public void displayPrompt() {
        System.out.printf(" --> ");
    }
    
    public int getOptionChoice(String prompt, ArrayList<String> options) {
        
        while (true) {
            System.out.println(prompt);
            
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

}
