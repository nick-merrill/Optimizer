package solutions;

import java.util.ArrayList;

public abstract class SolutionSet {
    private ArrayList<Solution> solutions;
    
    public SolutionSet() {
        solutions = new ArrayList<Solution>();
    }
    
    public void addSolution(Solution s) {
        solutions.add(s);
    }
}
