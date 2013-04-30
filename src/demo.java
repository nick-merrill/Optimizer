import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import exceptions.InputException;
import exceptions.PositiveNumberInputException;
import UIs.CsvReader;
import UIs.TerminalUI;
import algorithms.*;
import problems.*;
import solutions.*;

public class demo {
    
    static ArrayList<OptimizationAlgorithm> algs;
    static int algID;
    static TerminalUI gui;
    
    static OptimizationProblem prob;
    
    public static void runFence() {
	    double fenceLength;
        try {
            fenceLength = gui.getDoubleInput("length of fence");
            prob = new FenceProblem(fenceLength);
        } catch (InputException e) {
            gui.display(e.getMessage());
            System.exit(6);
        }
    }
    
    public static void runBox() {
        double volume;
        try {
            volume = gui.getDoubleInput("volume of box");
            prob = new BoxMinAreaProb(volume);
        }
        catch (InputException e) {
            gui.display(e.getMessage());
            System.exit(6);
        }
    }
    
    public static void runManufacturing() {
        double constraintA, constraintB, constraintC, profitX, profitY,
            usageAbyX, usageAbyY, usageBbyX, usageBbyY, usageCbyX,
            usageCbyY;
        try {
            constraintA = gui.getDoubleInput("units of resource A");
            constraintB = gui.getDoubleInput("units of resource B");
            constraintC = gui.getDoubleInput("units of resource C");
            profitX = gui.getDoubleInput("profit made by producing 1 item of X");
            profitY = gui.getDoubleInput("profit made by producing 1 item of Y");
            usageAbyX = gui.getDoubleInput("units of resource A used to produce 1 item of X");
            usageAbyY = gui.getDoubleInput("units of resource A used to produce 1 item of Y");
            usageBbyX = gui.getDoubleInput("units of resource B used to produce 1 item of X");
            usageBbyY = gui.getDoubleInput("units of resource B used to produce 1 item of Y");
            usageCbyX = gui.getDoubleInput("units of resource C used to produce 1 item of X");
            usageCbyY = gui.getDoubleInput("units of resource C used to produce 1 item of Y");
            prob = new ManufacturingProblem(constraintA, constraintB, constraintC, profitX, profitY,
                    usageAbyX, usageAbyY, usageBbyX, usageBbyY, usageCbyX,
                    usageCbyY);
        } catch (InputException e) {
            gui.display(e.getMessage());
            System.exit(6);
        }
    }
    
    public static void runMichaelwicz() {
        prob = new MichaelwiczMinProb();
    }
    
    public static void runNurse() {
        // Gets necessary CSVs
        CsvReader csvReader = new CsvReader();
        
        int numEmployees, numDays, numShifts;
        try {
            numEmployees = gui.getIntegerInput("number of employees");
            numDays = gui.getIntegerInput("number of days in the schedule");
            numShifts = gui.getIntegerInput("number of shifts per day");
        } catch (InputException e) {
            e.printStackTrace();
            System.exit(7);
            return;
        }
        

        ArrayList<ArrayList<Integer>> shiftReqs = null;
        do {
            try {
                String reqsFile = gui.getFile("Please choose the CSV file for nurses' shift **requirements**");
                shiftReqs = csvReader.getCsvAsIntegers(reqsFile);
                break;
            } catch (IOException e1) {
                gui.display(e1.getMessage());
            }
        } while (true);
                
        ArrayList<ArrayList<Integer>> shiftPrefs;
        do {
            try {
                String prefsFile = gui.getFile("Please choose the CSV file for nurses' shift **preferences**");
                shiftPrefs = csvReader.getCsvAsIntegers(prefsFile);
                break;
            } catch (IOException e1) {
                gui.display(e1.getMessage());
            }
        } while (true);
        
		try {
			prob = new NurseSchedProb(numEmployees, numDays, numShifts, shiftReqs, shiftPrefs);
		} catch (InputException e) {
			e.printStackTrace();
			System.exit(7);
		}
    }
    
	public static void main(String[] args) {
	    
	    gui = new TerminalUI();
	    
		int probID = gui.getOptionChoice("Hey! What problem do you want to solve?",
		        new String[]{"Fence Problem - Determines how long of a side adjacent to a river with given fence length to maximize area.",
		        "Box Minimization of Area Problem - Minimizes the surface area of a box, given a volume.",
		        "Manufacturing Problem - Maximize profit based on resources used.",
		        "Michaelwicz Problem - Optimizes a bivariate Michaelwicz function. No inputs are necessary.",
		        "Nurse Scheduling Problem - Optimizes nurse schedules for preferences with shift requirements as constraints."
		        });
		
		System.out.println(probID);
		
		int algID = gui.getOptionChoice("Which algorithm do you want to use?",
		        new String[]{"Cuckoo Search Optimization"});
		
	    algs = new ArrayList<OptimizationAlgorithm>();
		algs.add(new CuckooSearchOpt());
		algs.add(new ParticleSwarmOpt());
		
		switch (probID) {
		    case 0:
		        runFence();
		        break;
		    case 1:
		        runBox();
		        break;
		    case 2:
		        runManufacturing();
		        break;
		    case 3:
		        runMichaelwicz();
		        break;
		    case 4:
		        runNurse();
		        break;
	        default:
	            System.out.println("Bad case!");
	            System.exit(5);
		}
	    algs.get(algID).solve(prob);
	    Solution sol = algs.get(algID).getSolutions(prob).getMostFitSolution(prob);
	    gui.display(prob.solToString(sol));
		
	}

}
