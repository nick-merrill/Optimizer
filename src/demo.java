import java.util.ArrayList;

import exceptions.InputException;
import exceptions.PositiveNumberInputException;
import UIs.TerminalUI;
import algorithms.*;
import problems.*;
import solutions.*;

public class demo {
    
	public static void main(String[] args) {
	    System.out.println("Hey! What problem do you want to solve?");
	    
	    TerminalUI gui = new TerminalUI();
	    
	    ArrayList<OptimizationAlgorithm> algs = new ArrayList<OptimizationAlgorithm>();
		algs.add(new CuckooSearchOpt());
		algs.add(new ParticleSwarmOpt());
		
		int algID = gui.getOptionChoice("Which algorithm do you want to use?",
		        new String[]{"Cuckoo Search Optimization",
		        "Particle Swarm Optimization"});
	    
	    double fenceLength;
        try {
            fenceLength = gui.getDoubleInput("fence length");
        } catch (InputException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return;
        }
	    FenceProblem fenceProb;
        try {
            fenceProb = new FenceProblem(fenceLength);
        } catch (PositiveNumberInputException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return;
        }
	    algs.get(algID).solve(fenceProb);
	    algs.get(algID).getSolutions(fenceProb).getMostFitSolution(fenceProb).print();
	    
//		int numEmployees = 20;
//		NurseSchedProb optProb = new NurseSchedProb(numEmployees);
//		optAlg.solve(optProb);
	    
		/*
	    
	    
	    double volume = 100.;
	    BoxMinAreaProb boxProb = new BoxMinAreaProb(volume);
	    csAlg.solve(boxProb);
	    csAlg.getSolutions(boxProb).getMostFitSolution(boxProb).print();
	    */
	    /*
	    MichaelwiczMinProb michaelwiczProb = new MichaelwiczMinProb();
	    csAlg.solve(michaelwiczProb);
	    Solution michaelwiczSol = csAlg.getSolutions(michaelwiczProb).getMostFitSolution(michaelwiczProb);
	    michaelwiczSol.print();
	    System.out.printf("Michaelwicz minimum: %f\n", michaelwiczProb.eval(michaelwiczSol));
	    
	    psoAlg.solve(michaelwiczProb);
//	    ArrayList<PSOSolution> solutions = (ArrayList<PSOSolution>) psoAlg.getSolutions(michaelwiczProb).getSolutions();
//	    for(PSOSolution sol : solutions) {
//	    	sol.printAll();
//	    }
	    Solution michaelwiczSol2 = psoAlg.getSolutions(michaelwiczProb).getMostFitSolution(michaelwiczProb);
	    michaelwiczSol2.print();
	    System.out.printf("Michaelwicz minimum: %f\n", michaelwiczProb.eval(michaelwiczSol2));
	    */
	}

}
