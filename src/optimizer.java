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
		MichaelwiczMinProb prob = new MichaelwiczMinProb();
		//RastriginMinProb prob = new RastriginMinProb();
		//EggholderFuncProb prob = new EggholderFuncProb();
		//RosenbrockMinProb prob = new RosenbrockMinProb(4);
		
		
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
