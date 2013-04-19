package algorithms;

import java.util.Random;

import solutions.*;
import problems.*;

public class CuckooSearchOpt extends OptimizationAlgorithm {

    private SolutionSet solutions;
    
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
		final int N_OPTIMIZATIONS = 100;

		// probability of discovery
		final double ABANDON_PROBABILITY = 0.25;

		final int NUM_VAR = optProb.getNumVar();
		CSSolutionSet solutions = new CSSolutionSet(N_NESTS, NUM_VAR);
		
		Random rand = new Random();

		int t = 0;
		while (t < N_OPTIMIZATIONS) {
			CSSolution i = solutions.getRandSol();
		    CSSolution newSol = randWalk(i);
		    
		    int j = rand.nextInt(solutions.getNumSols());
		    CSSolution jSol = solutions.getSol(j);
		    
		    if (optProb.fitness(newSol) > optProb.fitness(jSol)) {
		        solutions.replace(j, newSol);
		    }
		    
		    solutions.abandonWorstSols(ABANDON_PROBABILITY);
		    
		    t++;
		}
	}

	// TODO
	public CSSolution randWalk(CSSolution seed) {
		return seed;
	}

	// TODO: prevent returning null. Instead throw an exception.
	public SolutionSet getSolutions() {
	    // TODO: ensure solutions are sorted, most fit to least fit.
        return solutions;
    }
}
