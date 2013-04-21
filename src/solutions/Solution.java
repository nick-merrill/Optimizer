package solutions;

import java.util.ArrayList;
import java.util.Random;

import DistLib.weibull;
//import ch.visnet.jgsl.Jgsl;
//import ch.visnet.jgsl.sf.*;

import problems.OptimizationProblem;

public abstract class Solution {
	
    protected ArrayList<Double> coefs;
    protected int numVars;
    
    private Random rand = new Random();
    
    public abstract ArrayList<Double> getCoefs();
    public abstract void setCoefs(ArrayList<Double> coefs);
    
    public void randomWalk (OptimizationProblem prob, String distribution) {
        
        
        
    	int n = prob.getNumVar();
    	// creates a neighborhood of size 1 times the scaling factor
    	double distanceSquared = Math.pow(rand.nextDouble() * prob.getScalingFactor(),2);
    	// creates an ArrayList from 0 to n-1 (for indexing purposes only)
    	ArrayList<Integer> variableIndicies = new ArrayList<Integer>();
    	for (int i = 0; i < n; i++) {
    		variableIndicies.set(i, i);
    	}
    	
    	ArrayList<Double> coefs = this.getCoefs();
    	System.out.println(coefs);
    	for (int i = 0; i < n; i++) {
    		// chooses a random index of the list of indices
    		int index = rand.nextInt(variableIndicies.size());
    		// finds the coefficient of the variable that this index corresponds to
    		int coefIndex = variableIndicies.get(index);
    		double curCoef = coefs.get(coefIndex);
    		
    		// use correct distribution to generate random double [0,1)
    		double r;
            if (distribution == "weibull")
                r = weibull.random(1.5, 1, null);
            else
                r = rand.nextDouble();
    		// alters this variable coefficient by adding a random step between (-distance,distance)
    		double distance = Math.sqrt(distanceSquared);
    		double varStep = r*distance*2-distance;
    		double newCoef = curCoef + varStep;
    		coefs.set(coefIndex, newCoef);
    		// removes the variable that has already been visited
    		variableIndicies.remove(index);
    		// updates distance for next for loop
    		distanceSquared -= Math.pow(varStep, 2);
    	}
    	System.out.println(coefs);
    }
    
    public void levyFlight (OptimizationProblem prob) {
    	// same as above, only the random numb generator used for varStep should have a Levy distribution
    }
}
