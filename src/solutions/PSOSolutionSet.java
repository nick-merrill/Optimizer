package solutions;

import java.util.ArrayList;
import java.util.Random;

import problems.OptimizationProblem;

public class PSOSolutionSet extends SolutionSet {
    private ArrayList<PSOSolution> solutions;
    private Random rand;
    private PSOSolution bestSol;
	
	public PSOSolutionSet(int nParticles, int numVars, OptimizationProblem optProb) {
        N_SOL = nParticles;
        this.rand = new Random();
        solutions = new ArrayList<PSOSolution>(nParticles);
        super.solutions = solutions;
        
        for (int i = 0; i < nParticles; i++) {
            
        	this.solutions.add(new PSOSolution(numVars));
        	PSOSolution currSol = this.solutions.get(i);
        	
        	//sets a random solution according to bounds
    		currSol.setAsRandSol(optProb);
        	currSol.setBestPos();
        	
        	currSol.evalFitness(optProb);
            
        	//sets the set's globally best position
            if(i==0 || bestSol.getFitness() > currSol.getFitness()) {
            	//sets the best solution and its fitness
            	bestSol = new PSOSolution(currSol.getVars());
            	bestSol.evalFitness(optProb);
            }
            
            ArrayList<Double> currVel = currSol.getVelocity();
            
            //random velocity
            for (int j=0; j<numVars; j++) {
            	currVel.add(2*(rand.nextDouble()-0.5) * (optProb.getMaxVar(j) - optProb.getMinVar(j)));
            }
        }
    }
	
	@Override
	public Solution getMostFitSolution(OptimizationProblem optProb) {
		//return bestSol.getBestPosSol();
		return bestSol;
	}
	
	public PSOSolution getBestSol() {
		return bestSol;
	}
	
	public void setBestSol(PSOSolution sol) {
		bestSol = new PSOSolution(sol.getVars());
	}

}
