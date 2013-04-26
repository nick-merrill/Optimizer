package problems;

import java.util.ArrayList;
import solutions.Solution;

public class NurseSchedProb extends OptimizationProblem {
	private int earlyShift;
	private int swingShift;
	private int nightShift;
	private ArrayList<ArrayList<Integer>> preferences;
	

	/**
	 * Constructs a NurseSchedProb 
	 */
	public NurseSchedProb(int earlyShift, int swingShift, int nightShift, 
			ArrayList<ArrayList<Integer>> preferences) {
		this.earlyShift = earlyShift;
		this.swingShift = swingShift;
		this.nightShift = nightShift;
		this.preferences = preferences;
	}
	
	int numEmployees = preferences.size(); 
	
	// idk, how many vars are there?? :/
	public int getNumVar() {
		return 1;
	}

	/** 
	 * The fitness of the solution is based on how well you can satisfy 
	 * workers' preferences while still meeting the requirement of
	 * workers on duty per shift.
	 */
	private double preferencesMet(Solution sol, ArrayList<ArrayList<Integer>> preferences) {
		double happiness = 0;
		for (int k = 0; k<numEmployees; k++){
			ArrayList<Integer> schedule = sol.get(k);
			ArrayList<Integer> workerPref = preferences.get(k);
			for (int j = 0; j<numEmployees; j=+3) {
				happiness += schedule.get(j) * workerPref.get(0); }
			for (int j = 1; j<numEmployees; j=+3) {
				happiness += schedule.get(j) * workerPref.get(1); }
			for (int j = 2; j<numEmployees; j=+3) {
				happiness += schedule.get(j) * workerPref.get(2); }
		}
		return happiness;
	}
	
	public double fitness(Solution sol) {
		return preferencesMet(sol, preferences);
	}

	/**
	 * A solution is within constraints if its number of workers
	 * per shift match or exceed the requirement.
	 */
	public boolean withinConstraints(Solution sol) {
		for (int j = 0; j<numEmployees; j=+3) {
			sumColumn(j,sol) >= earlyShift; }
		for (int j = 1; j<numEmployees; j=+3) {
			sumColumn(j,sol) >= swingShift; }
		for (int j = 2; j<numEmployees; j=+3) {
			sumColumn(j,sol) >= earlyShift; }
	}
	/**
	 *  Helper Functions
	 */
	private int sumColumn(int column, ArrayList<ArrayList<Integer>> matrix) {
		int length = matrix.size();
		int sum = 0;
		for (int i = 0; i<length; i++)
			sum += matrix.get(i).get(column);
		return sum;
	} 
	/*	private int sumRow(ArrayList<Integer> list) {
	int length = list.size();
	int sum = 0;
	for (int i = 0; i<length; i++)
		sum += list.get(i);
	return sum;
	} */
}
