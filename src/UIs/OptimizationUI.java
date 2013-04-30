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
        public Object execute(String data);
    }
    
    private static class ConvertToIntegerCommand implements Command {
        public Integer execute(String data) {
            return Integer.parseInt(data);
        }
    }
    
    private static class ConvertToDoubleCommand implements Command {
        public Double execute(String data) {
            return Double.parseDouble(data);
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
                    row.set(i, convertToType.execute((String) row.get(i)));
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
    
    public ArrayList<ArrayList<Object>> getCsvAsIntegers(String fileName) {
        return getCsv(fileName, new ConvertToIntegerCommand());
    }
    
    public ArrayList<ArrayList<Object>> getCsvAsDoubles(String fileName) {
        return getCsv(fileName, new ConvertToDoubleCommand());
    }

}
