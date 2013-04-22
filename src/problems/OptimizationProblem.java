package problems;

import java.util.ArrayList;

import solutions.Solution;

public abstract class OptimizationProblem {
	protected double scalingFactor = 1;
	
	protected ConstraintSet constraints;
	
	public abstract int getNumVar();
	public abstract double fitness(Solution s);
	
	public double getScalingFactor() {
		return scalingFactor;
	}
	
	/**
	 * Provides bounds for one variable of the problem. Reasonable bounds
	 * should ideally be set for every optimization problem to ensure
	 * the most efficient optimization.
	 */
	public class Constraint {
	    int coefIndex;
	    double min, max;
	    
	    Constraint(int coefIndex, double min, double max) {
	        this.min = min;
	        this.max = max;
	    }
	    
	    public int getCoefIndex() {
	        return this.coefIndex;
	    }
	    public double getMin() {
	        return this.min;
	    }
	    public double getMax() {
	        return this.max;
	    }
	}
	
	public class ConstraintSet {
	    ArrayList<Constraint> constraints;
	    
	    ConstraintSet() {
	        this.constraints = new ArrayList<Constraint>();
	    }
	    
	    // Returns index of Constraint for coefIndex or -1 if constraint is not set.
	    private int indexOfConstraintFor(int coefIndex) {
	        int size = this.constraints.size();
	        if (size > 0) {
    	        for (int i = 0; i < size; i++) {
    	            if (this.constraints.get(i).getCoefIndex() == coefIndex) return i;
    	        }
	        }
	        return -1;
	    }
	    
	    // Returns true if a constraint exists for the coefIndex.
	    public boolean hasConstraintFor(int coefIndex) {
	        return this.indexOfConstraintFor(coefIndex) != -1;
	    }
	    
	    // Returns true if old constraint was replaced due to add.
	    public boolean add(int coefIndex, Constraint coefConstraint) {
	        int i = this.indexOfConstraintFor(coefIndex);
	        if (i != -1) {
	            // Constraint does not yet exist, so creates new constraint.
	            this.constraints.add(coefConstraint);
	            return false;
	        } else {
	            // Constraint already exists, so replace the old constraint.
	            this.constraints.set(i, coefConstraint);
	            return true;
	        }
	    }
	    
	    // TODO: throw error if constraint does not exist for coefIndex.
	    public Constraint getConstraintFor(int coefIndex) {
	        return(this.constraints.get(this.indexOfConstraintFor(coefIndex)));
	    }
	}
	
	private Constraint getConstraint(int coefIndex) {
	    if (this.constraints.hasConstraintFor(coefIndex))
	        return this.constraints.getConstraintFor(coefIndex);
	    else
	        // TODO: provide better default coefficient constraints
	        return new Constraint(coefIndex, -1000, 1000);
	}
	public final double getMinCoef(int coefIndex) {
	    Constraint coefConstraint = this.getConstraint(coefIndex);
	    return coefConstraint.getMin();
	}
	public final double getMaxCoef(int coefIndex) {
	    Constraint coefConstraint = this.getConstraint(coefIndex);
	    return coefConstraint.getMax();
	}
	
	// Returns true if the variable is within the constraint's min and max, inclusive.
	// Also returns true if no constraint exists for the variable.
	public boolean withinConstraints(Solution sol, int coefIndex) {
	    double coef = sol.getCoefs().get(coefIndex);
	    if (this.constraints.hasConstraintFor(coefIndex)) {
	        Constraint constraint = this.constraints.getConstraintFor(coefIndex);
	        return constraint.getMin() <= coef && coef <= constraint.getMax();
	    } else {
	        return true;
	    }
	}
	
	/** Returns true if entire solution meets the defined constraints. */
	public boolean withinConstraints(Solution sol) {
	    for (int i = 0; i < this.getNumVar(); i++) {
	        if (!withinConstraints(sol, i)) return false;
	    }
	    return true;
	}
	
}
