package solutions;

import problems.OptimizationProblem;

public class CSPSOSolutionSet extends PSOSolutionSet {
	
	public CSPSOSolutionSet(int nParticles, int numVars, OptimizationProblem optProb) {
		super(nParticles, numVars, optProb);
	}
	
	public void replace(int j, CSPSOSolution sol) {
        this.solutions.set(j, sol);
    }
    
    public PSOSolution getRandSol() {
        return this.solutions.get(rand.nextInt(N_SOL));
    }
	
	public void abandonWorstSols(OptimizationProblem optProb, double abandonmentRatio) {
    	this.sortByFitness(optProb);
        int numToAbandon = (int) (abandonmentRatio * this.N_SOL);
        int numToKeep = this.N_SOL - numToAbandon;
        for (int i = numToKeep; i < N_SOL; i++) {
            solutions.get(i).setAsRandSol(optProb);
        }
    }

}
