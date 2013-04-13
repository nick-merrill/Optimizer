package solutions;

import java.util.ArrayList;

public class PSOSolution implements Solution {
    private ArrayList<Double> coefficients;
    double velocity;
    
    public void setVelocity(double v) {
        this.velocity = v;
    }
    
    public double getVelocity() {
        return this.velocity;
    }

    public void randSol() {
        
    }
    
    public ArrayList<Double> getCoefs() {
        return coefficients;
    }
    
    public void setCoefs(ArrayList<Double> coefs) {
        this.coefficients = coefs;
    }
}
