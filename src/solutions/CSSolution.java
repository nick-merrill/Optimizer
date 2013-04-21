package solutions;

import java.util.ArrayList;
import java.util.Random;

public class CSSolution extends Solution {
    
    public CSSolution(int numVars) {
    	coefs = new ArrayList<Double>();
    	this.numVars = numVars;
    }
    
    /**
     * Initialize CSSolution with specific ArrayList of
     * coefficients.
     */
    public CSSolution(ArrayList<Double> coefs) {
        this.coefs = coefs;
        this.numVars = coefs.size();
    }
    
    /**
     * Sets solution with random coefficients.
     */
    public void setAsRandSol() {
        Random rand = new Random();
        for (int i = 0; i < numVars; i++) {
            // TODO: generate legitimate random number
            coefs.set(i, rand.nextDouble() * 100);
        }
    }
    
    public ArrayList<Double> getCoefs() {
        return coefs;
    }
    
    public void setCoefs(ArrayList<Double> coefs) {
        this.coefs = coefs;
    }
}