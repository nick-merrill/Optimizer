package problems;

import java.util.ArrayList;
import java.util.Random;

import solutions.*;
import exceptions.*;

/**
 * The minimum area box problem takes a given volume 
 * (or a randomly generated one if it is not specified)
 * and finds the minimum surface area possible for a 
 * rectangular prism of this volume.  Of course, we know
 * the solution should be a cube, so this is a perfect
 * problem on which to test our algorithms.
 */
public class BoxMinAreaProb extends OptimizationProblem {

	final double boxV;
	
	public BoxMinAreaProb(double volume) throws PositiveNumberInputException {
		if (!(volume > 0)) {
		    throw new PositiveNumberInputException("volume");
		}
		this.boxV = volume;
		this.constraints.add(new Constraint(0,0,volume));
		this.constraints.add(new Constraint(1,0,volume));
	}
	
	// generates a random volume if no volume is specified.
	private Random rand = new Random();
	public BoxMinAreaProb() {
		this.boxV = rand.nextDouble() * 1000;
	}
	
	public int getNumVar() {
		return 2;
	}

	public double surfaceArea(Solution s) {
		ArrayList<Double> var = s.getVars();
        double x = var.get(0);
        double y = var.get(1);
        double z = 1000 / x / y;
		return 2*x*y + 2*y*z + 2*x*z;
	}
		
	/** 
	 *  The fitness of the solution is defined as the minimal 
	 *  surface area required.
	 */
	public double fitness(Solution s) {
		return -surfaceArea(s);
		//TODO -- do we want to return the negative of this, or the inverse, or what?
	}

	/** 
	 * A solution is within constraints if the lengths of each side are greater 
	 * than zero, and the area of the x-y plane is less than the boxV.
	 */
	@Override
	public boolean withinCustomConstraints(Solution s) {
		ArrayList<Double> vars = s.getVars();
        double x = vars.get(0);
        double y = vars.get(1);

		if (x > 0 &&
			y > 0 &&
			x * y < boxV) {
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * Returns the length of the third side of the box, given two other sides.
	 */
	public double side3(double x, double y) {
	    return boxV / x / y;
	}
	public double side3(Solution sol) {
	    ArrayList<Double> vars = sol.getVars();
	    return side3(vars.get(0), vars.get(1));
	}

    @Override
    public String solToString(Solution s) {
        ArrayList<Double> vars = s.getVars();
        return String.format("Your box should be %.2f by %.2f by %.2f units.", vars.get(0), vars.get(1), this.side3(s));
    }
}
