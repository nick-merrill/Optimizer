package algorithms;

import problems.OptimizationProblem;
import solutions.SolutionSet;

public abstract class OptimizationAlgorithm {
    public abstract SolutionSet newSolutionSet(SolutionSet seed);
    public abstract void solve(OptimizationProblem optProb);
    public abstract SolutionSet getSolutions();
}
