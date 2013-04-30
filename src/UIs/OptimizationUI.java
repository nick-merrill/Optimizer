package UIs;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Scanner;

import exceptions.InputException;

import solutions.Solution;
import problems.OptimizationProblem;
import UIs.*;

public abstract class OptimizationUI {
    
    public OptimizationUI() {
    }
    
    public abstract String getVariableInput(String varName);
    public abstract void printSolution(Solution solution);
    public abstract ArrayList<ArrayList<Integer>> getCsvAsIntegers();
    public abstract void display(String output);
    
    public Double getDoubleInput(String varName) throws InputException {
        String s = getVariableInput(varName);
        try {
            return Double.parseDouble(s);
        }
        catch(NumberFormatException e) {
            throw new InputException(varName, "is not a double");
        }
    }
    
    public StringBuilder readFile(String fFileName) throws IOException {
        StringBuilder text = new StringBuilder();
        String NL = System.getProperty("line.separator");
        Scanner scanner = new Scanner(new FileInputStream(fFileName));
        try {
            while (scanner.hasNextLine()){
                text.append(scanner.nextLine() + NL);
            }
        }
        finally{
            scanner.close();
        }
        return text;
    }

}
