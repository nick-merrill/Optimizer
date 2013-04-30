package solutions;

import java.util.ArrayList;

public class PSOSolution extends Solution {
	private bestPosClass bestPos;
//	private double bestFitness;
    private ArrayList<Double> velocity;
    
    public class bestPosClass extends Solution {
    	public bestPosClass(ArrayList<Double> vars) {
    		super(vars);
    	}
    	public bestPosClass(int numVars) {
    		super(numVars);
    	}
    }
    
    public PSOSolution(ArrayList<Double> vars) {
        super(vars);
    	this.bestPos = new bestPosClass(vars);
    	velocity = new ArrayList<Double>(vars.size());
    }

    public PSOSolution(int numVars) {
        super(numVars);
        this.bestPos = new bestPosClass(numVars);
        velocity = new ArrayList<Double>(numVars);
    }
    
    public void setVelocity(ArrayList<Double> v) {
        this.velocity = v;
    }
    
    public ArrayList<Double> getVelocity() {
        return this.velocity;
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
    
    public void printAll() {
    	
        for (int i = 0; i < this.numVars; i++) {
            System.out.printf("v%d:\t%f\n", i, this.velocity.get(i));
        }
        this.print();
    }
}
