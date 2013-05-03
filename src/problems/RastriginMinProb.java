package problems;

import java.util.ArrayList;
import solutions.Solution;

/*
 * Rastrigin is a non-convex function used as a performance test problem
 * for optimization algorithms.
 * 
 * f(x)=A*n + Sum_1^n [x_i^2 - A*Cos(2*Pi*x_i)]
 * 
 * Global minimum at x=0, f(x)=0
 */
public class RastriginMinProb extends OptimizationProblem {
	
	private final int N;
	private final int A=10;

	//Set number of variables
	public RastriginMinProb(int numVar) {
        N = numVar;
		
        for(int i=0; i<N; i++) {
        	this.constraints.add(new Constraint(i, -5.12, 5.12));
        }
    }
	
	//Default constructor for setting constraints, N=10
	public RastriginMinProb() {
        this(10);
    }
	
	private double rastriginFunction(Solution s) {
		ArrayList<Double> vars = s.getVars();
		double sum = A*N;
		double xi;
		
		for(int i=0; i<N; i++) {
			xi = vars.get(i);
			sum += Math.pow(xi, 2) - A * Math.cos(2 * Math.PI * xi);
		}
		
		return sum;
	}
	
	@Override
	public int getNumVar() {
		return N;
	}

	@Override
	public double fitness(Solution s) {
		return -this.rastriginFunction(s);
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
		
	    return solString;
	}

}
