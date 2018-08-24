package main;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class GUIFrame extends JFrame {
	
	public static final int INITIAL_WIDTH = 753, INITIAL_HEIGHT = 698;
	
	private OptionPanel optionPanel;
	
	/**
	 * Creates the frame for the application and sets up the
	 * necessary classes
	 * @param title
	 */
	public GUIFrame(String title){
		super(title);
		setMinimumSize(new Dimension(INITIAL_WIDTH, INITIAL_HEIGHT));
		
		// sets the layout manager
		setLayout(new BorderLayout());
		
		// creates the panels
		optionPanel = new OptionPanel();
		JScrollPane pane = new JScrollPane(optionPanel);
		pane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		pane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		pane.setSize(new Dimension(250,500));
		
		WorldPane world = new WorldPane(optionPanel);
		
		TopPanel tp = new TopPanel(world);
		world.sendTopPanel(tp);
		
		
		// gets the parent container and adds the components to it
		Container c = getContentPane();
		c.add(world, BorderLayout.CENTER);
		c.add(pane, BorderLayout.WEST);
		c.add(tp, BorderLayout.NORTH);
	}
	
	/**
	 * Catches the resizing events and compensates
	 */
	public void catchResize(){
		
	}

}
