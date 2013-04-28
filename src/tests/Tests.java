package tests;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.junit.Assert;

import algorithms.*;
import problems.*;
import exceptions.*;
import solutions.Solution;

import java.util.Arrays;

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
//    @Ignore
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
    
    
		int algID = gui.getOptionChoice("Which algorithm do you want to use?",
		        new String[]{"Cuckoo Search Optimization",
		        "Particle Swarm Optimization"});
    
    @Test 
    public void testNurseSchedProb () {
    	int numEmployees = 8;
    	int numDays = 3;
    	int numShifts = 4;
    	Integer[] shiftReqArr = new Integer[]{1,1,1,0};
    	ArrayList<Integer> shiftReq1 = new ArrayList<Integer>(Arrays.asList(shiftReqArr));
    	ArrayList<Integer> shiftReq2 = new ArrayList<Integer>(Arrays.asList(shiftReqArr));
    	ArrayList<Integer> shiftReq3 = new ArrayList<Integer>(Arrays.asList(shiftReqArr));
    	ArrayList<Integer> shiftReq4 = new ArrayList<Integer>(Arrays.asList(shiftReqArr));
    	ArrayList<Integer> shiftReq5 = new ArrayList<Integer>(Arrays.asList(shiftReqArr));
    	ArrayList<Integer> shiftReq6 = new ArrayList<Integer>(Arrays.asList(shiftReqArr));
    	ArrayList<Integer> shiftReq7 = new ArrayList<Integer>(Arrays.asList(shiftReqArr));
    	
    	ArrayList<ArrayList<Integer>> shiftReqs = new ArrayList<ArrayList<Integer>>();
    	shiftReqs.add(shiftReq1);
    	shiftReqs.add(shiftReq2);
    	shiftReqs.add(shiftReq3);
    	shiftReqs.add(shiftReq4);
    	shiftReqs.add(shiftReq5);
    	shiftReqs.add(shiftReq6);
    	shiftReqs.add(shiftReq7);
    	
    	Integer[] prefArr = new Integer[28];
    	int j = 1;
    	for (int i = 0; i < 28; i++) {
    	    if (j == 5) j = 1;
    	    prefArr[i] = j;
    	    j++;
    	}
    	ArrayList<Integer> pref1 = new ArrayList<Integer>(Arrays.asList(prefArr));
    	j = 4;
    	for (int i = 0; i < 28; i++) {
    	    if (j == 5) j = 1;
    	    prefArr[i] = j;
    	    j++;
    	}
    	ArrayList<Integer> pref2 = new ArrayList<Integer>(Arrays.asList(prefArr));
    	j = 3;
    	for (int i = 0; i < 28; i++) {
    	    if (j == 5) j = 1;
    	    prefArr[i] = j;
    	    j++;
    	}
    	ArrayList<Integer> pref3 = new ArrayList<Integer>(Arrays.asList(prefArr));
    	
    	ArrayList<ArrayList<Integer>> preferences = new ArrayList<ArrayList<Integer>>();
    	preferences.add(pref1);
    	preferences.add(pref2);
    	preferences.add(pref3);
    	
    	
    	
    }
    
    
}
