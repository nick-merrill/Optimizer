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
		N_PARTICLES = 100;
		N_RUNS = 1000;
		inertiaWeight = 0.7;
		cognitiveWeight = 1.4;
		socialWeight = 1.4;
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
			//w = intertiaWeight
			//c1 = cognitiveWeight
			//c2 = socialWeight
			//rp, rg are randomly generated numbers for each dimension
			for(int j=0; j<N_PARTICLES; j++) {
				PSOSolution currSol = (PSOSolution) solutions.getSol(i);

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
				}
				
				//Updates the particle's position, x_i = x_i + v_i
				addListsInPlace(currPos, currVel);
				
				//Updates particle's best solution and globally best solution
				if(optProb.fitness(currSol) < optProb.fitness(currSol.getBestPosSol())) {
					currSol.setBestPos();
					if(optProb.fitness(currSol) < optProb.fitness(solutions.getBestSol())) {
						solutions.setBestSol(currSol);
					}
				}
			}
		}
	}
	

	private void addListsInPlace(ArrayList<Double> lst1, ArrayList<Double> lst2) {
		for(int i=0; i<lst1.size(); i++) {
			lst1.set(i, lst1.get(i).doubleValue() + lst2.get(i).doubleValue());
		}
	}
	
	private ArrayList<Double> addLists(ArrayList<Double> lst1, ArrayList<Double> lst2) {
		ArrayList<Double> sum = new ArrayList<Double>(lst1.size());
		for(int i=0; i<lst1.size(); i++) {
			sum.add(lst1.get(i).doubleValue() + lst2.get(i).doubleValue());
		}
		return sum;
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
