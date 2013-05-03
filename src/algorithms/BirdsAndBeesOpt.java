package algorithms;

import java.util.ArrayList;
import java.util.Random;
import problems.OptimizationProblem;
import solutions.*;

public class BirdsAndBeesOpt extends OptimizationAlgorithm {

	private CSPSOSolutionSet solutions;
	protected final int N_NESTS;					//number of nests (solutions)
    protected final int N_OPTIMIZATIONS;			//number of generations
    protected final double ABANDON_PROBABILITY;		//percentage of worst solutions discarded

	private double inertiaWeight;
	private double cognitiveWeight;
	private double socialWeight;
	
	private final int MAX_RANDOM_ATTEMPTS;
	
	
	
	public BirdsAndBeesOpt() {
		N_NESTS = 50;
		//3900
		N_OPTIMIZATIONS = 4000;
		ABANDON_PROBABILITY = 0.25;
		inertiaWeight = 0.7;
		cognitiveWeight = 1.5;
		socialWeight = 1.5;
		MAX_RANDOM_ATTEMPTS = 1000;
    }
	
	/* 
	 * Objective function 
	 * Generate an initial population of host nests; 
	 * While (t<MaxGeneration) or (stop criterion)
	 *    Get a cuckoo randomly (say, i) and replace its solution by performing Lévy flights;
	 *    Evaluate its quality/fitness F_i
	 *          [For maximization, -F_i];
	 *    Choose a nest among n (say, j) randomly;
	 *    if (F_i > F_j),
	 *           Replace j by the new solution;
	 *    end if
	 *    Move cuckoo birds using the velocity functions (listed below)
	 *    A fraction (P_a) of the worse nests are abandoned and new ones are built;
	 *    Keep the best solutions/nests;
	 *    Rank the solutions/nests and find the current best;
	 *    Pass the current best solutions to the next generation;
	 * end while
	*/
	@Override
	public void solve(OptimizationProblem optProb) {
		//for collecting data - TODO: delete afterwards
		//fitnesses = new ArrayList<Double>(N_OPTIMIZATIONS/NUM_DATA+1);
		
		int NUM_VAR = optProb.getNumVar();
		solutions = new CSPSOSolutionSet(N_NESTS, NUM_VAR, optProb);
		
		Random rand = new Random();

		int t = 0;
		while (t < N_OPTIMIZATIONS) {
			CSPSOSolution iSol = (CSPSOSolution) solutions.getSol(rand.nextInt(N_NESTS));
			CSPSOSolution newSol;
			int tries = 0;
			do {
				if (tries > MAX_RANDOM_ATTEMPTS) {
		            System.out.printf("Could not generate new random solution! " +
		            		"Perhaps you should widen your constraints.");
		            System.exit(1);;
				}
    		    newSol = iSol.randomWalk(optProb, "");
    		    /* If the random walk resulted in a solution that is not within constraints,
    		     * then try another random walk from the original solution. */
    		    tries++;
			} while(!optProb.withinConstraints(newSol));
		    
		    int j = rand.nextInt(N_NESTS);
		    CSPSOSolution jSol = (CSPSOSolution) solutions.getSol(j);
		    
		    if (newSol.getFitness(optProb) > jSol.getFitness()) {
		    	solutions.replace(j, newSol);
		    	newSol = (CSPSOSolution) solutions.getSol(j);
		    	newSol.setRandVel(optProb, NUM_VAR);
		    	newSol.setBestPos();
		    }

		    
		    //PSO
		    for(int j1=0; j1<N_NESTS; j1++) {
				CSPSOSolution currSol = (CSPSOSolution) solutions.getSol(j1);
				CSPSOSolution bestSol = solutions.getBestSol();

				ArrayList<Double> currPos = currSol.getCurrPos();
				ArrayList<Double> currVel = currSol.getVelocity();
				ArrayList<Double> localVector = subLists(currSol.getBestPos(), currPos);
				ArrayList<Double> globalVector = subLists(bestSol.getVars(), currPos);
				
				for(int k=0; k<NUM_VAR; k++) {
					double rp = rand.nextDouble();
					double rg = rand.nextDouble();
					
					//Update the particle's velocity according to above formula
					currVel.set(k, inertiaWeight*currVel.get(k) 
							+ cognitiveWeight*rp*localVector.get(k)
							+ socialWeight*rg*globalVector.get(k));
					
					//Updates the particle's position, x_i = x_i + v_i
					currPos.set(k, currPos.get(k) + currVel.get(k));
					
					//If the particle goes out of bounds, pushes it back in
					if(currPos.get(k) > optProb.getMaxVar(k)) {
						currPos.set(k, optProb.getMaxVar(k));
						currVel.set(k, currVel.get(k) * -1);
					}
					else if(currPos.get(k) < optProb.getMinVar(k)) {
						currPos.set(k, optProb.getMinVar(k));
						currVel.set(k, currVel.get(k) * -1);
					}
				}
				
				currSol.evalFitness(optProb);
				
				//Updates particle's individual best solution
				if(currSol.getFitness() > currSol.getBestPosSol().getFitness(optProb))
					currSol.setBestPos();
			}
		    
		    // Resets worst solutions to random values.
		    solutions.abandonWorstSols(optProb, ABANDON_PROBABILITY);
		    
		    //updates the globally best solution
			if(solutions.getSol(0).getFitness() > solutions.getBestSol().getFitness()) {
				solutions.setBestSol((CSPSOSolution) solutions.getSol(0));
				solutions.getBestSol().evalFitness(optProb);
			}
		    
		    t++;
		    
		    //for collecting data - TODO: delete afterwards
		    /*
		    if((t+1)%(N_OPTIMIZATIONS/NUM_DATA)==0) {
		    	fitnesses.add(new Double(solutions.getBestSol().getFitness()));
		    }*/
		}

	}
	
	private ArrayList<Double> subLists(ArrayList<Double> lst1, ArrayList<Double> lst2) {
		ArrayList<Double> diff = new ArrayList<Double>(lst1.size());
		for(int i=0; i<lst1.size(); i++) {
			diff.add(lst1.get(i).doubleValue() - lst2.get(i).doubleValue());
		}
		return diff;
	}

	public CSPSOSolutionSet getSolutions(OptimizationProblem optProb) {
		solutions.sortByFitness(optProb);
        return solutions;
	}

}
