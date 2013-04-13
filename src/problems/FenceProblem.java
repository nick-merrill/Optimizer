package problems;

import java.util.ArrayList;

import solutions.Solution;

public class FenceProblem extends OptimizationProblem {
    private double fenceLength;
    
    public FenceProblem(double fenceLength) {
        this.fenceLength = fenceLength;
    }
    
    // We maximize for area.
    public double fitness(Solution sol) {
        ArrayList<Double> coefs = sol.getCoefs();
        double area = coefs.get(0) * coefs.get(1);
        
        return area;
    }
    
    public int getNumVar() {
        return 2;
    }
}
