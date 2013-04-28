package UIs;

import java.util.ArrayList;

import solutions.Solution;
import problems.OptimizationProblem;

public abstract class OptimizationUI {
    
    protected ArrayList<OptimizationProblem> solvableProblems;
    
    public OptimizationUI(ArrayList<OptimizationProblem> solvableProblems) {
        this.solvableProblems = solvableProblems;
    }
    
    public abstract void requestGivens(OptimizationProblem problem);
    public abstract void printSolution(Solution solution);

}
