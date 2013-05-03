package problems;
/* Guided by the wise words of http://www.math.cmu.edu/~af1p/Teaching/OR2/Projects/P23/ORProject_Final_Copy.pdf */
import java.util.ArrayList;

import com.google.gson.Gson;

import exceptions.InputException;
import exceptions.PositiveNumberInputException;

import solutions.Solution;

public class NurseSchedProb extends OptimizationProblem {
	private final int numEmployees, numDays, numShifts,
	                  maxShiftsInRow, maxShiftsADay, minShifts;
    private final double lambdaPref, lambdaMin;
	private final ArrayList<ArrayList<Integer>> shiftReqs, preferences;

	/**
	 * Constructs a Nurse Scheduling Problem.  
	 * In this problem, maxShiftsInRow and maxShiftsADay are treated as hard constraints.
	 * minShifts, however, has the option of being ignored via a lambdaMin value of zero.
	 */
	public NurseSchedProb(int numEmployees, int numDays, int numShifts, int maxShiftsInRow, 
			int maxShiftsADay, int minShifts, double lambdaPref, double lambdaMin,
			ArrayList<ArrayList<Integer>> shiftReqs, ArrayList<ArrayList<Integer>> preferences) 
					throws InputException, PositiveNumberInputException {
		this.numEmployees = numEmployees;
		this.numDays = numDays;
		this.numShifts = numShifts;
		this.maxShiftsInRow = maxShiftsInRow;
		this.maxShiftsADay = maxShiftsADay;
		this.minShifts = minShifts;
		this.lambdaPref = lambdaPref;
		this.lambdaMin = lambdaMin;
		this.shiftReqs = shiftReqs;
		this.preferences = preferences;
		for (int i = 0; i < numEmployees * numDays * numShifts; i++)
			this.constraints.add(new Constraint(i,0,1));
		
		// checking user input
		if (numEmployees <= 0) throw new PositiveNumberInputException("number of employees");
		if (numDays <= 0) throw new PositiveNumberInputException("number of days in scheduling cycle");
		if (numShifts <= 0) throw new PositiveNumberInputException("number of shifts per day");
		if (maxShiftsInRow <= 0) throw new PositiveNumberInputException("maximum number of shifts in a row");
		if (maxShiftsADay <= 0) throw new PositiveNumberInputException("maximum number of shifts per 24 hours");
		if (minShifts < 0) throw new PositiveNumberInputException("minimum number of shifts for an employee per scheduling period");
		if (minShifts > numDays * numShifts)
			throw new InputException("the minimum number of shifts for an employee per scheduling period",
					"must not exceed the total number of shifts in the scheduling period");
		if (lambdaPref < 0) throw new PositiveNumberInputException("the proportional weight of satisfying employee preferences");
		if (lambdaPref > 1) throw new InputException("the proportional weight of satisfying employee preferences",
				"must be less than or equal to one","enter a value between zero and one, inclusive");
		if (lambdaMin < 0) throw new PositiveNumberInputException("the proportional weight of satisfying minimum shifts per employee per scheduling period");
		if (lambdaMin > 1) throw new InputException("the proportional weight of satisfying minimum shifts per employee per scheduling period",
				"must be less than or equal to one","enter a value between zero and one, inclusive");
		if (shiftReqs.size() != numDays || shiftReqs.get(0).size() != numShifts) 
			throw new InputException("shift requirements","does not have the right dimension",
					"the matrix should have dimensions [number of days] by [number of shifts]");
		if (preferences.size() != numEmployees || preferences.get(0).size() != numDays * numShifts)
			throw new InputException("employee preferences","does not have the right dimension",
					"the matrix should have dimensions [number of employees] by [number of days times number of shifts]");
	}
	
	/** 
	 * Soft Requirements: The fitness of the solution is based on how well you 
	 * can satisfy workers' preferences while still meeting the requirement of
	 * workers on duty per shift.  There is also an obeyMinShifts function in
	 * case you would like all employees to have a minimum number of shifts per 
	 * scheduling period.
	 * 
	 * Hard Requirements: If an employee has more than maxShiftsInRow shifts in 
	 * a row or more than maxShiftsADay in a 24 hours period, then fitness 
	 * decreases by a significant amount.
	 */
	private double preferencesMet(Solution sol) {
		ArrayList<Integer> intSol = integerVarsOfSolution(sol);
		ArrayList<Integer> listPref = preferencesList(preferences);
		double totalHappiness = 0;
		for (int i = 0; i<numEmployees; i++){
			ArrayList<Integer> employeePref = row(listPref,i);
			ArrayList<Integer> employeeSched = row(intSol,i);
			for (int j = 0; j < numDays*numShifts; j++) 
				totalHappiness += employeePref.get(j) * employeeSched.get(j);
		}
		return totalHappiness;
	}
	
