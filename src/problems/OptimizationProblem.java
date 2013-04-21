package problems;

import solutions.Solution;

public abstract class OptimizationProblem {
	protected double scalingFactor = 1;
	
	public abstract int getNumVar();
	public abstract double fitness(Solution s);
	public abstract boolean withinConstraints(Solution s);
	
	public double getScalingFactor() {
		return scalingFactor;
	}
}
