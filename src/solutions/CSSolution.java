package solutions;

import java.util.ArrayList;

public class CSSolution implements Solution {
    public ArrayList<Double> coefficients;
    
    // Uses old solution to WALK to a new solution
    // and returns that solution.
    public void newSolution(Solution i) {
        // TODO: Implement this on a per-problem basis,
        //       accessing the public coefficients ArrayList.
    }
    
    // sets solution with random solution
    public void randSol() {
        
    }
    
    public ArrayList<Double> getCoefs() {
        return coefficients;
    }
    
    public void setCoefs(ArrayList<Double> coefs) {
        this.coefficients = coefs;
    }
}