	private double obeyMaxShiftsInRow(Solution sol) {
		ArrayList<Integer> intSol = integerVarsOfSolution(sol);
		int length = numDays * numShifts;
		double penalty = Math.pow(numEmployees * length,2);
		double violations = 0;
		for (int i = 0; i < numEmployees; i++) { // for each employee
			ArrayList<Integer> schedule = row(intSol,i);
			for (int j = 0; j < length; j++) { // for each shift
				int counter = 0;
				// check to make sure there are NO MORE THAN n shifts in a row, 
				// including wrapping around to the beginning of the schedule
				for (int k = 0; k < maxShiftsInRow + 1; k++){
					if (schedule.get((j+k) % length) == 0) break;
					else counter++;
				}
				if (counter > maxShiftsInRow) violations += penalty;
			}
		}
		return violations;
	}
	
	private double obeyMaxShiftsADay(Solution sol){
		ArrayList<Integer> intSol = integerVarsOfSolution(sol);
		int length = numDays * numShifts;
		double penalty = Math.pow(numEmployees * length,2);
		double violations = 0;
		for (int i = 0; i < numEmployees; i++) { // for each employee
			ArrayList<Integer> schedule = row(intSol,i);
			for (int j = 0; j < length; j++) { // for each shift
				int counter = 0;
				// check to make sure there are NO MORE THAN n shifts in a 24 hours span,
				// including wrapping around to the beginning of the schedule
				for (int k = 0; k < numShifts; k++){
					if (schedule.get((j+k) % length) == 1)
						counter++;
				}
				if (counter > maxShiftsADay) violations += penalty;
			}
		}
		return violations;
	}
	
	private double obeyMinShifts(Solution sol){
		ArrayList<Integer> intSol = integerVarsOfSolution(sol);
		int length = numDays * numShifts;
		double penalty = Math.pow(numEmployees * length,2);
		double violations = 0;
		for (int i = 0; i < numEmployees; i++) { // for each employee
			if (sumArrayList(row(intSol,i)) < minShifts) 
				violations += penalty;				
		}
		return violations;
	}
	
	private double extraCost(Solution sol){
		ArrayList<Integer> intSol = integerVarsOfSolution(sol);
		ArrayList<Integer> shiftReqsList = shiftReqsList(shiftReqs);
		int length = numDays * numShifts;
		double cost = 0; 
		for (int i = 0; i < length; i++){ // for each shift
			double difference = sumArrayList(col(intSol,i)) - shiftReqsList.get(i);
			cost += Math.pow(difference, 2);
		}
		return cost;
	}
	
	@Override
	public double fitness(Solution sol) {
		return -(lambdaPref * preferencesMet(sol) + (1 - .5*lambdaPref - .5*lambdaMin) * extraCost(sol) 
				+ obeyMaxShiftsInRow(sol) + obeyMaxShiftsADay(sol) + lambdaMin * obeyMinShifts(sol));
	}

	/**
	 * A solution is within constraints if its number of workers
	 * per shift match or exceed the requirement.
	 */
	@Override
	public boolean withinCustomConstraints(Solution sol) {
		ArrayList<Integer> intSol = integerVarsOfSolution(sol);
		int length = numDays * numShifts;
		ArrayList<Integer> shiftReqsList = shiftReqsList(shiftReqs);		
		for (int j = 0; j < length; j++) {
			if (sumArrayList(col(intSol,j)) < shiftReqsList.get(j))
				return false;
		}
		return true;
	}
	
	@Override
	public int getNumVar() {
		return numEmployees * numDays * numShifts;
	}
	
	@Override
	public String solToString(Solution s) {
	    ArrayList<Integer> vars = this.integerVarsOfSolution(s);
	    
	    String output = "";
		for (int i = 0; i < vars.size(); i++) {
		    if (i % (numDays * numShifts) == 0) output += "\n";
		    output += String.format("%d ", vars.get(i));
		}
		return output;
	}
	
    // converts solution array list back to matrix
	public ArrayList<ArrayList<Integer>> solToMatrix(Solution s) {
        ArrayList<Integer> vars = integerVarsOfSolution(s);
        ArrayList<ArrayList<Integer>> matrix = new ArrayList<ArrayList<Integer>>(numEmployees);
        int length = numDays * numShifts;
        for (int i = 0; i < numEmployees; i++){ // for each row
            ArrayList<Integer> temp = new ArrayList<Integer>(length);
            for (int j = 0; j < length; j++){ // for each shift
                temp.add(vars.get(i*length + j));
            }
            matrix.add(temp);
        }
        return matrix;
	}
	
