package solutions;

import java.util.ArrayList;

public class PSOSolution extends Solution {
    private ArrayList<Double> bestPos;
    private ArrayList<Double> velocity;
    
    public PSOSolution(ArrayList<Double> coefs) {
    	this.coefs = coefs;
    	this.bestPos = coefs;
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
        return coefs;
    }
    
    public void setCoefs(ArrayList<Double> coefs) {
        this.coefs = coefs;
    }
    
    public void setBestPos() {
    	this.bestPos = this.coefs;
    }
    
    public void setBestPos(ArrayList<Double> coefs) {
    	this.bestPos = coefs;
    }
}
