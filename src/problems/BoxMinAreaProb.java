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
		ArrayList<Double> var = s.getVars();
        double x = var.get(0);
        double y = var.get(1);
        double z = 1000 / x / y;
		double area = 2*x*y + 2*y*z + 2*x*z;
        
        return area;
	}

	@Override
	public boolean withinCustomConstraints(Solution s) {
		ArrayList<Double> vars = s.getVars();
        double x = vars.get(0);
        double y = vars.get(0);
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
