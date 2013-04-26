package solutions;

import java.util.ArrayList;

public class PSOSolution extends Solution {
	private bestPosClass bestPos;
    private ArrayList<Double> velocity;
    
    private class bestPosClass extends Solution {
    	public bestPosClass(ArrayList<Double> vars) {
    		super(vars);
    	}
    }
    
    public PSOSolution(ArrayList<Double> vars) {
        super(vars);
    	this.bestPos = new bestPosClass(vars);
    }
    
    public void setVelocity(ArrayList<Double> v) {
        this.velocity = v;
    }
    
    public ArrayList<Double> getVelocity() {
        return this.velocity;
    }

    public void randSol() {
        
    }
    
    public ArrayList<Double> getCurrPos() {
    	return super.getVars();
    }
    
    public void setCurrPos(ArrayList<Double> vars) {
    	super.setVars(vars);
    }
    
    public void setBestPos() {
    	this.bestPos.setVars(this.vars);
    }
    
    public void setBestPos(ArrayList<Double> vars) {
    	this.bestPos.setVars(vars);
    }
    
    public ArrayList<Double> getBestPos() {
    	return bestPos.getVars();
    }
    
    public bestPosClass getBestPosSol() {
    	return bestPos;
    }
}
