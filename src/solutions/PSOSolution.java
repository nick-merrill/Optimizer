package solutions;

import java.util.ArrayList;

public class PSOSolution extends Solution {
    private ArrayList<Double> bestPos;
    private ArrayList<Double> velocity;
    
    public PSOSolution(ArrayList<Double> vars) {
        super(vars);
    	this.bestPos = vars;
    }
    
    public void setVelocity(ArrayList<Double> v) {
        this.velocity = v;
    }
    
    public ArrayList<Double> getVelocity() {
        return this.velocity;
    }

    public void randSol() {
        
    }
    
    public ArrayList<Double> getVars() {
        return vars;
    }
    
    public void setVars(ArrayList<Double> vars) {
        this.vars = vars;
    }
    
    public void setBestPos() {
    	this.bestPos = this.vars;
    }
    
    public void setBestPos(ArrayList<Double> vars) {
    	this.bestPos = vars;
    }
}
