package algorithms;

import solutions.*;
import problems.*;

public class CuckooSearchOpt extends OptimizationAlgorithm {
	
	public SolutionSet newSolutionSet(SolutionSet seed) {
		// TODO Auto-generated method stub
		return null;
	}

	public void solve(OptimizationProblem optProb) {
		/* 
		 * Objective function 
		 * Generate an initial population of host nests; 
		 * While (t<MaxGeneration) or (stop criterion)
		 *    Get a cuckoo randomly (say, i) and replace its solution by performing Lévy flights;
		 *    Evaluate its quality/fitness 
		 *          [For maximization,  ];
		 *    Choose a nest among n (say, j) randomly;
		 *    if (),
		 *           Replace j by the new solution;
		 *    end if
		 *    A fraction () of the worse nests are abandoned and new ones are built;
		 *    Keep the best solutions/nests;
		 *    Rank the solutions/nests and find the current best;
		 *    Pass the current best solutions to the next generation;
		 * end while
		*/

		// number of nests
		final int N_NESTS = 150;

		// probability of discovery
		final double ABANDON_PROBABILITY = 0.25;

		//TODO: THIS STUFF NEEDS TO BE ABSTRACTED  OUT
		final int NUM_VAR = optProb.getNumVar();
		CSSolutionSet nests = new CSSolutionSet(N_NESTS, NUM_VAR);

		// upper and lower bounds

		// fitness function


		int t = 0;
		while (t < 100) {
		    CSSolution i = nests.getRandSol();
		    Solution newSol = randWalk(i);
		    Solution j = nests.getRandSol();
		    if (optProb.fitness(newSol) > optProb.fitness(j)) {
		        nests.replace(j, newSol);
		    }
		    nests.abandon(ABANDON_PROBABILITY);
		    
		    
		    
		    t++;
		}

	}

	public SolutionSet getSolutions() {
		return null;
	}

	public CSSolution randWalk(CSSolution seed) {
		return seed;
	}
}
