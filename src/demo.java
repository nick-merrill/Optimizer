import java.util.ArrayList;

import exceptions.InputException;
import exceptions.PositiveNumberInputException;
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
        } catch (InputException e) {
            System.out.println(e.getMessage());
            return;
        }
        try {
            prob = new FenceProblem(fenceLength);
        } catch (PositiveNumberInputException e) {
            System.out.println(e.getMessage());
            return;
        }
    }
    
    public static void runBox() {
        double volume;
        try {
            volume = gui.getDoubleInput("volume of box");
        } catch (InputException e) {
            System.out.println("Error. Sorry.");
            return;
        }
        try {
            prob = new BoxMinAreaProb(volume);
        }
        catch (PositiveNumberInputException e) {
            System.out.println("Error. Sorry.");
            return;
        }
    }
    
    public static void runManufacturing() {
        double constraintA, constraintB, constraintC, profitX, profitY,
            usageAbyX, usageAbyY, usageBbyX, usageBbyY, usageCbyX,
            usageCbyY;
        try {
            constraintA = gui.getDoubleInput("units of resource A");
        } catch (InputException e) {
            System.out.println("Error. Sorry.");
            return;
        }
        try {
            constraintB = gui.getDoubleInput("units of resource B");
        } catch (InputException e) {
            System.out.println("Error. Sorry.");
            return;
        }
        try {
            constraintC = gui.getDoubleInput("units of resource C");
        } catch (InputException e) {
            System.out.println("Error. Sorry.");
            return;
        }
        try {
            profitX = gui.getDoubleInput("profit made by producing 1 item of X");
        } catch (InputException e) {
            System.out.println("Error. Sorry.");
            return;
        }
        try {
            profitY = gui.getDoubleInput("profit made by producing 1 item of Y");
        } catch (InputException e) {
            System.out.println("Error. Sorry.");
            return;
        }
        try {
            usageAbyX = gui.getDoubleInput("units of resource A used to produce 1 item of X");
        } catch (InputException e) {
            System.out.println("Error. Sorry.");
            return;
        }
        try {
            usageAbyY = gui.getDoubleInput("units of resource A used to produce 1 item of Y");
        } catch (InputException e) {
            System.out.println("Error. Sorry.");
            return;
        }
        try {
            usageBbyX = gui.getDoubleInput("units of resource B used to produce 1 item of X");
        } catch (InputException e) {
            System.out.println("Error. Sorry.");
            return;
        }
        try {
            usageBbyY = gui.getDoubleInput("units of resource B used to produce 1 item of Y");
        } catch (InputException e) {
            System.out.println("Error. Sorry.");
            return;
        }
        try {
            usageCbyX = gui.getDoubleInput("units of resource C used to produce 1 item of X");
        } catch (InputException e) {
            System.out.println("Error. Sorry.");
            return;
        }
        try {
            usageCbyY = gui.getDoubleInput("units of resource C used to produce 1 item of Y");
        } catch (InputException e) {
            System.out.println("Error. Sorry.");
            return;
        }
        prob = new ManufacturingProblem(constraintA, constraintB, constraintC, profitX, profitY,
                usageAbyX, usageAbyY, usageBbyX, usageBbyY, usageCbyX,
                usageCbyY);
    }
    
    public static void runMichaelwicz() {
        prob = new MichaelwiczMinProb();
    }
    
	public static void main(String[] args) {
	    
	    gui = new TerminalUI();
	    
		int probID = gui.getOptionChoice("Hey! What problem do you want to solve?",
		        new String[]{"Fence Problem - Determines how long of a side adjacent to a river with given fence length to maximize area.",
		        "Box Minimization of Area Problem - Minimizes the surface area of a box, given a volume.",
		        "Manufacturing Problem - Maximize profit based on resources used.",
		        "Michaelwicz Problem - Optimizes a bivariate Michaelwicz function. No inputs are necessary."
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
	        default:
	            System.out.println("Bad case!");
	            System.exit(5);
		}
	    algs.get(algID).solve(prob);
	    Solution sol = algs.get(algID).getSolutions(prob).getMostFitSolution(prob);
	    gui.display(prob.solToString(sol));
		
	}

}
