package problems;

import java.util.ArrayList;

import solutions.Solution;

/**
 * The objective of the fence problem is to maximize the area
 * enclosed by a rectangular fence given that the fence only
 * requires three walls of material (the fourth wall is a river).
 * Naming convention: side1 and side2 are opposite sides of equal length,
 * and side3 always uses any remaining fence that side1 and side2
 * did not use.
 */
public class FenceProblem extends OptimizationProblem {
    private double fenceLength;
    
    /**
     * Constructs a FenceProblem.
     */
    public FenceProblem(double fenceLength) {
        this.fenceLength = fenceLength;
    }
    
    /**
     * Returns the length of side3, given side1. 
     */
    private double side3(double side1) {
        return fenceLength - 2*side1;
    }
    
    private double area(Solution sol) {
        ArrayList<Double> vars = sol.getVars();
        double side1 = vars.get(0);
        return side1 * side3(side1);
    }
    
    /**
     * Returns the area of the fence, as the objective is
     * to maximize that.
     */
    public double fitness(Solution sol) {
        return area(sol);
    }
    
    /**
     * A solution is within constraints if side lengths
     * are greater than 0. Note: Ensuring that side3 is
     * greater than 0 simultaneously ensures that side1
     * is less than half the fence length.
     */
    public boolean withinConstraints(Solution sol) {
        ArrayList<Double> vars = sol.getVars();
        double side1 = vars.get(0);
        return (side1 > 0 && side3(side1) > 0);
    }
    
    public int getNumVar() {
        return 1;
    }

}
