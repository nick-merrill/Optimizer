package algorithms;

import java.util.ArrayList;
import java.util.Random;

import problems.OptimizationProblem;
import solutions.*;

public class ParticleSwarmOpt extends OptimizationAlgorithm{

	private PSOSolutionSet solutions;
	private final int N_PARTICLES;
	private final int N_RUNS;
	private double inertiaWeight;
	private double cognitiveWeight;
	private double socialWeight;
	
	public ParticleSwarmOpt() {
		N_PARTICLES = 10;
		N_RUNS = 500;
		inertiaWeight = 0.99;
		cognitiveWeight = 1.5;
		socialWeight = 1.5;
	}
	
	@Override
	public void solve(OptimizationProblem optProb) {
		int NUM_VAR = optProb.getNumVar();
		
		//Initializes the swarm with random solutions and velocities
		solutions = new PSOSolutionSet(N_PARTICLES, NUM_VAR, optProb);
		
		Random rand = new Random();
		
		//number of iterations, or "generations", to termination criterion
		for(int i = 0; i<N_RUNS; i++) {
			//Iterates through particles, updating each's position and velocity
			//Velocity: v_i,d = w * v_i,d + c1 * rp * (p_i,d - x_i,d) + c2 * rg * (g_d - x_i,d)
			//w = inertiaWeight
			//c1 = cognitiveWeight
			//c2 = socialWeight
			//rp, rg are randomly generated numbers for each dimension
			for(int j=0; j<N_PARTICLES; j++) {
				PSOSolution currSol = (PSOSolution) solutions.getSol(j);

				ArrayList<Double> currPos = currSol.getCurrPos();
				ArrayList<Double> currVel = currSol.getVelocity();
				ArrayList<Double> localVector = subLists(currSol.getBestPos(), currPos);
				ArrayList<Double> globalVector = subLists(solutions.getBestSol().getBestPos(), currPos);
				
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
				
				//Updates particle's best solution and globally best solution
				if(optProb.fitness(currSol) > optProb.fitness(currSol.getBestPosSol())) {
					currSol.setBestPos();
					if(optProb.fitness(currSol) > solutions.getBestSol().getFitness()) {
						solutions.setBestSol(currSol);
						
					}
				}

				
			}
			solutions.getBestSol().evalFitness(optProb);
			
			solutions.getBestSol().printAll();
		}
	}
	
	private ArrayList<Double> subLists(ArrayList<Double> lst1, ArrayList<Double> lst2) {
		ArrayList<Double> diff = new ArrayList<Double>(lst1.size());
		for(int i=0; i<lst1.size(); i++) {
			diff.add(lst1.get(i).doubleValue() - lst2.get(i).doubleValue());
		}
		return diff;
	}
	
	public PSOSolutionSet getSolutions(OptimizationProblem optProb) {
	    solutions.sortByFitness(optProb);
        return solutions;
    }
}