	@Override
    public String solToJson(Solution s) {
	    ArrayList<ArrayList<Integer>> matrix = solToMatrix(s);
    	
    	Gson gson = new Gson();
    	String json = gson.toJson(matrix);
    	return json;
    }
	
	@Override
	public String solToTable(Solution s) {
	    ArrayList<ArrayList<Integer>> matrix = solToMatrix(s);
	    
	    String html = "<table>";
	    for (int i = 0; i < matrix.size(); i++) {
	        html += "<tr>";
	        ArrayList<Integer> row = matrix.get(i);
	        for (int j = 0; j < row.size(); j++) {
	            html += String.format("<td>%d</td>", row.get(j));
	        }
	        html += "</tr>";
	    }
	    html += "</table>";
	    
	    return html;
	}
	
	public void printSol(Solution s) {
	    ArrayList<Integer> vars = this.integerVarsOfSolution(s);
	    
		for (int i = 0; i < vars.size(); i++) {
		    if (i % (numDays * numShifts) == 0) System.out.printf("\n");
		    System.out.printf("%d ", vars.get(i));
		}
		System.out.printf("\n");
	}

	

	/* ********************** Helper Functions **************************/
	
	/*
	 * Converts the solution array list of doubles to an array list of integers.
	 * Needed for use throughout the rest of this file.
	 */
	private ArrayList<Integer> integerVarsOfSolution(Solution sol) {
		ArrayList<Double> vars = sol.getVars();
		return doubleListToIntegerList(vars);
	}
	private ArrayList<Integer> doubleListToIntegerList(ArrayList<Double> doubleList) {
	    int length = doubleList.size();
		ArrayList<Integer> integerList = new ArrayList<Integer>(length);
		int x;
		for (int i = 0; i < length; i++){
			if (doubleList.get(i)<.5) {x = 0;} else {x = 1;}
			integerList.add(x); 
		}
		return integerList;
	}
	
	/*
	 * Converts the inputed preferences matrix into a single array list,
	 * mimicking the appearance of the solutions array list.
	 */
	private ArrayList<Integer> preferencesList(ArrayList<ArrayList<Integer>> preferences) {
		int length = numDays * numShifts;
		ArrayList<Integer> prefList = new ArrayList<Integer>(numEmployees * length);
		for (int i = 0; i < numEmployees; i++){
			prefList.addAll(preferences.get(i));
		}
		return prefList;
	}
	
	/*
	 * Converts the inputed shift requirements matrix into a single array list.
	 */
	private ArrayList<Integer> shiftReqsList(ArrayList<ArrayList<Integer>> shiftReqs){
		int length = numDays * numShifts;
		ArrayList<Integer> shiftReqsList = new ArrayList<Integer>(length);
		for (int i = 0; i < numDays; i++) 
			shiftReqsList.addAll(shiftReqs.get(i));
		return shiftReqsList;
	}
	
	/*
	 * Finds the i^th row of solution matrix or a preference matrix that is
	 * in the form of a single array list.
	 */
	private ArrayList<Integer> row(ArrayList<Integer> matrix, int index){
		int length = numDays * numShifts;
		ArrayList<Integer> row = new ArrayList<Integer>(length);
		for (int i = index*length; i < (index+1)*length; i++) 
			row.add(matrix.get(i));
		return row;
	}

	/*
	 * Finds the i^th column of solution matrix or a preference matrix that is
	 * in the form of a single array list.
	 */
	private ArrayList<Integer> col(ArrayList<Integer> matrix, int index) {
		ArrayList<Integer> col = new ArrayList<Integer>(numEmployees);
		int skiplength = numDays * numShifts;
		for (int i = index; i < numEmployees * skiplength; i += skiplength)
			col.add(matrix.get(i));
		return col;
	}
	
	private int sumArrayList(ArrayList<Integer> list) {
		int length = list.size(); 
		int sum = 0;
		for (int i = 0; i < length; i++)
			sum += list.get(i);
		return sum;
	}
	
	public boolean solsAreEqual(Solution s1, Solution s2) {
		ArrayList<Integer> s1Arr = this.integerVarsOfSolution(s1);
		ArrayList<Integer> s2Arr = this.integerVarsOfSolution(s2);
		return s1Arr.equals(s2Arr);
	}
	
}
