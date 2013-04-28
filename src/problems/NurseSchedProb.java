package problems;
/* Guided by the wise words of http://www.math.cmu.edu/~af1p/Teaching/OR2/Projects/P23/ORProject_Final_Copy.pdf */
import java.util.ArrayList;

import problems.OptimizationProblem.Constraint;
import solutions.Solution;

public class NurseSchedProb extends OptimizationProblem {
	private int numEmployees;
	private int numDays; 
	private int numShifts;
	private ArrayList<ArrayList<Integer>> shiftReqs;
	private ArrayList<ArrayList<Integer>> preferences;

	/**
	 * Constructs a NurseSchedProb
	 */
	public NurseSchedProb(int numEmployees, int numDays, int numShifts,
			ArrayList<ArrayList<Integer>> shiftReqs, ArrayList<ArrayList<Integer>> preferences) {
		this.numEmployees = numEmployees;
		this.numDays = numDays;
		this.numShifts = numShifts;
		this.shiftReqs = shiftReqs;
		this.preferences = preferences;
		for (int i = 0; i < numEmployees * numDays * numShifts; i++)
			this.constraints.add(new Constraint(i,0,1));
		// TODO NICK
/*		if (shiftReqs.size() != numDays || shiftReqs.get(0).size() != numShifts) {
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
*/
	}
	
	/** 
	 * The fitness of the solution is based on how well you can satisfy 
	 * workers' preferences while still meeting the requirement of
	 * workers on duty per shift.
	 */
	private double preferencesMet(Solution sol) {
		ArrayList<Integer> intSol = useableSolution(sol);
		ArrayList<Integer> listPref = preferencesList(preferences);
		double totalHappiness = 0;
		for (int i = 0; i<numEmployees; i++){
			ArrayList<Integer> employeePref = row(listPref,i);
			ArrayList<Integer> employeeSched = row(intSol,i);
			double happiness = 0;
			for (int j = 0; j < numDays*numShifts; j++) {
				happiness += employeePref.get(j) * employeeSched.get(j);
			}
			totalHappiness += happiness;
		}
		return totalHappiness;
	}
	private double extraCost(Solution sol){
		ArrayList<Integer> intSol = useableSolution(sol);
		ArrayList<Integer> shiftReqsList = shiftReqsList(shiftReqs);
		double cost = 0; 
		for (int i = 0; i < shiftReqsList.size(); i++){
			ArrayList<Integer> col = col(intSol,i);
			double difference = sumArrayList(col) - shiftReqsList.get(i);
			cost += Math.pow(difference, 2);
		}
		return cost;
	}
	
	public double fitness(Solution sol) {
		return preferencesMet(sol);
		// return lambda * preferencesMet(sol) + (1 - lambda) * extraCost(sol);
	}

	/**
	 * A solution is within constraints if its number of workers
	 * per shift match or exceed the requirement.
	 * The solution must also not have one worker on duty for 
	 * more than two shifts in a row.
	 */
	public boolean withinCustomConstraints(Solution sol) {
		ArrayList<Integer> intSol = useableSolution(sol);
		ArrayList<Integer> shiftReqsList = shiftReqsList(shiftReqs);		
		int length = numDays * numShifts;
		for (int j = 0; j < length; j++) {
			if (sumArrayList(col(intSol,j)) < shiftReqsList.get(j))
				return false;
		}
		return true;
		// max shifts per 24 hrs
		// max back-to-back shifts
		//TODO back-to-back shifts
	}
	
	public int getNumVar() {
		return numEmployees * numDays * numShifts;
	}
	
	
	

	/* ********************** Helper Functions **************************/
	
	/*
	 * Converts the solution arraylist of doubles to an arraylist of integers.
	 * Needed for use throughout the rest of this file.
	 */
	private ArrayList<Integer> useableSolution(Solution sol) {
		int length = numDays * numShifts;
		ArrayList<Integer> newSol = new ArrayList<Integer>(length);
		ArrayList<Double> vars = sol.getVars();
		for (int i = 0; i < length; i++){
			int x;
			if (vars.get(i)<.5) {x = 0;} else {x = 1;};
			newSol.add(i,x); 
		}
		return newSol;
	}
	
	/*
	 * Converts the inputed preferences matrix into a single arraylist,
	 * mimicking the appearance of the solutions arraylist.
	 */
	private ArrayList<Integer> preferencesList(ArrayList<ArrayList<Integer>> preferences) {
		int length = numDays * numShifts;
		ArrayList<Integer> prefList = new ArrayList<Integer>(length);
		for (int i = 0; i < numEmployees; i++){
			prefList.addAll(preferences.get(i));
		}
		return prefList;
	}
	/*
	 * Converts the shiftReqs matrix into a single arraylist
	 */
	private ArrayList<Integer> shiftReqsList(ArrayList<ArrayList<Integer>> shiftReqs){
		int length = numDays * numShifts;
		ArrayList<Integer> shiftReqsList = new ArrayList<Integer>(length);
		for (int i = 0; i < numDays; i++) 
			shiftReqsList.addAll(shiftReqs.get(i));
		return shiftReqsList;
	}
	
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
