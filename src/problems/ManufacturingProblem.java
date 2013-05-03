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
	private final double constraintA, constraintB, constraintC, profitX, profitY,
	               usageAbyX, usageAbyY, usageBbyX, usageBbyY, usageCbyX,
	               usageCbyY;
	
	/**
	 * Constructs a Manufacturing Problem, where constraints, profits, 
	 * and material usages are inputed
	 */
	public ManufacturingProblem(double constraintA, double constraintB, double constraintC, 
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
		this.constraints.add(new Constraint(0,0,greatestPossibleX()));
		this.constraints.add(new Constraint(1,0,greatestPossibleY()));
	}
	private double greatestPossibleX() {
		ArrayList<Double> list = new ArrayList<Double>(3);
		list.add(constraintA / usageAbyX);
		list.add(constraintB / usageBbyX);
		list.add(constraintC / usageCbyX);
		return leastOf(list);
	}
	private double greatestPossibleY() {
		ArrayList<Double> list = new ArrayList<Double>(3);
		list.add(constraintA / usageAbyY);
		list.add(constraintB / usageBbyY);
		list.add(constraintC / usageCbyY);
		return leastOf(list);
	}
	private double leastOf(ArrayList<Double> list){
		double least = Double.MAX_VALUE;
		for (int i = 0; i < list.size(); i++){
			if (list.get(i) < least)
				least = list.get(i);
		}
		return least;
	}

	private double profit(Solution sol) {
		ArrayList<Double> vars = sol.getVars();
		double varX = vars.get(0);
		double varY = vars.get(1);
		return profitX * varX + profitY * varY;
	}

	/**
	 * Returns the profit, as the objective is to maximize that.
	 */
	@Override
	public double fitness(Solution sol) {
		return profit(sol);
	}

	/** 
	 * A solution is within constraints if the number of items produced
	 * for each product are greater than zero.  In addition, the sum of 
	 * the material used for each resource must be less than the constraint
	 * of that resource.
	 */
	@Override
	public boolean withinCustomConstraints(Solution sol) {
		ArrayList<Double> vars = sol.getVars();
		double varX = vars.get(0);
		double varY = vars.get(1);
		return (varX > 0 && varY > 0 && 
				usageAbyX * varX + usageAbyY * varY <= constraintA &&
				usageBbyX * varX + usageBbyY * varY <= constraintB &&
				usageCbyX * varX + usageCbyY * varY <= constraintC);
	}

	@Override
	public int getNumVar() {
		return 2;
	}
	
	@Override
    public String solToString(Solution sol) {
        ArrayList<Double> solVars = sol.getVars();
	    return String.format("You should produce %.1f items of Product X and %.1f items of Product Y.", 
	    		solVars.get(0), solVars.get(1));
    }
	
}
