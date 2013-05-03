package algorithms;

import java.util.ArrayList;
import java.util.Random;

import problems.OptimizationProblem;
import solutions.CSSolution;
import solutions.CSSolutionSet;
import solutions.Solution;

public class CuckooSearchOpt extends OptimizationAlgorithm {

    private CSSolutionSet solutions;
    protected final int N_NESTS;					//number of nests (solutions)
    protected final int N_OPTIMIZATIONS;			//number of generations
    protected final double ABANDON_PROBABILITY;		//percentage of worst solutions discarded
    private final int MAX_RANDOM_ATTEMPTS;			//maximum attempts to create a new random solution
    
    public CuckooSearchOpt() {
    	N_NESTS = 15;
    	//56000
		N_OPTIMIZATIONS = 177000;
		ABANDON_PROBABILITY = 0.25;
		MAX_RANDOM_ATTEMPTS = 1000;
		
		//for collecting data - delete afterwards
		fitnesses = new ArrayList<Double>(N_OPTIMIZATIONS/NUM_DATA+1);
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
		solutions = new CSSolutionSet(N_NESTS, NUM_VAR);
		solutions.initializeWithRandomSols(optProb);
		
		Random rand = new Random();

		int numRuns = 0;
		int tries;
		for (int t = 0; t < N_OPTIMIZATIONS; t++) {
			CSSolution i = solutions.getRandSol();
			CSSolution newSol;
			tries = 0;
			do {
				if (tries > MAX_RANDOM_ATTEMPTS) {
		   //       TODO  throw new Exception("Could not generate new random solution! Perhaps you should widen your constraints.");
		            System.out.printf("Could not generate new random solution! Perhaps you should widen your constraints.");
		            System.exit(1);;
				}
    		    newSol = i.randomWalk(optProb, "");
    		    /* If the random walk resulted in a solution that is not within constraints,
    		     * then try another random walk from the original solution. */
    		    tries++;
			} while(!optProb.withinConstraints(newSol));
		    
		    int j = rand.nextInt(solutions.getNumSols());
		    Solution jSol = solutions.getSol(j);
		    
		    // TODO: use solutions' instance data to get the fitness values to avoid unnecessary calculations
		    if (optProb.fitness(newSol) > optProb.fitness(jSol))
		        solutions.replace(j, newSol);
		    
		    // Resets worst solutions to random values.
		    solutions.abandonWorstSols(optProb, ABANDON_PROBABILITY);
		    
		    numRuns++;
		    solutions.setNumRuns(numRuns);
		    
		    //for collecting data - TODO: delete afterwards
		    if((t+1)%(N_OPTIMIZATIONS/NUM_DATA)==0) {
		    	fitnesses.add(new Double(solutions.getMostFitSolution(optProb).getFitness()));
		    }
		}
	}
	
	// TODO: prevent returning null. Instead throw an exception. 
	public CSSolutionSet getSolutions(OptimizationProblem optProb) {
	    solutions.sortByFitness(optProb);
        return solutions;
    }
}
