package problems;

import solutions.*;

public abstract class OptimizationProblem {
	public abstract int getNumVar();
	public abstract float fitness(Solution s);
}
