package main;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

public class TopPanel extends JPanel {
	
	GridBagConstraints gbc = new GridBagConstraints();
	private WorldPane worldPane;
	private JDialog newWorld;
	
	private JLabel xCoord, yCoord;
	
	public TopPanel(WorldPane wp){
		worldPane = wp;
		
		this.setSize(new Dimension(50, 50));
		setBorder(BorderFactory.createTitledBorder("Options"));
//		this.add(new JRadioButton("Solid"));
//		this.add(new JRadioButton("Transparent"));
//		this.add(new JRadioButton("Transporter"));
		xCoord = new JLabel("X: 0");
		yCoord = new JLabel("Y: 0");
		this.add(xCoord);
		this.add(yCoord);
		
		JCheckBox lines = new JCheckBox("Show Lines", true);
		lines.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				wp.setShowLines(lines.isSelected());
			}
		});
		this.add(lines);
		
		JCheckBox modifiers = new JCheckBox("Show Modifiers", false);
		modifiers.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				wp.setShowModifiers(modifiers.isSelected());
			}
		});
		this.add(modifiers);
		
		JButton newWorldButton = new JButton("New");
		newWorldButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				openNewWorldDialog();
			}
		});
		this.add(newWorldButton);
		
		JButton save = new JButton("Save");
		save.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				SaveManager.initiateSave(worldPane.getTiles(), worldPane.getBuildings());
			}
		});
		this.add(save);
		
		JButton open = new JButton("Open");
		open.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				World w = SaveManager.getWorld();
				worldPane.loadWorld(w);
			}
		});
		this.add(open);
	}
	
	public void refreshCoords(int x, int y){
		xCoord.setText("X: "+x);
		yCoord.setText("Y: "+y);
	}
	
	private void openNewWorldDialog(){
		JFrame f = new JFrame();
		newWorld = new JDialog(f, "New World", true);
		
		newWorld.setMinimumSize(new Dimension(250, 200));
		newWorld.setResizable(false);
		
		newWorld.setLayout(new GridBagLayout());
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		//gbc.weighty = 1;
//		gbc.fill=GridBagConstraints.BOTH;
		gbc.anchor=GridBagConstraints.CENTER;
		
		newWorld.setAlwaysOnTop(true);
		newWorld.setLocationRelativeTo(null);
		
		gbc.gridwidth = 2;
		newWorld.add(new JLabel("<HTML><U>Please Enter Desired Dimensions</U></HTML>"), gbc);
		gbc.gridy++;
		gbc.gridwidth = 1;
		
		gbc.insets = new Insets(10,10,10,10);
		newWorld.add(new JLabel("Width"), gbc);
		gbc.gridx++;
		SpinnerNumberModel xSpinnerModel = new SpinnerNumberModel(10, 10, 150, 1);
		JSpinner xSpinner = new JSpinner(xSpinnerModel);
		newWorld.add(xSpinner, gbc);
		gbc.gridy++;
		gbc.gridx--;
		
		newWorld.add(new JLabel("Height"), gbc);
		gbc.gridx++;
		SpinnerNumberModel ySpinnerModel = new SpinnerNumberModel(10, 10, 150, 1);
		JSpinner ySpinner = new JSpinner(ySpinnerModel);
		newWorld.add(ySpinner, gbc);
		
		gbc.gridy++;
		gbc.gridwidth = 2;
		gbc.gridx = 0;
//		gbc.weighty = 2;
		JButton create = new JButton("Create");
		create.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				int width = ((Integer) xSpinner.getValue());
				int height = ((Integer) ySpinner.getValue());
				worldPane.resetWorld(new Dimension(width, height));
				newWorld.dispose();
			}
		});
		newWorld.add(create, gbc);
		

		//f.repaint();
		//newWorld.pack();
		
		newWorld.setVisible(true);
	}

}













