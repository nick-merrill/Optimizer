package solutions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import problems.OptimizationProblem;

public abstract class SolutionSet {
	protected ArrayList<? extends Solution> solutions;
	protected int N_SOL;
	protected int numRuns;
	
    /** 
     * Sorts all solutions with most fit solution being solutions.get(0).
     */
    public void sortByFitness(OptimizationProblem optProb) {
        // Evaluates the fitness of each solution in solutions.
        /*
    	for (int i = 0; i < this.N_SOL; i++)
            solutions.get(i).evalFitness(optProb);
        */
    	
        // Sorts solutions by their respective fitness numbers with lowest fitness first.
        Collections.sort(this.solutions, new SolutionByFitnessComparator());
        
        // Reverses sort to put the most fit solution first.
        Collections.reverse(this.solutions);
    }
    
    public ArrayList<? extends Solution> getSolutions() {
    	return solutions;
    }
    
	public Solution getSol(int i) {
    	return solutions.get(i);
    }
	
    public Solution getMostFitSolution(OptimizationProblem optProb) {
        this.sortByFitness(optProb);
        return this.solutions.get(0);
    }
    
    public class SolutionByFitnessComparator implements Comparator<Solution> {
        public int compare(Solution s1, Solution s2) {
            double s1Fitness = s1.getFitness();
            double s2Fitness = s2.getFitness();
            if (s1Fitness < s2Fitness) return -1;
            else if (s1Fitness > s2Fitness) return 1;
            else return 0;
        }
    }
    
    public void setNumRuns(int newNumRuns){
    	this.numRuns = newNumRuns;
    }
    
    public int getNumRuns() {
    	return numRuns;
    }
}
