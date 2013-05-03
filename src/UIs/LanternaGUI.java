package UIs;

import java.util.ArrayList;

import com.googlecode.lanterna.TerminalFacade;
import com.googlecode.lanterna.gui.Action;
import com.googlecode.lanterna.gui.GUIScreen;
import com.googlecode.lanterna.gui.Window;
import com.googlecode.lanterna.gui.component.Button;
import com.googlecode.lanterna.gui.component.Label;
import com.googlecode.lanterna.gui.dialog.MessageBox;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.Terminal;

import problems.OptimizationProblem;
import solutions.Solution;

public class LanternaGUI extends OptimizationUI {
    
    private GUIScreen gui;
    
    public LanternaGUI() {
        Terminal terminal = TerminalFacade.createTerminal();
        gui = TerminalFacade.createGUIScreen();
        if (gui == null) {
            System.err.println("Couldn't allocate a terminal!");
            return;
        }
        gui.getScreen().startScreen();
        
    }

    @Override
    public String getVariableInput(String varName) {
        final Window window = new Window("Please enter " + varName);
        gui.showWindow(window, GUIScreen.Position.CENTER);
        window.addComponent(new Button("Done", new Action() {
            public void doAction() {
                MessageBox.showMessageBox(window.getOwner(), "Hello", "You selected the button with an action attached to it.");
            }
        }));
        String s = null;
		return s;
    }

    @Override
    public void printSolution(Solution solution) {
        // TODO Auto-generated method stub
        
    }

	@Override
	public String getFile(String request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getOptionChoice(String prompt, String[] options) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void display(String output) {
		// TODO Auto-generated method stub
		
	}

}
