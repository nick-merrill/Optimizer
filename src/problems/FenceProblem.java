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
        double side1 = coefs.get(0);
        double side3 = fenceLength - 2*side1;
        double area = side1 * side3;
        
        return area;
    }
    
    
    
    
    public int getNumVar() {
        return 1;
    }

	@Override
	public boolean contraints(Solution s) {
		// TODO Auto-generated method stub
		return false;
	}
}
