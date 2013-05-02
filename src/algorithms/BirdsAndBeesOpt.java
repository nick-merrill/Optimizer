package algorithms;

import java.util.ArrayList;

import java.util.Random;

import problems.OptimizationProblem;
import solutions.*;

public class BirdsAndBeesOpt extends CuckooSearchOpt {

	private CSPSOSolutionSet solutions;
	private double inertiaWeight;
	private double cognitiveWeight;
	private double socialWeight;
	
	public BirdsAndBeesOpt() {
    	super();
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
    		    newSol = iSol.randomWalk(optProb, "weibull");
    		    /* If the random walk resulted in a solution that is not within constraints,
    		     * then try another random walk from the original solution. */
			} while(!optProb.withinConstraints(newSol));
		    
		    int j = rand.nextInt(N_NESTS);
		    Solution jSol = solutions.getSol(j);
		    
		    // TODO: use solutions' instance data to get the fitnesses to avoid unnecessary calculations
		    if (optProb.fitness(newSol) > optProb.fitness(jSol))
		        solutions.replace(j, newSol);
		    
		    
		    
		    
		    
		    //PSO
		    for(int j1=0; j1<N_NESTS; j1++) {
				PSOSolution currSol = (PSOSolution) solutions.getSol(j1);
				PSOSolution bestSol = solutions.getBestSol();

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
					
					//updates the globally best solution
					if(currSol.getFitness() > bestSol.getFitness()) {
						solutions.setBestSol(currSol);
						solutions.getBestSol().evalFitness(optProb);
					}
				}

				// Resets worst solutions to random values.
			    solutions.abandonWorstSols(optProb, ABANDON_PROBABILITY);
			    
			    t++;
			}
		}

	}
	
	private ArrayList<Double> subLists(ArrayList<Double> lst1, ArrayList<Double> lst2) {
		ArrayList<Double> diff = new ArrayList<Double>(lst1.size());
		for(int i=0; i<lst1.size(); i++) {
			diff.add(lst1.get(i).doubleValue() - lst2.get(i).doubleValue());
		}
		return diff;
	}

	@Override
	public CSSolutionSet getSolutions(OptimizationProblem optProb) {
		// TODO Auto-generated method stub
		return null;
	}

}
