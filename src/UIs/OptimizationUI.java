package UIs;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.Callable;

import exceptions.InputException;

import solutions.Solution;
import problems.OptimizationProblem;
import UIs.*;

public abstract class OptimizationUI {
    
    public OptimizationUI() {
    }
    
    public abstract String getVariableInput(String varName);
    public abstract void printSolution(Solution solution);
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
    
//    public StringBuilder readFile(String fFileName) throws IOException {
//        StringBuilder text = new StringBuilder();
//        String NL = System.getProperty("line.separator");
//        Scanner scanner = new Scanner(new FileInputStream(fFileName));
//        try {
//            while (scanner.hasNextLine()){
//                text.append(scanner.nextLine() + NL);
//            }
//        }
//        finally{
//            scanner.close();
//        }
//        return text;
//    }

    public ArrayList<ArrayList<String>> getCsvAsStrings(String fileName) {
        String delimiter = ",";
        try {
//            StringBuilder string = this.readFile(fileName);
            File fin = new File(fileName);
            Scanner csvScanner = new Scanner(fin);
            csvScanner.useDelimiter(delimiter);
            ArrayList<ArrayList<String>> rows = new ArrayList<ArrayList<String>>();
            while (csvScanner.hasNextLine()) {
                String[] rowArr = csvScanner.nextLine().split(delimiter);
                ArrayList<String> row = new ArrayList<String>(Arrays.asList(rowArr));
                rows.add(row);
            }
            
            // print
            for (int i = 0; i < rows.size(); i++) {
                for (int j = 0; j < rows.get(i).size(); j++) {
                    System.out.print(rows.get(i).get(j)+"|");
                }
                System.out.println();
            }
            
            return rows;
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(7);
            return null;
        }
    }
    
    public interface Command {
        public Object execute(Object data);
    }
    
    private class ConvertIntegerCommand implements Command {
        public Integer execute(Object data) {
            return 777;
        }
    }
    
    public ArrayList<ArrayList<Object>> getCsv(String fileName, Command convertToType) {
        String delimiter = ",";
        try {
            File fin = new File(fileName);
            Scanner csvScanner = new Scanner(fin);
            csvScanner.useDelimiter(delimiter);
            ArrayList<ArrayList<Object>> rows = new ArrayList<ArrayList<Object>>();
            while (csvScanner.hasNextLine()) {
                String[] rowArr = csvScanner.nextLine().split(delimiter);
                ArrayList<Object> row = new ArrayList<Object>(Arrays.asList(rowArr));
                for (int i = 0; i < row.size(); i++) {
                    row.set(i, convertToType.execute(row.get(i)));
                    System.out.println(row.get(i));
                }
                rows.add(row);
            }
            
            // print
            for (int i = 0; i < rows.size(); i++) {
                for (int j = 0; j < rows.get(i).size(); j++) {
                    System.out.print(rows.get(i).get(j)+"|");
                }
                System.out.println();
            }
            
            return rows;
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(7);
            return null;
        }
    }
    
    protected ArrayList<ArrayList<Object>> matrixMap(Callable<T> fun) {
        ArrayList<ArrayList<String>> rows = getCsvAsStrings(fileName);
        ArrayList<ArrayList<Integer>> intRows = new ArrayList<ArrayList<Integer>>(rows.size());
        for (ArrayList<String> row : rows) {
            ArrayList<String> intRow = new ArrayList<String>(row.size());
            
        }
        fun.call();
    }
    
    public ArrayList<ArrayList<Integer>> getCsvAsIntegers(String fileName) {
        ArrayList<ArrayList<String>> rows = getCsvAsStrings(fileName);
        ArrayList<ArrayList<Integer>> intRows = new ArrayList<ArrayList<Integer>>(rows.size());
        for (ArrayList<String> row : rows) {
            ArrayList<String> intRow = new ArrayList<String>(row.size());
            
        }
        return null;
    }

}
