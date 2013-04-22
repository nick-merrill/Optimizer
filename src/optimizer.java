import algorithms.*;
import problems.*;

public class optimizer {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		int numEmployees = 20;
//		NurseSchedProb optProb = new NurseSchedProb(numEmployees);
		CuckooSearchOpt csAlg = new CuckooSearchOpt();
//		optAlg.solve(optProb);
	    
	    double fenceLength = 100.;
	    FenceProblem fenceProb = new FenceProblem(fenceLength);
	    csAlg.solve(fenceProb);
	    csAlg.getSolutions(fenceProb).getMostFitSolution(fenceProb).print();
	    
	    double volume = 100.;
	    BoxMinAreaProb boxProb = new BoxMinAreaProb(volume);
	    csAlg.solve(boxProb);
	    csAlg.getSolutions(boxProb).getMostFitSolution(boxProb).print();
	    
	    MichaelwiczMinProb michaelwiczProb = new MichaelwiczMinProb();
	    csAlg.solve(michaelwiczProb);
	    csAlg.getSolutions(michaelwiczProb).getMostFitSolution(michaelwiczProb).print();
	}

}
