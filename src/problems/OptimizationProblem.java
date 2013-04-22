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
	    int varIndex;
	    double min, max;
	    
	    Constraint(int varIndex, double min, double max) {
	        this.varIndex = varIndex;
	        this.min = min;
	        this.max = max;
	    }
	    
	    public int getVarIndex() {
	        return this.varIndex;
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
	    
	    // Returns index of Constraint for varIndex or -1 if constraint is not set.
	    private int indexOfConstraintFor(int varIndex) {
	        int size = this.constraints.size();
	        if (size > 0) {
    	        for (int i = 0; i < size; i++) {
    	            if (this.constraints.get(i).getVarIndex() == varIndex) return i;
    	        }
	        }
	        return -1;
	    }
	    
	    // Returns true if a constraint exists for the varIndex.
	    public boolean hasConstraintFor(int varIndex) {
	        return this.indexOfConstraintFor(varIndex) != -1;
	    }
	    
	    // Returns true if old constraint was replaced due to add.
	    public boolean add(int varIndex, Constraint varConstraint) {
	        int i = this.indexOfConstraintFor(varIndex);
	        if (i != -1) {
	            // Constraint does not yet exist, so creates new constraint.
	            this.constraints.add(varConstraint);
	            return false;
	        } else {
	            // Constraint already exists, so replace the old constraint.
	            this.constraints.set(i, varConstraint);
	            return true;
	        }
	    }
	    
	    // TODO: throw error if constraint does not exist for varIndex.
	    public Constraint getConstraintFor(int varIndex) {
	        return(this.constraints.get(this.indexOfConstraintFor(varIndex)));
	    }
	}
	
	private Constraint getConstraint(int varIndex) {
	    if (this.constraints.hasConstraintFor(varIndex))
	        return this.constraints.getConstraintFor(varIndex);
	    else
	        // TODO: provide better default variable constraints
	        return new Constraint(varIndex, -1000, 1000);
	}
	public final double getMinVar(int varIndex) {
	    Constraint varConstraint = this.getConstraint(varIndex);
	    return varConstraint.getMin();
	}
	public final double getMaxVar(int varIndex) {
	    Constraint varConstraint = this.getConstraint(varIndex);
	    return varConstraint.getMax();
	}
	
	// Returns true if the variable is within the constraint's min and max, inclusive.
	// Also returns true if no constraint exists for the variable.
	public boolean withinConstraints(Solution sol, int varIndex) {
	    double var = sol.getVars().get(varIndex);
	    if (this.constraints.hasConstraintFor(varIndex)) {
	        Constraint constraint = this.constraints.getConstraintFor(varIndex);
	        return constraint.getMin() <= var && var <= constraint.getMax();
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
