package solutions;

import java.util.ArrayList;
import java.util.Random;

import problems.OptimizationProblem;

public abstract class Solution {
	
    protected ArrayList<Double> coefficients;
    protected int numVars;
    
    private Random rand = new Random();
    
    public abstract ArrayList<Double> getCoefs();
    public abstract void setCoefs(ArrayList<Double> coefs);
    
    public void randomWalk (OptimizationProblem prob) {
    	int n = prob.getNumVar();
    	double distance = rand.nextDouble() * prob.getScalingFactor();

    	ArrayList<Integer> variableIndicies = new ArrayList<Integer>();
    	for (int i = 0; i < n; i++) {
    		variableIndicies.set(i, i);
    	}

    	ArrayList<Double> coefs = this.getCoefs();
    	for (int i = 0; i < n; i++) {
    		int index = rand.nextInt(variableIndicies.length());
    		int coefIndex = variableIndicies.get(index);
    		
    		double curCoef = coefs.get(coefIndex);
    		
    		double varStep = rand.nextDouble()*distance*2-distance;
    		
    		double newCoef = curCoef + varStep;
    		
    		coefs.set(coefIndex, newCoef);
    		
    		variableIndicies.remove(index);
    	}

    }
}
