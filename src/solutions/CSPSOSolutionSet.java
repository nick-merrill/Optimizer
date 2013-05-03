package solutions;

import java.util.ArrayList;
import java.util.Random;

import problems.OptimizationProblem;

public class CSPSOSolutionSet extends SolutionSet {
	
	protected ArrayList<CSPSOSolution> solutions;
	protected Random rand;
    protected CSPSOSolution bestSol;
	
	public CSPSOSolutionSet(int nParticles, int numVars, OptimizationProblem optProb) {
		N_SOL = nParticles;
        this.rand = new Random();
        solutions = new ArrayList<CSPSOSolution>(nParticles);
        super.solutions = this.solutions;
        
        for (int i = 0; i < nParticles; i++) {
            
        	this.solutions.add(new CSPSOSolution(numVars));
        	CSPSOSolution currSol = this.solutions.get(i);
        	
        	//sets a random solution according to bounds
    		currSol.setAsRandSol(optProb);
        	currSol.setBestPos();
        	
        	currSol.evalFitness(optProb);
            
        	//sets the set's globally best position
            if(i==0 || bestSol.getFitness() > currSol.getFitness()) {
            	//sets the best solution and its fitness
            	bestSol = new CSPSOSolution(currSol.getVars());
            	bestSol.evalFitness(optProb);
            }
            
            currSol.setRandVel(optProb, numVars);
        }
	}
	
	
	@Override
	public Solution getMostFitSolution(OptimizationProblem optProb) {
		//return bestSol.getBestPosSol();
		return bestSol;
	}
	
	public CSPSOSolution getBestSol() {
		return bestSol;
	}
	
	public void setBestSol(PSOSolution sol) {
		bestSol = new CSPSOSolution(sol.getVars());
	}
	
	public void replace(int j, CSPSOSolution sol) {
        this.solutions.set(j, sol);
    }
    
    public CSPSOSolution getRandSol() {
        return this.solutions.get(rand.nextInt(N_SOL));
    }
	
	public void abandonWorstSols(OptimizationProblem optProb, double abandonmentRatio) {
    	this.sortByFitness(optProb);
        int numToAbandon = (int) (abandonmentRatio * this.N_SOL);
        int numToKeep = this.N_SOL - numToAbandon;
        
        CSPSOSolutionSet newSols = new CSPSOSolutionSet(numToAbandon, optProb.getNumVar(), optProb);
        
        for (int i = numToKeep; i < N_SOL; i++) {
            solutions.set(i, (CSPSOSolution) newSols.getSol(i-numToKeep));
        }
    }

}
