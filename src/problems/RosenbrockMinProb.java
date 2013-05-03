package problems;

import java.util.ArrayList;
import solutions.Solution;

public class RosenbrockMinProb extends OptimizationProblem {

	private final int N;
	
	//Default constructor for setting constraints
	public RosenbrockMinProb() {
		this(2);
	}
	
	//Default constructor for setting constraints
	public RosenbrockMinProb(int n) {
		N=n;
		for(int i=0; i<N; i++) {
			this.constraints.add(new Constraint(i, -Math.pow(10,4), Math.pow(10,4)));
		}
	}
	
	public double rosenbrockFunc(Solution s) {
		double sum = 0;
		ArrayList<Double> vars = s.getVars();
		
		for(int i=0; i<N-1; i++) {
			double xi = vars.get(i).doubleValue();
			double xi1 = vars.get(i+1).doubleValue();
			sum += 100*Math.pow(xi1 - xi*xi, 2)+Math.pow(xi-1, 2);
		}
		
		return sum;
	}
	
	@Override
	public int getNumVar() {
		return N;
	}

	@Override
	public double fitness(Solution s) {
		return -rosenbrockFunc(s);
	}

	@Override
	public boolean withinCustomConstraints(Solution s) {
		return true;
	}

	@Override
	public String solToString(Solution s) {
		ArrayList<Double> vars = s.getVars();
		String solString = "";
		for(int i=0; i<N; i++) {
			solString += "x" + i +" = " + vars.get(i) + "\n";
		}
		solString += "f(x) = " + s.getFitness();
		
	    return solString;
	}

}
