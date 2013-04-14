package solutions;

import java.util.ArrayList;
import java.util.Random;

public class CSSolution implements Solution {
    private ArrayList<Double> coefficients;
    private int numVars;
    
    public CSSolution(int numVars) {
    	coefficients = new ArrayList<Double>();
    	this.numVars = numVars;
    }
    
    /**
     * Initialize CSSolution with specific ArrayList of
     * coefficients.
     */
    public CSSolution(ArrayList<Double> coefs) {
        this.coefficients = coefs;
        this.numVars = coefs.size();
    }
    
    /**
     * Sets solution with random coefficients.
     */
    public void setAsRandSol() {
        Random rand = new Random();
        for (int i = 0; i < numVars; i++) {
            // TODO: generate legitimate random number
            coefficients.set(i, rand.nextDouble() * 100);
        }
    }
    
    public ArrayList<Double> getCoefs() {
        return coefficients;
    }
    
    public void setCoefs(ArrayList<Double> coefs) {
        this.coefficients = coefs;
    }
}
