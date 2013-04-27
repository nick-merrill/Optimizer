package problems;

import java.util.ArrayList;
import solutions.Solution;

public class NurseSchedProb extends OptimizationProblem {
	private int numEmployees;
	private int numDays; 
	private int numShifts;
	private ArrayList<ArrayList<Integer>> shiftReqs;
	private ArrayList<ArrayList<Integer>> preferences;
	

	/**
	 * Constructs a NurseSchedProb, checking for appropriate inputs, else exiting
	 */
	public NurseSchedProb(int numEmployees, int numDays, int numShifts,
			ArrayList<ArrayList<Integer>> shiftReqs, ArrayList<ArrayList<Integer>> preferences) {
		this.numEmployees = numEmployees;
		this.numDays = numDays;
		this.numShifts = numShifts;
		this.shiftReqs = shiftReqs;
		this.preferences = preferences;
		// run checks on inputs
		if (shiftReqs.size() != numDays || shiftReqs.get(0).size() != numShifts) {
			System.out.println("The dimensions of the shift requirements must match " +
					"the number of days and the number of shifts per day \n");
			System.exit(4);
		}
		for (int i = 0; i < numDays; i++){
			if (shiftReqs.get(i).get(numShifts-1) != 0){
				System.out.println("The last shift must have a requirement of zero employees. \n");
				System.exit(4);
			}
		}
		if (preferences.size() != numEmployees || preferences.get(0).size() != numDays * numShifts){
			System.out.println("The dimensions of the employee preferences must match " +
					"the number of employees and the number of days times the number of shifts per day \n");
			System.exit(4);
		}
	}
	
	/** 
	 * The fitness of the solution is based on how well you can satisfy 
	 * workers' preferences while still meeting the requirement of
	 * workers on duty per shift.
	 */
	private double preferencesMet(Solution sol) {
		double totalHappiness = 0;
		for (int i = 0; i<numEmployees; i++){
			ArrayList<Integer> employeePref = row(preferences,i);
			ArrayList<Integer> employeeSched = row(sol,i);
			double happiness = 0;
			for (int j = 0; j < numDays*numShifts; j++) {
				happiness += employeePref.get(j) * employeeSched.get(j);
			}
			totalHappiness += happiness;
		}
		return totalHappiness;
	}
	
	public double fitness(Solution sol) {
		return preferencesMet(sol);
	}

	/**
	 * A solution is within constraints if its number of workers
	 * per shift match or exceed the requirement.
	 * The solution must also not have one worker on duty for 
	 * more than two shifts in a row.
	 */
	public boolean withinCustomConstraints(Solution sol) {
		// changes the shiftReqs matrix into a single arraylist
		ArrayList<Integer> shiftReqsList;
		for (int i = 0; i < numDays; i++) {
			shiftReqsList.addAll(shiftReqs.get(i));
		}
		
		int length = numDays * numShifts;
		for (int j = 0; j < length; j++) {
			if (sumArrayList(col(sol,j)) < shiftReqsList.get(j))
				return false;
		}
		return true;
		
		//TODO back-to-back shifts
	}
	
	public int getNumVar() {
		return 1;
	}
	
	/**
	 *  Helper Functions
	 */
	private ArrayList<Integer> row(ArrayList<Integer> matrix, int index){
		int length = numDays * numShifts;
		ArrayList<Integer> row = new ArrayList<Integer>(length);
		for (int i = index*length; i < (index+1)*length; i++) {
			row.add(matrix.get(i));
		}
		return row;
	}
	private ArrayList<Integer> col(ArrayList<Integer> matrix, int index) {
		ArrayList<Integer> col = new ArrayList<Integer>(numEmployees);
		int skiplength = numDays * numShifts;
		for (int i = index; i < numEmployees*skiplength+index; i += skiplength) {
			col.add(matrix.get(i));
		}
		return col;
	}
	private int sumArrayList(ArrayList<Integer> list) {
		int length = list.size(); 
		int sum = 0;
		for (int i = 0; i < length; i++){
			sum += list.get(i);
		}
		return sum;
	}

}
