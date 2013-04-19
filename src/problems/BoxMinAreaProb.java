package problems;

import java.util.ArrayList;
import solutions.*;

public class BoxMinAreaProb extends OptimizationProblem {

	final double boxV;
	
	public BoxMinAreaProb(double volume) {
		this.boxV = volume;
	}
	
	public BoxMinAreaProb() {
		//TODO: RANDOM
		this.boxV = 1000;
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
	public boolean withinConstraints(Solution s) {
		ArrayList<Double> coefs = s.getCoefs();
        double x = coefs.get(0);
        double y = coefs.get(0);
		if (x > 0 &&
			y > 0 &&
			x * y < boxV) {
			return true;
		}
		else {
			return false;
		}
	}
}
