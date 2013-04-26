package problems;

import java.util.ArrayList;
import java.util.Random;

import solutions.*;

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
	
	public BoxMinAreaProb(double volume) {
		this.boxV = volume;
	}
	
	// generates a random volume if no volume is specified.
	private Random rand = new Random();
	public BoxMinAreaProb() {
		this.boxV = rand.nextDouble() * 1000;
	}
	
	public int getNumVar() {
		return 2;
	}

	private double surfacearea(Solution s) {
		ArrayList<Double> coefs = s.getCoefs();
        double x = coefs.get(0);
        double y = coefs.get(1);
        double z = 1000 / x / y;
		return 2*x*y + 2*y*z + 2*x*z;
	}
		
	/** 
	 *  The fitness of the solution is defined as the minimal 
	 *  surface area required.
	 */
	public double fitness(Solution s) {
		return (-1)*surfacearea(s);
		//TODO -- do we want to return the negative of this, or the inverse, or what?
	}

	/** 
	 * A solution is within constraints if the lengths of each side are greater 
	 * than zero, and the area of the x-y plane is less than the boxV.
	 */
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
