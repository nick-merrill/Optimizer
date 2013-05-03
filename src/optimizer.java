import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;

import algorithms.*;
import problems.*;
import solutions.*;

public class optimizer {
	
	static final Double ZERO_D = new Double(0);
	static final int N_TRIALS = 1;
	
	private static double avgTime = 0;
	private static ArrayList<Double> finalFitnesses;

	public static void main(String[] args) {
	    //dataTest();
		psoTest();
	}
	
	private static void psoTest() {
		ParticleSwarmOpt psoAlg = new ParticleSwarmOpt();
		CuckooSearchOpt csAlg = new CuckooSearchOpt();
		BirdsAndBeesOpt bbAlg = new BirdsAndBeesOpt();
		
		
		//MichaelwiczMinProb prob = new MichaelwiczMinProb();
		//RastriginMinProb prob = new RastriginMinProb();
		EggholderFuncProb prob = new EggholderFuncProb();
		//RosenbrockMinProb prob = new RosenbrockMinProb(4);
		
		
		testAlgProb(psoAlg, prob);
		testAlgProb(csAlg, prob);
		testAlgProb(bbAlg, prob);
	}
	
	private static void testAlgProb(OptimizationAlgorithm optAlg, OptimizationProblem prob) {
		long startTime, endTime;

	 	startTime = System.currentTimeMillis();
	 	optAlg.solve(prob);
	 	endTime = System.currentTimeMillis();
	 	System.out.println("Algorithm Time: " + (endTime - startTime));
	 	
	 	Solution sol = optAlg.getSolutions(prob).getMostFitSolution(prob);
	    sol.print();
	}
	
	private static void dataTest() {
		ParticleSwarmOpt psoAlg = new ParticleSwarmOpt();
		CuckooSearchOpt csAlg = new CuckooSearchOpt();
		BirdsAndBeesOpt bbAlg = new BirdsAndBeesOpt();
		ArrayList<Double> psoAvg, csAvg, bbAvg, psoFinal, csFinal, bbFinal;
		double psoTime, csTime, bbTime;
		
		//RastriginMinProb prob = new RastriginMinProb();
		//EggholderFuncProb prob = new EggholderFuncProb();
		//RosenbrockMinProb prob = new RosenbrockMinProb(4);
		MichaelwiczMinProb prob = new MichaelwiczMinProb();
		
		psoAvg = avgData(psoAlg, prob);
		psoTime = avgTime;
		psoFinal = finalFitnesses;
		csAvg = avgData(csAlg, prob);
		csTime = avgTime;
		csFinal = finalFitnesses;
		bbAvg = avgData(bbAlg, prob);
		bbTime = avgTime;
		bbFinal = finalFitnesses;
		
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter("fitnesses.txt"));
			
			String line = "";
			
			bw.write("PSO Time:" + psoTime + "\tCS Time: " + csTime +"\tBB Time:" + bbTime);
			bw.newLine();
			
			bw.write("iteration\tPSO\tCS\tHybrid");
			bw.newLine();
			
			for(int i=0; i<psoAlg.NUM_DATA; i++) {
				line += (i+1) + "\t";
				line += psoAvg.get(i) + "\t";
				line += csAvg.get(i) + "\t";
				line += bbAvg.get(i) + "\t";
				bw.write(line);
				bw.newLine();
				line = "";
			}
			
			bw.newLine();
			bw.write("Trial\tPSO Final\tCS Final\tHybrid Final\n");
			
			for(int i=0; i<N_TRIALS; i++) {
				line += (i+1) + "\t";
				line += psoFinal.get(i) + "\t";
				line += csFinal.get(i) + "\t";
				line += bbFinal.get(i) + "\t";
				bw.write(line);
				bw.newLine();
				line = "";
			}
			
			
			bw.close();
		} catch(Exception e) {}
	}
	
	private static ArrayList<Double> avgData(OptimizationAlgorithm optAlg, OptimizationProblem prob) {
		long startTime, endTime;
		avgTime = 0;
		finalFitnesses = new ArrayList<Double>(N_TRIALS);
		
		ArrayList<Double> avg = new ArrayList<Double>(Collections.nCopies(optAlg.NUM_DATA, ZERO_D));
		
		for(int i=0; i<N_TRIALS; i++) {
			startTime = System.currentTimeMillis();
			optAlg.solve(prob);
			endTime = System.currentTimeMillis();
			
			avgTime += endTime - startTime;
			
			addLists(avg, optAlg.getFitnesses(), optAlg.NUM_DATA);
			finalFitnesses.add(optAlg.getFitnesses().get(optAlg.NUM_DATA-1));
		}
		divList(avg, N_TRIALS, optAlg.NUM_DATA);
		avgTime /= N_TRIALS;
		
		return avg;
	}
	
	// for testing, adds values in lst2 to lst1
	private static void addLists(ArrayList<Double> lst1, ArrayList<Double> lst2, int n) {
		for(int i=0; i<n; i++) {
			lst1.set(i, lst1.get(i).doubleValue() + lst2.get(i).doubleValue());
		}
	}
	
	// for testing, divides each value in lst by dividend
	private static void divList(ArrayList<Double> lst, int dividend, int n) {
		for(int i=0; i<n; i++) {
			lst.set(i, lst.get(i).doubleValue() / dividend);
		}
	}
}
