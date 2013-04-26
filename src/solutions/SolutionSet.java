package solutions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

import problems.OptimizationProblem;

public interface SolutionSet {
	public void sortByFitness(OptimizationProblem optProb);
}
