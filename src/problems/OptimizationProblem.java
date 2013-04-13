package problems;

import solutions.*;

public abstract class OptimizationProblem {
	public abstract int getNumVar();
	public abstract double fitness(Solution s);
	public abstract boolean contraints(Solution s);
}
