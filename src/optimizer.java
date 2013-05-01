import java.util.ArrayList;
import java.util.Arrays;

import exceptions.InputException;

import UIs.LanternaGUI;
import algorithms.*;
import problems.*;
import solutions.*;

public class optimizer {

	public static void main(String[] args) {
	    
//	    LanternaGUI gui = new LanternaGUI();
//	    
////		int numEmployees = 20;
////		NurseSchedProb optProb = new NurseSchedProb(numEmployees);
		CuckooSearchOpt csAlg = new CuckooSearchOpt();
//		ParticleSwarmOpt psoAlg = new ParticleSwarmOpt();
////		optAlg.solve(optProb);
//	    
//		/*
//	    double fenceLength = 100.;
//	    FenceProblem fenceProb = new FenceProblem(fenceLength);
//	    csAlg.solve(fenceProb);
//	    csAlg.getSolutions(fenceProb).getMostFitSolution(fenceProb).print();
//	    
//	    
//	    double volume = 100.;
//	    BoxMinAreaProb boxProb = new BoxMinAreaProb(volume);
//	    csAlg.solve(boxProb);
//	    csAlg.getSolutions(boxProb).getMostFitSolution(boxProb).print();
//	    */
//	    
//	    MichaelwiczMinProb michaelwiczProb = new MichaelwiczMinProb();
//	    csAlg.solve(michaelwiczProb);
//	    Solution michaelwiczSol = csAlg.getSolutions(michaelwiczProb).getMostFitSolution(michaelwiczProb);
//	    michaelwiczSol.print();
//	    System.out.printf("Michaelwicz minimum: %f\n", michaelwiczProb.eval(michaelwiczSol));
//	    
//	    psoAlg.solve(michaelwiczProb);
////	    ArrayList<PSOSolution> solutions = (ArrayList<PSOSolution>) psoAlg.getSolutions(michaelwiczProb).getSolutions();
////	    for(PSOSolution sol : solutions) {
////	    	sol.printAll();
////	    }
//	    Solution michaelwiczSol2 = psoAlg.getSolutions(michaelwiczProb).getMostFitSolution(michaelwiczProb);
//	    michaelwiczSol2.print();
////	    System.out.printf("Michaelwicz minimum: %f\n", michaelwiczProb.eval(michaelwiczSol2));
	    
	    
    	int numEmployees = 3;
    	int numDays = 3;
    	int numShifts = 4;
    	int maxShiftsInRow = 1;
    	Integer[] shiftReqArr = new Integer[]{1,1,1,0};
    	ArrayList<Integer> shiftReq1 = new ArrayList<Integer>(Arrays.asList(shiftReqArr));
    	ArrayList<Integer> shiftReq2 = new ArrayList<Integer>(Arrays.asList(shiftReqArr));
    	ArrayList<Integer> shiftReq3 = new ArrayList<Integer>(Arrays.asList(shiftReqArr));
//    	ArrayList<Integer> shiftReq4 = new ArrayList<Integer>(Arrays.asList(shiftReqArr));
//    	ArrayList<Integer> shiftReq5 = new ArrayList<Integer>(Arrays.asList(shiftReqArr));
//    	ArrayList<Integer> shiftReq6 = new ArrayList<Integer>(Arrays.asList(shiftReqArr));
//    	ArrayList<Integer> shiftReq7 = new ArrayList<Integer>(Arrays.asList(shiftReqArr));
    	
    	ArrayList<ArrayList<Integer>> shiftReqs = new ArrayList<ArrayList<Integer>>();
    	shiftReqs.add(shiftReq1);
    	shiftReqs.add(shiftReq2);
    	shiftReqs.add(shiftReq3);
//    	shiftReqs.add(shiftReq4);
//    	shiftReqs.add(shiftReq5);
//    	shiftReqs.add(shiftReq6);
//    	shiftReqs.add(shiftReq7);
    	
    	Integer[] prefArr = new Integer[12];
    	int j = 1;
    	for (int i = 0; i < 12; i++) {
    	    if (j == 5) j = 1;
    	    prefArr[i] = j;
    	    j++;
    	}
    	ArrayList<Integer> pref1 = new ArrayList<Integer>(Arrays.asList(prefArr));
    	j = 4;
    	for (int i = 0; i < 12; i++) {
    	    if (j == 5) j = 1;
    	    prefArr[i] = j;
    	    j++;
    	}
    	ArrayList<Integer> pref2 = new ArrayList<Integer>(Arrays.asList(prefArr));
    	j = 3;
    	for (int i = 0; i < 12; i++) {
    	    if (j == 5) j = 1;
    	    prefArr[i] = j;
    	    j++;
    	}
    	ArrayList<Integer> pref3 = new ArrayList<Integer>(Arrays.asList(prefArr));
    	
    	ArrayList<ArrayList<Integer>> preferences = new ArrayList<ArrayList<Integer>>();
    	preferences.add(pref1);
    	preferences.add(pref2);
    	preferences.add(pref3);
    	

    	NurseSchedProb nProb;
		try {
			nProb = new NurseSchedProb(numEmployees, numDays, numShifts, maxShiftsInRow, shiftReqs, preferences);
			csAlg.solve(nProb);
	    	SolutionSet sols = csAlg.getSolutions(nProb);
	    	int n = 15;
	    	int counter = 0;
	    	nProb.printSol(sols.getSol(0));
	    	for (int i = 1; i < n; i++) {
	    		if (!nProb.solsAreEqual(sols.getSol(i), sols.getSol(i-1))){
	    			nProb.printSol(sols.getSol(i-1));
	    			System.out.println("("+counter+")");
	    			counter = 0;
	    			System.out.println(nProb.fitness(sols.getSol(i-1)));
	    		}
	       		else counter++;
	    	}
		} catch (InputException e) {
			e.printStackTrace();
		}
    	
    	
    	
	}

}
