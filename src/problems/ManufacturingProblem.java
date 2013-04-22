package problems;

import solutions.Solution;
import java.util.ArrayList;

/** 
 * 	The manufacturing problem is always a problem of maximizing 
 *	profit with limited resources.  In this problem, there are 
 *	two products, X and Y, and three resources, A, B, and C.
 *	Each resource is limited to a said amount, and the constraints
 *	are the equations that represent these limits.  For example,
 *	if there is only 100 units of wood, and X uses 10 units per 
 *	item while Y uses 5 units, the constraint for wood would be
 *	W >= 10X + 5Y.  The constraint of non-negativity is implied.
 */

public class ManufacturingProblem extends OptimizationProblem {
	private double constraintA; 
	private double constraintB;
	private double constraintC;
	private double profitX;
	private double profitY;
	private double usageAbyX;
	private double usageAbyY;
	private double usageBbyX;
	private double usageBbyY;
	private double usageCbyX;
	private double usageCbyY;
	
	/**
	 * Constructs a ManufacturingProblem, where only constraints are inputed
	 */
	public ManufacturingProblem(double constraintA, double constraitB, double constraintC) {
		this.constraintA = constraintA;
		this.constraintB = constraintB;
		this.constraintC = constraintC;
	}

	/**
	 * Constructs a ManufacturingProblem, where constraints and profits are inputed
	 */
	public ManufacturingProblem(double constraintA, double constraitB, double constraintC, 
			double profitX, double profitY) {
		this.constraintA = constraintA;
		this.constraintB = constraintB;
		this.constraintC = constraintC;
		this.profitX = profitX;
		this.profitY = profitY;
	}
	
	/**
	 * Constructs a ManufacturingProblem, where only constraints, profits, 
	 * and material usages are inputed
	 */
	public ManufacturingProblem(double constraintA, double constraitB, double constraintC, 
			double profitX, double profitY, double usageAbyX, double usageAbyY, double usageBbyX, 
			double usageBbyY, double usageCbyX, double usageCbyY) {
		this.constraintA = constraintA;
		this.constraintB = constraintB;
		this.constraintC = constraintC;
		this.profitX = profitX;
		this.profitY = profitY;
		this.usageAbyX = usageAbyX;
		this.usageAbyY = usageAbyY;
		this.usageBbyX = usageBbyX;
		this.usageBbyY = usageBbyY;
		this.usageCbyX = usageCbyX;
		this.usageCbyY = usageCbyY;
	}
	
	private double profit(Solution sol) {
		ArrayList<Double> coefs = sol.getCoefs();
		double varX = coefs.get(0);
		double varY = coefs.get(1);
		return profitX * varX + profitY * varY;
	}

	/**
	 * Returns the profit, as the objective is to maximize that.
	 */
	public double fitness(Solution sol) {
		return profit(sol);
	}

	/** 
	 * A solution is within constraints if the number of items produced
	 * for each product are greater than zero.  In addition, the sum of 
	 * the material used for each resource must be less than the constraint
	 * of that resource.
	 */
	public boolean withinConstraints(Solution sol) {
		ArrayList<Double> coefs = sol.getCoefs();
		double varX = coefs.get(0);
		double varY = coefs.get(1);
		return (varX > 0 && varY > 0 && 
				usageAbyX * varX + usageAbyY * varY <= constraintA &&
				usageBbyX * varX + usageBbyY * varY <= constraintB &&
				usageCbyX * varX + usageCbyY * varY <= constraintC);
	}

	public int getNumVar() {
		return 2;
	}
	
}
