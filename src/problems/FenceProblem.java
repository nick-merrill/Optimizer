package problems;

import java.util.ArrayList;

import solutions.Solution;


/* Representation invariant: side1 and side2 are of equal length.
 * Side3 always uses the maximum amount of fence available after
 * side1 and side2 have used fence.
 */
public class FenceProblem extends OptimizationProblem {
    private double fenceLength;
    
    public FenceProblem(double fenceLength) {
        this.fenceLength = fenceLength;
    }
    
    // Return the length of side3, given side1.
    private double side3(double side1) {
        return fenceLength - 2*side1;
    }
    
    private double area(Solution sol) {
        ArrayList<Double> coefs = sol.getCoefs();
        double side1 = coefs.get(0);
        return side1 * side3(side1);
    }
    
    // We maximize for area.
    public double fitness(Solution sol) {
        return area(sol);
    }
    
    /* A solution is within constraints if side lengths
     * are greater than 0.
     */
    public boolean withinConstraints(Solution sol) {
        ArrayList<Double> coefs = sol.getCoefs();
        double side1 = coefs.get(0);
        return (side1 > 0 && side3(side1) > 0);
    }
    
    public int getNumVar() {
        return 1;
    }

}
