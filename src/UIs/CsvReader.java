package UIs;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class CsvReader {
    
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
