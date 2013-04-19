package solutions;

import java.util.ArrayList;
import java.util.Random;

public class CSSolutionSet implements SolutionSet {
    private ArrayList<CSSolution> solutions;
    private Random rand;
    private int numNests;
    private int numVars;
    
    public CSSolutionSet(int numNests, int numVars) {
        this.numNests = numNests;
        this.numVars  = numVars;
        this.rand = new Random();
        this.solutions = new ArrayList<CSSolution>(numNests);
        for (int i = 0; i < numNests; i++) {
            this.solutions.set(i, new CSSolution(numVars));
        }
    }
    
    public CSSolution getRandSol() {
        return solutions.get(rand.nextInt(this.numNests));
    }
    
    // TODO
    // evaluates the fitness of each solution in solutions
    private void evalFitness() {
        for (int i = 0; i < this.numNests; i++) {
            // solutions.get(0).evalFitness();
        }
    }
    
    // sorts all solutions with most fit solution being solutions.get(0)
    private void sortSolsByFitness() {
        
    }
    
    public void replace(int j, CSSolution sol) {
        this.solutions.set(j, sol);
    }
    
    public CSSolution getSol(int i) {
    	return this.solutions.get(i);
    }
    
    // TODO
    public void abandonWorstSols(double abandonmentRatio) {
        sortSolsByFitness();
        int numToAbandon = (int) (abandonmentRatio * this.numNests);
        int numToKeep = this.numNests - numToAbandon;
        for (int i = numToKeep; i < numNests; i++) {
            solutions.get(i).setAsRandSol();
        }
    }
    
    public int getNumSols() {
        return numNests;
    }


}
