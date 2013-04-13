import algorithms.*;
import problems.*;


public class optimizer {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int numEmployees = 20;
		
		NurseSchedProb optProb = new NurseSchedProb(numEmployees);
		CuckooSearchOpt optAlg = new CuckooSearchOpt();
		optAlg.solve(optProb);
	}

}
