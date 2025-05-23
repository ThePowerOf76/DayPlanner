package com.thevintagetyrolean.dplanner.client;

import java.awt.Font;
import java.awt.Dimension;
import java.util.ArrayList;
import java.awt.event.*;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.plaf.basic.BasicComboBoxRenderer;

import com.thevintagetyrolean.dplanner.utils.DPIO;
public class Client {
public static final int WINDOW_LENGTH = 800;
public static final int WINDOW_WIDTH = 600;
public static void main(String[] args) {
	
		boolean modes[] = DPIO.ReadArgs(args); //Read command line arguments
		if(!modes[0]) { //if NoGui false
			DGUIMode(modes[1]); 
		} else {
			ClientText.DTextMode(modes[1]);
		}
		

	}

static void DGUIMode(boolean verbose) {
	try {
	JFrame window = new JFrame("DayPlanner");
	window.setSize(WINDOW_WIDTH, WINDOW_LENGTH);
	
	
	ArrayList<String> fileList = new ArrayList<String>(); //List of available entry storages.
	DPIO.ReadFile(fileList, "startup.cfg");
	String username = fileList.get(0);
	window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	window.setResizable(false);
	DrawMenuScreen(window, username, fileList); //Draw storage pick screen.
	SwingUtilities.updateComponentTreeUI(window); //Refresh
	
	window.setLayout(null);
	window.setVisible(true);
	
	} catch(Exception e) {
		e.printStackTrace();
	}
}
private static void DrawMenuScreen(JFrame window, String username, ArrayList<String> fileList) {
	JLabel title = new JLabel("TheVintageTyrolean's Day Planner");
	JLabel greeting = new JLabel("Hello " + username + ".");
	JLabel question = new JLabel("What list do you wish to pick?");
	title.setBounds(30, 100, 500, 150);
	greeting.setBounds(50, 180, 500, 140);
	question.setBounds(50, 200, 500, 140);
	question.setFont(question.getFont().deriveFont(20.0f));
	title.setFont(title.getFont().deriveFont(25.0f));
	greeting.setFont(greeting.getFont().deriveFont(20.0f));
	JPanel titleScreen = new JPanel();
	String[] arr = new String[fileList.size()];
	arr = fileList.toArray(arr);
	arr[0] = "Pick a list...";
	JComboBox<String> files = new JComboBox<String>(arr);
	files.setSelectedIndex(0);
	files.setBounds(250, 300, 250, 200);
	files.setFont(new Font("Serif", Font.BOLD, 20));
    files.setRenderer(new ComboRenderer());
	JButton ContButton = new JButton("Continue");
	titleScreen.add(title);
	titleScreen.add(greeting);
	titleScreen.add(question);
	titleScreen.add(files);
	titleScreen.add(ContButton);
	titleScreen.setBounds(0, 0, WINDOW_WIDTH, WINDOW_LENGTH);
	window.add(titleScreen);

	ContButton.addActionListener(new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			if(files.getSelectedIndex() != 0) {
				DrawFunctionScreen(window, fileList, files.getSelectedIndex()); //Draw screen with storage manipulation options.
			}
			
		}
		
	});
}
private static void DrawFunctionScreen(JFrame window, ArrayList<String> fileList, int index) {
	JPanel pickPanel = new JPanel();
	JButton genB = new JButton("Generate");
	JButton addB = new JButton("Add Entry");
	JButton modB = new JButton("Modify Entry");
	JButton delB = new JButton("Delete Entry");
	JButton addLB = new JButton("Add List");
	JButton modLB = new JButton("Modify List");
	JButton delLB = new JButton("Delete List");
	JButton extB = new JButton("Exit");
	genB.addActionListener(new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			
		}
		
	});
	addB.addActionListener(new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			
		}
		
	});
	modB.addActionListener(new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			//TODO Make gui for this
		}
		
	});
	delB.addActionListener(new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			//TODO Make gui for this
		}
		
	});
	addLB.addActionListener(new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			//TODO Make gui for this
		}
		
	});
	modLB.addActionListener(new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			//TODO Make gui for this
		}
		
	});
	delLB.addActionListener(new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			//TODO Make gui for this
		}
		
	});
	extB.addActionListener(new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			//TODO Make gui for this
		}
		
	});
	pickPanel.add(genB);
	pickPanel.add(addB);
	pickPanel.add(modB);
	pickPanel.add(delB);
	pickPanel.add(addLB);
	pickPanel.add(modLB);
	pickPanel.add(delLB);
	pickPanel.add(extB);
	window.add(pickPanel);
	
}
public static void GGen() {
	//Gui Generate
	JCheckBox pomodoro = new JCheckBox("Use Pomodoro Technique?");
	//TODO Finish generation in GUI
}
//TODO Finish other functions in GUI
}

class ComboRenderer extends BasicComboBoxRenderer {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
    public Dimension getPreferredSize() {
        return new Dimension(200, 80);
    } 
}
