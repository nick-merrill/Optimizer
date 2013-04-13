package problems;

import java.util.ArrayList;
import solutions.*;

public class BoxMinAreaProb extends OptimizationProblem {

	final double boxW;
	
	public BoxMinAreaProb(double volume) {
		this.boxW = volume;
	}
	
	public BoxMinAreaProb() {
		this.boxW = 1000;
	}
	
	public int getNumVar() {
		return 2;
	}

	@Override
	public double fitness(Solution s) {
		ArrayList<Double> coefs = s.getCoefs();
        double x = coefs.get(0);
        double y = coefs.get(0);
        double z = 1000 / x / y;
		double area = 2*x*y + 2*y*z + 2*x*z;
        
        return area;
	}

	@Override
	public boolean contraints(Solution s) {
		
		
		// TODO Auto-generated method stub
		return false;
	}

}
