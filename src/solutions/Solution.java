package solutions;

import java.util.ArrayList;
import java.util.Random;

import DistLib.weibull;
//import ch.visnet.jgsl.Jgsl;
//import ch.visnet.jgsl.sf.*;

import problems.OptimizationProblem;

public class Solution {
    
    protected ArrayList<Double> vars;
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
    public Solution(ArrayList<Double> vars) {
        this.vars = vars;
        this.numVars = vars.size();
    }
    
    public Solution(int numVars) {
    	this.vars = new ArrayList<Double>();
    	this.numVars = numVars;
    }
    
    public ArrayList<Double> getVars() {
        return vars;
    }
    
    public void setVars(ArrayList<Double> vars) {
        this.vars = vars;
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
    
    private void initializeWithNull() {
        for (int i = 0; i < this.numVars; i++) {
            this.vars.add(null);
        }
    }
    
    /**
     * Sets solution with random variable values.
     */
    public void setAsRandSol(OptimizationProblem optProb) {
        if (this.vars.size() < this.numVars) {
            this.initializeWithNull();
        }
        for (int i = 0; i < this.numVars; i++) {
            // TODO: generate legitimate random number, depending on optProb
            this.vars.set(i, rand.nextDouble() * 100);
        }
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
    	ArrayList<Integer> varIndices = new ArrayList<Integer>(n);
    	for (int i = 0; i < n; i++) {
    		varIndices.add(i, i);
    	}
    	
    	ArrayList<Double> vars = this.getVars();
    	Solution newSol = new Solution(this.numVars);
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
    
    public void print() {
        if (this.vars.size() == 0) {
            System.out.printf("Empty Solution\n");
        } else {
            for (int i = 0; i < this.numVars; i++) {
                System.out.printf("x%d:\t%f\n", i, this.vars.get(i));
            }
        }
        if (this.fitness != null)
            System.out.printf("---------------------\nFitness: %f\n", this.fitness);
    }
}
