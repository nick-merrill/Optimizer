package algorithms;

import problems.OptimizationProblem;
import solutions.SolutionSet;

public abstract class OptimizationAlgorithm {
    public abstract void solve(OptimizationProblem optProb);
    public abstract SolutionSet getSolutions(OptimizationProblem optProb);
}
