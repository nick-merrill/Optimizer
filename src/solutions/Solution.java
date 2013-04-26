package solutions;

import java.util.ArrayList;
import java.util.Random;

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
    
    protected void initializeWithNull() {
        for (int i = 0; i < this.numVars; i++) {
            this.vars.add(null);
        }
    }
    
    /**
     * Sets solution with random variable values, ensuring constraints for
     * the optimization problem are met.
     */
    public void setAsRandSol(OptimizationProblem optProb) {
        if (this.vars.size() < this.numVars) {
            this.initializeWithNull();
        }
        do {
            for (int i = 0; i < this.numVars; i++) {
                double min = optProb.getMinVar(i);
                double max = optProb.getMaxVar(i);
                double range = max - min;
                double newVar = rand.nextDouble() * range + min;
                this.vars.set(i, newVar);
            }
        } while(!optProb.withinConstraints(this));
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
