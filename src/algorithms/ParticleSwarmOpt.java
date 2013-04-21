package algorithms;

import problems.OptimizationProblem;
import solutions.*;

public class ParticleSwarmOpt extends OptimizationAlgorithm{

	private PSOSolutionSet solutions;
	private final int N_PARTICLES;
	private final int N_RUNS;
	
	public ParticleSwarmOpt() {
		N_PARTICLES = 100;
	}
	
	@Override
	public void solve(OptimizationProblem optProb) {
		int NUM_VAR = optProb.getNumVar();
		solutions = new PSOSolutionSet(N_PARTICLES, NUM_VAR);
		
		//termination criterion
		for(int i = 0; i<N_RUNS; i++) {
			for
		}
	}

	@Override
	public SolutionSet getSolutions() {
		return solutions;
	}
	
}
