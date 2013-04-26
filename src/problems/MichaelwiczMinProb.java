package problems;

import java.util.ArrayList;
import solutions.Solution;

/**
 * Optimizes for the *minimum* of the bivariate Michaelwicz function,
 * letting m = 10 and constraining x and y between 0 and 5, inclusive.
 */
public class MichaelwiczMinProb extends OptimizationProblem {
	
    final double M = 10.;
    
    @Override
    public int getNumVar() {
        return 2;
    }

    @Override
    /**
     * Returns the negative of the Michaelwicz function in order to
     * minimize the function.
     */
    public double fitness(Solution s) {
        ArrayList<Double> coefs = s.getCoefs();
        double x = coefs.get(0);
        double y = coefs.get(1);
        
        double michaelwicz =
            -Math.sin(x) * Math.pow(Math.sin(  Math.pow(x, 2) / Math.PI), 2*M) -
             Math.sin(y) * Math.pow(Math.sin(2*Math.pow(y, 2) / Math.PI), 2*M);
        
        // Negates the evaluation in order to optimize for the *minimum*        
        return -michaelwicz;
    }

    /** Returns true if x is between low and high, inclusively. */
    private boolean isInRange(double x, double low, double high) {
        return x >= low && x <= high;
    }
    
    /** Returns true if x and y are between 0 and 5. */
    @Override
    public boolean withinConstraints(Solution s) {
        ArrayList<Double> coefs = s.getCoefs();
        return this.isInRange(coefs.get(0), 0., 5.) &&
               this.isInRange(coefs.get(1), 0., 5.);
    }

}
