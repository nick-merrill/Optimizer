package algorithms;

import java.util.Random;

import solutions.*;
import problems.*;

public class CuckooSearchOpt extends OptimizationAlgorithm {

    private SolutionSet solutions;
    private final int N_NESTS;				//number of nests (solutions)
    private final int N_OPTIMIZATIONS;		//number of generations
    private final double ABANDON_PROBABILITY;	//percentage of worst solutions discarded
    
    public CuckooSearchOpt() {
		N_NESTS = 5;
		N_OPTIMIZATIONS = 3000;
		ABANDON_PROBABILITY = 0.25;
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

		int NUM_VAR = optProb.getNumVar();
		solutions = new SolutionSet(N_NESTS, NUM_VAR);
		solutions.initializeWithRandomSols(optProb);
		for (int i = 0; i < 5; i++) {
		    System.out.printf("Solution %d... ", i);
		    solutions.getSol(i).print();
		}
		
		Random rand = new Random();

		int t = 0;
		while (t < N_OPTIMIZATIONS) {
			Solution i = solutions.getRandSol();
		    Solution newSol = i.randomWalk(optProb, "");
		    
		    int j = rand.nextInt(solutions.getNumSols());
		    Solution jSol = solutions.getSol(j);
		    
		    // TODO: use solutions' instance data to get the fitnesses
		    if (optProb.fitness(newSol) > optProb.fitness(jSol))
		        solutions.replace(j, newSol);
		    
		    solutions.abandonWorstSols(optProb, ABANDON_PROBABILITY);
		    
		    t++;
		}
	}

	// TODO: prevent returning null. Instead throw an exception.
	public SolutionSet getSolutions(OptimizationProblem optProb) {
	    solutions.sortByFitness(optProb);
        return solutions;
    }
}
