package problems;

import java.util.ArrayList;

import solutions.Solution;

public abstract class OptimizationProblem {
	protected double scalingFactor = 1;
	
	protected ConstraintSet constraints;
	
	public OptimizationProblem() {
	    this.constraints = new ConstraintSet();
	}
	
	/**
	 * Returns the number of variables for the problem. For example,
	 * in a bivariate problem, getNumVar() would return 2.
	 */
	public abstract int getNumVar();
	
	/**
	 * Returns the fitness of the solution. An OptimizationAlgorithm will
	 * optimize for this value to be a *maximum*.
	 */
	public abstract double fitness(Solution s);
	
	/**
	 * Should return true if the solution is within any constraints of the
	 * problem that are more complicated than simple variable bounding.
	 */
	public abstract boolean withinCustomConstraints(Solution s);
	
	/**
	 * Should return a user-friendly string or Json of the solution.
	 */
	public abstract String solToString(Solution s);
	public String solToJson(Solution s){
		return null;
	}
	
	public double getScalingFactor() {
		return scalingFactor;
	}
	
	/**
	 * Provides bounds for one variable of the problem. Reasonable bounds
	 * should ideally be set for every optimization problem to ensure
     * the most efficient optimization.
     * The main use of the Constraint class is to provide a means of random
     * number generation for variable values.
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
	    
	    public ConstraintSet() {
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
	    public boolean add(Constraint varConstraint) {
	        int varIndex = varConstraint.getVarIndex();
	        int i = this.indexOfConstraintFor(varIndex);
	        if (i == -1) {
	            // Constraint does not yet exist, so creates new constraint.
	            this.constraints.add(varConstraint);
	            return false;
	        } else {
	            // Constraint already exists, so replace the old constraint.
	            this.constraints.set(i, varConstraint);
	            return true;
	        }
	    }
	    
	    public Constraint getConstraintFor(int varIndex) throws Exception {
	        if (!this.hasConstraintFor(varIndex)) {
	            throw new Exception("Constraint not initialized.");
	        }
	        return this.constraints.get(this.indexOfConstraintFor(varIndex));
	    }
	}
	
	private Constraint getConstraint(int varIndex) {
        try {
            return this.constraints.getConstraintFor(varIndex);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(2);
            return null; // never reached
        }
	}
	public final double getMinVar(int varIndex) {
	    Constraint varConstraint;
        varConstraint = this.getConstraint(varIndex);
	    return varConstraint.getMin();
	}
	public final double getMaxVar(int varIndex) {
	    Constraint varConstraint;
        varConstraint = this.getConstraint(varIndex);
	    return varConstraint.getMax();
	}
	
	/* Returns true if the variable is within the constraint's min and max, inclusive. */
	private boolean withinConstraints(Solution sol, int varIndex) {
	    double var = sol.getVars().get(varIndex);
        Constraint constraint = this.getConstraint(varIndex);
        return constraint.getMin() <= var && var <= constraint.getMax();
	}
	
	/**
	 * Returns true if entire solution meets both any defined variable constraints
	 * as well as the custom constraints defined by concrete problems.
	 */
	public boolean withinConstraints(Solution sol) {
	    for (int i = 0; i < this.getNumVar(); i++) {
	        if (!withinConstraints(sol, i)) return false;
	    }
	    /* If each variable is within constraints, then verify entire solution
	     * is within the problem's custom constraints. */
	    return this.withinCustomConstraints(sol);
	}
}
