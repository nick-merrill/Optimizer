package solutions;

import java.util.ArrayList;

public class PSOSolution implements Solution {
    private ArrayList<Double> coeffs;
    private ArrayList<Double> bestPos;
    private ArrayList<Double> velocity;
    
    public PSOSolution(ArrayList<Double> coeffs) {
    	this.coeffs = coeffs;
    	this.bestPos = coeffs;
    }
    
    public void setVelocity(ArrayList<Double> v) {
        this.velocity = v;
    }
    
    public ArrayList<Double> getVelocity() {
        return this.velocity;
    }

    public void randSol() {
        
    }
    
    public ArrayList<Double> getCoefs() {
        return coeffs;
    }
    
    public void setCoefs(ArrayList<Double> coefs) {
        this.coeffs = coefs;
    }
    
    public void setBestPos() {
    	this.bestPos = this.coeffs;
    }
    
    public void setBestPos(ArrayList<Double> coefs) {
    	this.bestPos = coefs;
    }
}
