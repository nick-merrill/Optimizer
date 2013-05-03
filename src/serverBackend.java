import java.io.IOException;
import java.util.ArrayList;

import exceptions.InputException;
import UIs.ArgumentUI;
import UIs.CsvReader;
import UIs.TerminalUI;
import algorithms.*;
import problems.*;
import solutions.*;

public class serverBackend {
    
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
        int numEmployees, numDays, numShifts, maxShiftsInRow, maxShiftsADay, minShifts;
        double lambdaPref, lambdaMin;
        try {
            numEmployees = gui.getIntegerInput("number of employees");
            numDays = gui.getIntegerInput("number of days in the schedule");
            numShifts = gui.getIntegerInput("number of shifts per day");
            maxShiftsInRow = gui.getIntegerInput("the maximum number of shifts in a row that an employee can be on duty");
            maxShiftsADay = gui.getIntegerInput("the maximum number of shifts in a 24 hour span that an employee can be on duty");
            minShifts = gui.getIntegerInput("the minimum number of shifts that an employee must be on duty within one scheduling period");
            lambdaPref = gui.getDoubleInput("the proportional weight of satisfying employee preferences over minimizing costs");
            lambdaMin = gui.getDoubleInput("the proportional weight of satisfying minimum shifts per employee per scheduling period over minimizing costs");
        } catch (InputException e) {
            e.printStackTrace();
            System.exit(7);
            return;
        }
        
        // Gets necessary CSVs
        CsvReader csvReader = new CsvReader();
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
			prob = new NurseSchedProb(numEmployees, numDays, numShifts, maxShiftsInRow, 
					maxShiftsADay, minShifts,  lambdaPref, lambdaMin, shiftReqs, shiftPrefs);
		} catch (InputException e) {
			e.printStackTrace();
			System.exit(7);
		}
    }
    
    public static void runEgg() {
        prob = new EggholderFuncProb();
    }
    
    public static void runRastrigin() {
	    int N;
        try {
			N = gui.getIntegerInput("number of variables");
			prob = new RastriginMinProb(N);
		} catch (InputException e) {
			e.printStackTrace();
			System.exit(7);
		}
     }
    
    public static void runRosenbrock() {
	    int N;
        try {
			N = gui.getIntegerInput("number of variables");
			prob = new RosenbrockMinProb(N);
		} catch (InputException e) {
			e.printStackTrace();
			System.exit(7);
		}
     }
    
	public static void main(String[] args) {
	    
	    gui = new ArgumentUI(args);
	    
		int probID = gui.getOptionChoice("Hey! What problem do you want to solve?",
		        new String[]{"Fence Problem - Determines how long of a side adjacent to a river with given fence length to maximize area.",
		        "Box Minimization of Area Problem - Minimizes the surface area of a box, given a volume.",
		        "Manufacturing Problem - Maximize profit based on resources used.",
		        "Michaelwicz Problem - Optimizes a bivariate Michaelwicz function. No inputs are necessary.",
		        "Nurse Scheduling Problem - Optimizes nurse schedules for preferences and cost with shift requirements as constraints.",
		        "Egg Holder Function - Optimizes a bivariate Egg Holder function. No inputs are necessary.",
		        "Rastrigin Minimization Problem - Optimizes a multivariate Rastrigin function. User inputs number of variables.",
		        "Rosenbrock Minimization Problem - Optimizes a multivariate Rosenbrock function. User inputs number of variables."
		        });
		
		int algID = gui.getOptionChoice("Which algorithm do you want to use?",
		        new String[]{"Cuckoo Search Optimization","Particle Swarm Optimization","Hybrid CS/PSO"});
		
	    algs = new ArrayList<OptimizationAlgorithm>();
		algs.add(new CuckooSearchOpt());
		algs.add(new ParticleSwarmOpt());
		algs.add(new BirdsAndBeesOpt());
		
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
		    case 5:
		    	runEgg();
		    	break;
		    case 6: 
		    	runRastrigin();
		    	break;
		    case 7: 
		    	runRosenbrock();
		    	break;
	        default:
	            System.out.println("Bad case!");
	            System.exit(5);
		}
	    algs.get(algID).solve(prob);
	    Solution sol = algs.get(algID).getSolutions(prob).getMostFitSolution(prob);
	    if (prob.solToTable(sol) != null)
	    	gui.display(prob.solToTable(sol));
	    else gui.display(prob.solToString(sol));
		
	}

}
