package UIs;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class CsvReader {
    
    public interface Command<E> {
        public Object execute(String data);
    }
    
    private static class ConvertToIntegerCommand implements Command<Integer> {
        public Integer execute(String data) {
            return Integer.parseInt(data);
        }
    }
    
    private static class ConvertToDoubleCommand implements Command<Double> {
        public Double execute(String data) {
            return Double.parseDouble(data);
        }
    }
    
    public <E> ArrayList<ArrayList<E>> getCsv(String fileName, Command<E> convertToType) {
        String delimiter = ",";
        try {
            File fin = new File(fileName);
            Scanner csvScanner = new Scanner(fin);
            csvScanner.useDelimiter(delimiter);
            
            ArrayList<ArrayList<E>> rows = new ArrayList<ArrayList<E>>();
            while (csvScanner.hasNextLine()) {
                String[] rowArr = csvScanner.nextLine().split(delimiter);
                ArrayList<E> row = new ArrayList<E>();
                for (int i = 0; i < rowArr.length; i++) {
                    row.add((E) convertToType.execute(rowArr[i]));
                }
                rows.add(row);
            }
            
            return rows;
            
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(7);
            return null;
        }
    }
    
    public ArrayList<ArrayList<Integer>> getCsvAsIntegers(String fileName) {
        return getCsv(fileName, new ConvertToIntegerCommand());
    }
    
    public ArrayList<ArrayList<Double>> getCsvAsDoubles(String fileName) {
        return getCsv(fileName, new ConvertToDoubleCommand());
    }

}
