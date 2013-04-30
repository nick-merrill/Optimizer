import java.io.IOException;
import java.util.ArrayList;

import exceptions.InputException;
import exceptions.PositiveNumberInputException;
import UIs.TerminalUI;
import algorithms.*;
import problems.*;
import solutions.*;

public class temporary {
    
    static ArrayList<OptimizationAlgorithm> algs;
    static int algID;
    static TerminalUI gui;
    
    static OptimizationProblem prob;
    
	public static void main(String[] args) {
	    
	    gui = new TerminalUI();
	    
	    gui.getCsvAsIntegers(args[0]);
	}

}
