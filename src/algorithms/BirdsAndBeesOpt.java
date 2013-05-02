package algorithms;

import java.util.ArrayList;
import java.util.Random;
import problems.OptimizationProblem;
import solutions.*;

public class BirdsAndBeesOpt extends OptimizationAlgorithm {

	private CSPSOSolutionSet solutions;
	protected final int N_NESTS;					//number of nests (solutions)
    protected final int N_OPTIMIZATIONS;			//number of generations
    protected final double ABANDON_PROBABILITY;	//percentage of worst solutions discarded

	private double inertiaWeight;
	private double cognitiveWeight;
	private double socialWeight;
	
	public BirdsAndBeesOpt() {
		N_NESTS = 50;
		N_OPTIMIZATIONS = 2000;
		ABANDON_PROBABILITY = 0.25;
		inertiaWeight = 0.7;
		cognitiveWeight = 1.5;
		socialWeight = 1.5;
    }
	
	@Override
	public void solve(OptimizationProblem optProb) {
		int NUM_VAR = optProb.getNumVar();
		solutions = new CSPSOSolutionSet(N_NESTS, NUM_VAR, optProb);
		
		Random rand = new Random();

		int t = 0;
		while (t < N_OPTIMIZATIONS) {
			CSPSOSolution iSol = (CSPSOSolution) solutions.getSol(rand.nextInt(N_NESTS));
			CSPSOSolution newSol;
			do {
    		    newSol = iSol.randomWalk(optProb, "");
    		    /* If the random walk resulted in a solution that is not within constraints,
    		     * then try another random walk from the original solution. */
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
				if(currSol.getFitness() > currSol.getBestPosSol().getFitness(optProb)) {
					currSol.setBestPos();
				}
			}
		    
		    // Resets worst solutions to random values.
		    solutions.abandonWorstSols(optProb, ABANDON_PROBABILITY);
		    
		    //updates the globally best solution
			if(solutions.getSol(0).getFitness() > solutions.getBestSol().getFitness()) {
				solutions.setBestSol((CSPSOSolution) solutions.getSol(0));
				solutions.getBestSol().evalFitness(optProb);
			}
		    
		    t++;
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
