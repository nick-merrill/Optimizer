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
        System.out.printf("Evaluated fitness: %f\n", this.getFitness());
    }
    
    public double getFitness() {
/*        if (this.fitness == null) {
//            throw new Exception("Uninitialized fitness!");
            System.out.printf("Uninitializted fitness!");
            System.exit(1);
        }*/
        return this.fitness;
    }
    
    private void initializeWithNull() {
        System.out.printf("Initializing solution with null\n");
        for (int i = 0; i < this.numVars; i++) {
            this.coefs.add(null);
        }
    }
    
    /**
     * Sets solution with random coefficients.
     */
    public void setAsRandSol(OptimizationProblem optProb) {
        if (this.coefs.size() < this.numVars) {
            this.initializeWithNull();
        }
        for (int i = 0; i < this.numVars; i++) {
            // TODO: generate legitimate random number, depending on optProb
            this.coefs.set(i, rand.nextDouble() * 100);
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
    	ArrayList<Integer> coefIndices = new ArrayList<Integer>(n);
    	for (int i = 0; i < n; i++) {
    		coefIndices.add(i, i);
    	}
    	
    	this.print();
    	
    	ArrayList<Double> coefs = this.getCoefs();
    	Solution newSol = new Solution(this.numVars);
    	newSol.initializeWithNull();
    	ArrayList<Double> newCoefs = newSol.getCoefs();
    	for (int i = 0; i < n; i++) {
    		/* Chooses a random coefficient index from the indices
    		 * of the remaining/unwalked coefficients. */
    		int index = rand.nextInt(coefIndices.size());
    		System.out.printf("index: %d\n", index);
    		// Finds the coefficient of the variable that this index corresponds to.
    		int coefIndex = coefIndices.get(index);
    		System.out.printf("coefIndex: %d\n", coefIndex);
    		double curCoef = coefs.get(coefIndex);
    		System.out.printf("curCoef: %f\n", curCoef);
    		
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
    		coefIndices.remove(index);
    		// updates distance for next for loop
    		distanceSquared -= Math.pow(varStep, 2);
    	}
    	
    	return newSol;
    }
    
    public void print() {
        if (this.coefs.size() == 0) {
            System.out.printf("Empty Solution\n");
        } else {
            for (int i = 0; i < this.numVars; i++) {
                System.out.printf("x%d:\t%f\n", i, this.coefs.get(i));
            }
        }
        if (this.fitness != null)
            System.out.printf("---------------------\nFitness: %f\n", this.fitness);
    }
}
