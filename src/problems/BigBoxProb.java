package problems;

import java.util.ArrayList;

import solutions.Solution;

public class BigBoxProb extends OptimizationProblem {

	final double boxW, boxL;
	
	public BigBoxProb(double width, double length) {
		this.boxW = width;
		this.boxL = length;
	}
	
	public BigBoxProb() {
		this.boxW = 10;
		this.boxL = 20;
	}
	
	@Override
	public int getNumVar() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double fitness(Solution s) {
		ArrayList<Double> coefs = s.getCoefs();
        double volume = coefs.get(0) * coefs.get(1);
        
        return area;
	}

	@Override
	public boolean contraints(Solution s) {
		// TODO Auto-generated method stub
		return false;
	}

}
