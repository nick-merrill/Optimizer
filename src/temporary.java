import java.util.ArrayList;

import problems.OptimizationProblem;
import UIs.CsvReader;
import UIs.TerminalUI;
import algorithms.OptimizationAlgorithm;

public class temporary {
    
    static ArrayList<OptimizationAlgorithm> algs;
    static int algID;
    static TerminalUI gui;
    
    static OptimizationProblem prob;
    
	public static void main(String[] args) {
	    
	    gui = new TerminalUI();
	    CsvReader csvReader = new CsvReader();
	    
	    csvReader.getCsvAsIntegers(args[0]);
	}

}
