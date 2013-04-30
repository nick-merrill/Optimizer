package solutions;

import java.util.ArrayList;
import java.util.Random;

import problems.OptimizationProblem;

public class PSOSolutionSet extends SolutionSet {
    private ArrayList<PSOSolution> solutions;
    private Random rand;
    private final int N_PARTICLES;
    private PSOSolution bestSol;
	
	public PSOSolutionSet(int nParticles, int numVars, OptimizationProblem optProb) {
        this.N_PARTICLES = nParticles;
        N_SOL = N_PARTICLES;
        this.rand = new Random();
        solutions = new ArrayList<PSOSolution>(N_PARTICLES);
        super.solutions = solutions;
        
        for (int i = 0; i < nParticles; i++) {
            //TODO: random initial solution should come from the problem, not solution
        	this.solutions.add(new PSOSolution(numVars));
        	PSOSolution currSol = this.solutions.get(i);
        	currSol.setAsRandSol(optProb);
        	currSol.setBestPos();
            
            if(i==0) {
            	bestSol = new PSOSolution(currSol.getVars());
            	currSol.evalFitness(optProb);
            	bestSol.evalFitness(optProb);
            	//currSol.evalBestFitness(optProb);
            }
            else if(bestSol.getFitness() < currSol.getFitness(optProb)) {
            	bestSol = new PSOSolution(currSol.getVars());
            	bestSol.evalFitness(optProb);
            	//currSol.evalBestFitness(optProb);
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
