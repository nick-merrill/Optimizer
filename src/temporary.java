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
    
	public static void main(String[] args) {
	    
	    gui = new TerminalUI();
	    
	    gui.getCsvAsIntegers(args[0]);
	}

}
