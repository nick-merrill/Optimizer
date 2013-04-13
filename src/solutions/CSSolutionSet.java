package solutions;

import java.util.ArrayList;
import java.util.Random;

public class CSSolutionSet implements SolutionSet {
    private ArrayList<Solution> solutions;
    private Random rand;
    private int numNests;
    private int numVars;
    
    public CSSolutionSet(int numNests, int numVars) {
        this.numNests = numNests;
        this.numVars  = numVars;
        this.rand = new Random();
        this.solutions = new ArrayList<Solution>();
        for (int i = 0; i < numNests; i++) {
            this.solutions.set(i, new CSSolution());
        }
    }
    
    public Solution getRandSol() {
        return solutions.get(rand.nextInt(this.numNests));
    }
    
    // evaluates the fitness of each solution in solutions
    private void evalFitness() {
        for (int i = 0; i < this.numNests; i++) {
            // solutions.get(0).evalFitness();
        }
    }
    
    // sorts all solutions with most fit solution being solutions.get(0)
    private void sortSolsByFitness() {
        
    }
    
    // 
    public void abandonWorstSolutions(double abandonmentRatio) {
        sortSolsByFitness();
        int numToAbandon = (int) (abandonmentRatio * this.numNests);
        int numToKeep = this.numNests - numToAbandon;
        for (int i = numToKeep; i < numNests; i++) {
            solutions.get(i).randSol();
        }
    }


}
