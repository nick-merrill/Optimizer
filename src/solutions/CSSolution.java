package solutions;

import java.util.ArrayList;
import java.util.Random;

import problems.OptimizationProblem;

public class CSSolution extends Solution {
    
    public CSSolution(int numVars) {
        super(numVars);
    }
    
    public CSSolution(ArrayList<Double> coefs) {
        super(coefs);
    }

    /**
     * Sets solution with random coefficients.
     */
    public void setAsRandSol(OptimizationProblem optProb) {
        for (int i = 0; i < this.numVars; i++) {
            // TODO: generate legitimate random number, depending on optProb
            this.coefs.set(i, rand.nextDouble() * 100);
        }
    }
}