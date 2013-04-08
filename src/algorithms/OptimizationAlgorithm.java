package algorithms;

import solutions.SolutionSet;

public abstract class OptimizationAlgorithm {
    public abstract SolutionSet newSolutionSet(SolutionSet seed);
    public abstract void solve();
    public abstract SolutionSet getSolutions();
}
