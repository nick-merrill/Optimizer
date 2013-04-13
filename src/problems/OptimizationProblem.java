package problems;

import solutions.Solution;

public abstract class OptimizationProblem {
	public abstract int getNumVar();
	public abstract double fitness(Solution s);
	public abstract boolean withinConstraints(Solution s);
}
