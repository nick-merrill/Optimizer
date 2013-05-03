import java.util.ArrayList;
import java.util.Arrays;

import exceptions.InputException;
import exceptions.PositiveNumberInputException;

import UIs.LanternaGUI;
import algorithms.*;
import problems.*;
import solutions.*;

public class optimizer {

	public static void main(String[] args) {
	    
		psoTest();
	    
	}
	
	private static void psoTest() {
		ParticleSwarmOpt psoAlg = new ParticleSwarmOpt();
		CuckooSearchOpt csAlg = new CuckooSearchOpt();
		BirdsAndBeesOpt bbAlg = new BirdsAndBeesOpt();
		
		//double fenceLength = 100.;
		RastriginMinProb prob = new RastriginMinProb();
		
		testAlgProb(psoAlg, prob);
		testAlgProb(csAlg, prob);
		testAlgProb(bbAlg, prob);

	    
	}
	
	private static void testAlgProb(OptimizationAlgorithm optAlg, OptimizationProblem prob) {
		long startTime, endTime;

	 	startTime = System.currentTimeMillis();
	 	optAlg.solve(prob);
	 	endTime = System.currentTimeMillis();
	 	System.out.println("Algorithm Time: " + (endTime - startTime));
	 	
	 	Solution sol = optAlg.getSolutions(prob).getMostFitSolution(prob);
	    sol.print();
	    
	}
	
}
