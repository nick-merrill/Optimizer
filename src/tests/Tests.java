package tests;

import java.util.ArrayList;

import org.junit.Test;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.junit.Assert;

import algorithms.*;
import problems.*;
import exceptions.*;
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
	    FenceProblem fenceProb;
        try {
            fenceProb = new FenceProblem(fenceLength);
        } catch (InputException e) {
            e.printStackTrace();
            return;
        }
	    csAlg.solve(fenceProb);
	    Solution sol = csAlg.getSolutions(fenceProb).getMostFitSolution(fenceProb);
	    double side1 = sol.getVars().get(0);
	    double area = fenceProb.area(sol);
	    Assert.assertEquals("Fence side length inaccurate.", 25, side1, 0.01);
	    Assert.assertEquals("Fence area inaccurate.", 1250, area, 0.01);
    }
    
    @Test
    public void testBoxProb() {
	    double volume = 100.;
	    BoxMinAreaProb boxProb;
        try {
            boxProb = new BoxMinAreaProb(volume);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
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
    @Ignore
    public void testRastriginMinProb() {
    	int numVar = 10;
    	RastriginMinProb prob = new RastriginMinProb(numVar);
    	csAlg.solve(prob);
    	Solution sol = csAlg.getSolutions(prob).getMostFitSolution(prob);
	    ArrayList<Double> solVars = sol.getVars();
    	for(int i=0; i<numVar; i++) {
    		Assert.assertEquals("x" + i + " not inaccurate.", 0, solVars.get(i), 0.01);
    	}
	    Assert.assertEquals("Value not accurate.", prob.fitness(sol), 0, 0.01);
    }
    
    @Test
    @Ignore
    public void testEggholderFuncProb() {
    	EggholderFuncProb prob = new EggholderFuncProb();
    	csAlg.solve(prob);
    	Solution sol = csAlg.getSolutions(prob).getMostFitSolution(prob);
	    ArrayList<Double> solVars = sol.getVars();
    	Assert.assertEquals("x not inaccurate.", 512, solVars.get(0), 0.01);
	    Assert.assertEquals("y not inaccurate.", 404.2319, solVars.get(1), 0.01);
	    Assert.assertEquals("Value not accurate.", prob.fitness(sol), 959.6407, 0.01);
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
