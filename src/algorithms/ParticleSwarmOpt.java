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
		solutions = new PSOSolutionSet(N_PARTICLES, NUM_VAR, optProb);
		Random rand = new Random();
		
		//termination criterion
		for(int i = 0; i<N_RUNS; i++) {
			//Iterates through particles, updating each's position and velocity
			for(int j=0; j<N_PARTICLES; j++) {
				PSOSolution currSol = solutions.getSol(i);

				ArrayList<Double> currPos = currSol.getCurrPos();
				ArrayList<Double> currVel = currSol.getVelocity();
				ArrayList<Double> localVector = subLists(currSol.getBestPos(), currPos);
				ArrayList<Double> globalVector = subLists(solutions.getBestSol().getBestPos(), currPos);
				
				for(int k=0; k<NUM_VAR; k++) {
					double rp = rand.nextDouble();
					double rg = rand.nextDouble();
					
					//Update the particle's velocity
					currVel.set(k, inertiaWeight*currVel.get(k) 
							+ cognitiveWeight*rp*localVector.get(k)
							+ socialWeight*rg*globalVector.get(k));
				}
				
				addListsInPlace(currPos, currVel);
				
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


	public SolutionSet getSolutions(OptimizationProblem optProb) {
		solutions.sortByFitness(optProb);
        return solutions;
	}
	
}
