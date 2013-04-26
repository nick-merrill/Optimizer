package solutions;

import java.util.ArrayList;
import java.util.Random;

import problems.OptimizationProblem;

public class PSOSolutionSet implements SolutionSet {
    private ArrayList<PSOSolution> solutions;
    private Random rand;
    private final int N_PARTICLES;
    private final int N_VARS;
    private PSOSolution bestSol;
	
	public PSOSolutionSet(int nParticles, int numVars, OptimizationProblem optProb) {
        this.N_PARTICLES = nParticles;
        this.N_VARS  = numVars;
        this.rand = new Random();
        solutions = new ArrayList<PSOSolution>(numVars);
        
        for (int i = 0; i < nParticles; i++) {
            //TODO: random initial solution should come from the problem, not solution
        	this.solutions.set(i, new PSOSolution(numVars));
            this.solutions.get(i).setBestPos();
            
            if(i==0) {
            	bestSol = solutions.get(0);
            }
            else if(optProb.fitness(bestSol) < optProb.fitness(solutions.get(i))) {
            	bestSol = solutions.get(i);
            }
        }
        
        for (int i = 0; i < nParticles; i++) {
        	this.solutions.get(i).setVelocity(//TODO)
        }
    }
	
	public PSOSolution getSol(int i) {
		return solutions.get(i);
	}
	
	public PSOSolution getBestSol() {
		return bestSol;
	}
	
	public void setBestSol(PSOSolution sol) {
		bestSol = sol;
	}

	@Override
	public void sortByFitness(OptimizationProblem optProb) {
		// TODO Auto-generated method stub
		
	}
}
