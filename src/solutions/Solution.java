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
    	// creates a neighborhood of size 1 times the scaling factor
    	double distance = rand.nextDouble() * prob.getScalingFactor();
    	// creates an ArrayList from 0 to n-1 (for indexing purposes only)
    	ArrayList<Integer> variableIndicies = new ArrayList<Integer>();
    	for (int i = 0; i < n; i++) {
    		variableIndicies.set(i, i);
    	}

    	ArrayList<Double> coefs = this.getCoefs();
    	for (int i = 0; i < n; i++) {
    		// chooses a random index of the list of indices
    		int index = rand.nextInt(variableIndicies.size());
    		// finds the coefficient of the variable that this index corresponds to
    		int coefIndex = variableIndicies.get(index);
    		double curCoef = coefs.get(coefIndex);
    		// alters this variable coefficient by adding a random step between (-distance,distance)
    		double varStep = rand.nextDouble()*distance*2-distance;
    		double newCoef = curCoef + varStep;
    		coefs.set(coefIndex, newCoef);
    		// removes the variable that has already been visited
    		variableIndicies.remove(index);
    	}
    }
    
    public void levyFlight (OptimizationProblem prob) {
    	// same as above, only the random numb generator used for varStep should have a Levy distribution
    }
}
