package solutions;

import java.util.ArrayList;

import problems.OptimizationProblem;
import DistLib.weibull;

public class CSPSOSolution extends PSOSolution {

	public CSPSOSolution(ArrayList<Double> vars) {
		super(vars);
	}
	
	public CSPSOSolution(int numVars) {
		super(numVars);
	}
	
	//copied from CSSolution
	public CSPSOSolution randomWalk (OptimizationProblem prob, String distribution) {
        
    	int n = prob.getNumVar();
    	// creates a neighborhood of size 1 times the scaling factor
    	double distanceSquared = Math.pow(rand.nextDouble() * prob.getScalingFactor(),2);
    	// creates an ArrayList from 0 to n-1 (for indexing purposes only)
    	ArrayList<Integer> varIndices = new ArrayList<Integer>(n);
    	for (int i = 0; i < n; i++) {
    		varIndices.add(i, i);
    	}
    	
    	ArrayList<Double> vars = this.getVars();
    	CSPSOSolution newSol = new CSPSOSolution(this.numVars);
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
                r = weibull.random(1.5, 1, null);
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
