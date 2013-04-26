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
    
}
