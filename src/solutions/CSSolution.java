package solutions;

import java.util.ArrayList;

import DistLib.uniform;
import DistLib.weibull;

import problems.OptimizationProblem;

public class CSSolution extends Solution {

    public CSSolution(ArrayList<Double> vars) {
        super(vars);
    }
    public CSSolution(int numVars) {
        super(numVars);
    }
    
    /**
     * Returns a *new* solution that is a random walk away from the current solution.
     * Pass in distribution as "weibull" for an appropriate weibull distribution.
     */
    public CSSolution randomWalk (OptimizationProblem prob, String distribution) {
        
    	int n = prob.getNumVar();
    	// creates a neighborhood of size 1 times the scaling factor
    	double distanceSquared = Math.pow(rand.nextDouble() * prob.getScalingFactor(),2);
    	// creates an ArrayList from 0 to n-1 (for indexing purposes only)
    	ArrayList<Integer> varIndices = new ArrayList<Integer>(n);
    	for (int i = 0; i < n; i++) {
    		varIndices.add(i, i);
    	}
    	
    	ArrayList<Double> vars = this.getVars();
    	CSSolution newSol = new CSSolution(this.numVars);
    	newSol.initializeWithNull();
    	ArrayList<Double> newVars = newSol.getVars();
    	for (int i = 0; i < n; i++) {
    		/* Chooses a random variable index from the indices
    		 * of the remaining/unwalked variables. */
    		int index = rand.nextInt(varIndices.size());
    		// Finds the variable value that this index corresponds to.
    		int varIndex = varIndices.get(index);
    		double curVar = vars.get(varIndex);
    		
    		// use correct distribution to generate random double [0,1)
    		double r;
            if (distribution == "weibull")
                r = weibull.random(1.5, 1, new uniform());
            else
                r = rand.nextDouble();
    		// alters this variable coefficient by adding a random step between (-distance,distance)
    		double distance = Math.sqrt(distanceSquared);
    		double varStep = r*distance*2-distance;
    		double newVar = curVar + varStep;
    		newVars.set(varIndex, newVar);
    		// removes the variable that has already been visited
    		varIndices.remove(index);
    		// updates distance for next for loop
    		distanceSquared -= Math.pow(varStep, 2);
    	}
    	
    	return newSol;
    }

}
