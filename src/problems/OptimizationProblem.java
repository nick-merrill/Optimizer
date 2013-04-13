package problems;

import solutions.Solution;

public abstract class OptimizationProblem {
	public int getNumVar() {
		return 0;
	}
	public double fitness(Solution s) {
		return 0;
	}
}
