package solutions;

import java.util.ArrayList;
import java.util.Random;

import DistLib.weibull;
//import ch.visnet.jgsl.Jgsl;
//import ch.visnet.jgsl.sf.*;

import problems.OptimizationProblem;

public class Solution {
    
    protected ArrayList<Double> coefs;
    protected int numVars;
    
    /**
     * Invariant: fitness must be updated to null whenever the solution is
     *            modified, to prevent inaccurate fitness numbers.
     */
    protected Double fitness;
    
    protected Random rand = new Random();
    
    /**
     * Initialize CSSolution with specific ArrayList of
     * coefficients.
     */
    public Solution(ArrayList<Double> coefs) {
        this.coefs = coefs;
        this.numVars = coefs.size();
    }
    
    public Solution(int numVars) {
    	coefs = new ArrayList<Double>();
    	this.numVars = numVars;
    }
    
    public ArrayList<Double> getCoefs() {
        return coefs;
    }
    
    public void setCoefs(ArrayList<Double> coefs) {
        this.coefs = coefs;
    }
    
    public void evalFitness(OptimizationProblem optProb) {
        this.fitness = optProb.fitness(this);
    }
    
    public double getFitness() {
        if (this.fitness == null) {
//            throw new Exception("Uninitialized fitness!");
            System.out.printf("Uninitializted fitness!");
            System.exit(1);
        }
        return this.fitness;
    }
    
    /**
     * Returns a *new* solution that is a random walk away from the current solution.
     * Pass in distribution as "weibull" for an appropriate weibull distribution.
     * TODO: Allow "levy" as a distribution type.
     */
    public Solution randomWalk (OptimizationProblem prob, String distribution) {
    	int n = prob.getNumVar();
    	// creates a neighborhood of size 1 times the scaling factor
    	double distanceSquared = Math.pow(rand.nextDouble() * prob.getScalingFactor(),2);
    	// creates an ArrayList from 0 to n-1 (for indexing purposes only)
    	ArrayList<Integer> variableIndicies = new ArrayList<Integer>(n);
    	for (int i = 0; i < n; i++) {
    		variableIndicies.add(i, i);
    	}
    	
    	ArrayList<Double> coefs = this.getCoefs();
    	ArrayList<Double> newCoefs = new ArrayList<Double>(n);
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
    		//coefs.set(coefIndex, newCoef);
    		newCoefs.set(coefIndex, newCoef);
    		// removes the variable that has already been visited
    		variableIndicies.remove(index);
    		// updates distance for next for loop
    		distanceSquared -= Math.pow(varStep, 2);
    	}
    	System.out.println(coefs);
    	return new Solution(newCoefs);
    }
    
    public void print() {
        for (int i = 0; i < this.numVars; i++)
            System.out.printf("x%d: %f\n", i, this.coefs.get(i));
        if (this.fitness != null)
            System.out.printf("---------------------\nFitness: %f\n", this.fitness);
    }
}
