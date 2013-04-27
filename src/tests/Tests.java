package tests;

import java.util.ArrayList;

import org.junit.Test;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.junit.Assert;

import algorithms.*;
import problems.*;
import solutions.Solution;

@RunWith(JUnit4.class)
public class Tests {
    
    CuckooSearchOpt csAlg;

    public Tests() {
		this.csAlg = new CuckooSearchOpt();
    }
    
    @Test
    public void testFenceProblem() {
        
	    double fenceLength = 100.;
	    FenceProblem fenceProb = new FenceProblem(fenceLength);
	    csAlg.solve(fenceProb);
	    Solution sol = csAlg.getSolutions(fenceProb).getMostFitSolution(fenceProb);
	    double side1 = sol.getVars().get(0);
	    double area = fenceProb.area(sol);
	    Assert.assertEquals("Fence side length inaccurate.", 25, side1, 0.01);
	    Assert.assertEquals("Fence area inaccurate.", 1250, area, 0.01);
    }
    
    @Test
//    @Ignore
    public void testBoxProb() {
	    double volume = 100.;
	    BoxMinAreaProb boxProb = new BoxMinAreaProb(volume);
	    csAlg.solve(boxProb);
	    ArrayList<Double> solVars = csAlg.getSolutions(boxProb).getMostFitSolution(boxProb).getVars();
	    Assert.assertEquals("Dimensions not equal.", solVars.get(0), solVars.get(1), 0.01);
    }
    
    @Test
    public void testMichaelwiczMinProb() {
	    MichaelwiczMinProb prob = new MichaelwiczMinProb();
	    csAlg.solve(prob);
	    Solution sol = csAlg.getSolutions(prob).getMostFitSolution(prob);
	    ArrayList<Double> solVars = sol.getVars();
	    Assert.assertEquals("X not inaccurate.", 2.20319, solVars.get(0), 0.01);
	    Assert.assertEquals("Y not inaccurate.", 1.57049, solVars.get(1), 0.01);
	    Assert.assertEquals("Value not accurate.", prob.eval(sol), -1.8013, 0.01);
    }
    
    @Test
    public void testManufacturingProblem() {
    	double constraintA = 20;
    	double constraintB = 30;
    	double constraintC = 16;
    	double profitX = 3;
    	double profitY = 4;
    	double usageAbyX = 2.5;
    	double usageAbyY = 1;
    	double usageBbyX = 3;
    	double usageBbyY = 3;
    	double usageCbyX = 1;
    	double usageCbyY = 2;
    	ManufacturingProblem manProb = new ManufacturingProblem(constraintA, constraintB, constraintC, 
    			profitX, profitY, usageAbyX, usageAbyY, usageBbyX, usageBbyY, usageCbyX, usageCbyY);
	    csAlg.solve(manProb);
	    Solution sol = csAlg.getSolutions(manProb).getMostFitSolution(manProb);
	    double x = sol.getVars().get(0);
	    double y = sol.getVars().get(1);
	    //double profit = manProb.profit(sol);
	    Assert.assertEquals("Production of X inaccurate.", 4, x, 0.01);
	    Assert.assertEquals("Production of Y inaccurate.", 6, y, 0.01);
	    //Assert.assertEquals("Profit inaccurate.", 36, profit, 0.01);
    }
    
}
