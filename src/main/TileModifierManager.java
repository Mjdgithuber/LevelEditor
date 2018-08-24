package main;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

public class TileModifierManager {
	
	private static ArrayList<ImageIcon> tileModImages;
	private static ArrayList<String> tileModNames;
	
	private static String solid_block_path;
	private static String transparent_block_path;
	private static String teleporter_block_path;
	
	static {
		init();
	}
	
	public static ImageIcon getImage(String path){
		int index = tileModNames.indexOf(path);
		return tileModImages.get(index);
	}
	
	public static String getTransparentModPath(){
		return transparent_block_path;
	}
	
	private static void init() {
		tileModImages = new ArrayList<ImageIcon>();
		tileModNames = new ArrayList<String>();

		final File assetFolder = new File("assets/tile_modifiers");
		final File[] assets = assetFolder.listFiles();

		for (File f : assets){
			try{
				String path = f.toString();
				
				if(path.endsWith("transparentModifier.png"))
					transparent_block_path = path;
				else if(path.endsWith("solidModifier.png"))
					solid_block_path = path;
				else if(path.endsWith("transporterModifier.png"))
					teleporter_block_path = path;
				else
					continue;
				
				tileModNames.add(path);				
				tileModImages.add(new ImageIcon(path));
			}catch(Exception e){}
			//System.out.println(f.toString());
		}
	}
	
	public static ImageIcon getModifierImage(int i){
		return tileModImages.get(i);
	}
	
	public static String getModifierName(int i){
		return tileModNames.get(i);
	}
	
	public static Action getModifierAction(String path){
		if(path.equals(solid_block_path))
			return new Action("solid_block");
		else if(path.equals(transparent_block_path))
			return new Action("transparent_block");
		else if(path.equals(teleporter_block_path)){
			return new Action(openTeleporterDialog());
		}
		
		return null;
	}
	
	private static JPanel levelPanel(){
		JPanel panel = new JPanel();
		GridBagConstraints gbc = new GridBagConstraints();
		panel.setLayout(new GridBagLayout());
		
		gbc.gridy=0;
		gbc.gridx=0;
		panel.add(new JLabel("Level File: "), gbc);
		gbc.gridx++;
		JTextField text = new JTextField();
		text.setPreferredSize(new Dimension(135, 25));
		panel.add(text, gbc);
		
		gbc.gridx--;
		gbc.gridy++;
		panel.add(new JLabel("Spawn X: "), gbc);
		gbc.gridx++;
		SpinnerNumberModel xSpinnerModel = new SpinnerNumberModel(0, 0, 149, 1);
		JSpinner xSpinner = new JSpinner(xSpinnerModel);
		panel.add(xSpinner, gbc);
		gbc.gridx--;
		
		gbc.gridy++;
		panel.add(new JLabel("Spawn Y: "), gbc);
		gbc.gridx++;
		SpinnerNumberModel ySpinnerModel = new SpinnerNumberModel(0, 0, 149, 1);
		JSpinner ySpinner = new JSpinner(ySpinnerModel);
		panel.add(ySpinner, gbc);
		
		gbc.gridx--;
		
		return panel;
	}
	
	private static String action;
	private static String openTeleporterDialog(){
		action = null;
		
		JDialog teleporterDialog;
		GridBagConstraints gbc = new GridBagConstraints();
		
		JFrame f = new JFrame();
		teleporterDialog = new JDialog(f, "New Teleporter", true);
		
		teleporterDialog.setMinimumSize(new Dimension(250, 200));
		teleporterDialog.setResizable(false);
		
		teleporterDialog.setLayout(new GridBagLayout());
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		//gbc.weighty = 1;
		gbc.anchor=GridBagConstraints.CENTER;
		
		teleporterDialog.setAlwaysOnTop(true);
		teleporterDialog.setLocationRelativeTo(null);
		
		gbc.gridwidth = 1;
		teleporterDialog.add(new JLabel("Type:"), gbc);
		gbc.gridx++;
		String[] typeStrings = {"Level", "Shop", "Hospital", "Battle"};
		JComboBox<String> typeList = new JComboBox<String>(typeStrings);
		typeList.setSelectedIndex(0);
		teleporterDialog.add(typeList, gbc);
		
		gbc.gridx--;
		gbc.gridy++;
		gbc.gridwidth = 2;
		JPanel levelPanel = levelPanel();
		teleporterDialog.add(levelPanel, gbc);
		
		gbc.gridy++;
		JButton set = new JButton("Set");
		set.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				String ending = "";
				
				switch(typeList.getSelectedIndex()){
					case 0: { // level
						Component[] comps = levelPanel.getComponents();
						
						ending += "_"+((JTextField)comps[1]).getText()+"_"+
								((JSpinner)comps[3]).getValue()+"_"+
								((JSpinner)comps[5]).getValue();
						break;
					}
					case 1: { // shop
						break;
					}
					case 2: { // hospital
						break;
					}
					case 3: { // battle
						break;
					}
				}
				
				action = "teleporter_block_" + ((String)typeList.getSelectedItem()).toLowerCase()
						+ ending;
				teleporterDialog.dispose();
			}
		});
		teleporterDialog.add(set, gbc);
		
//		
//		gbc.insets = new Insets(10,10,10,10);
//		teleporterDialog.add(new JLabel("Width"), gbc);
//		gbc.gridx++;
//		SpinnerNumberModel xSpinnerModel = new SpinnerNumberModel(10, 10, 150, 1);
//		JSpinner xSpinner = new JSpinner(xSpinnerModel);
//		teleporterDialog.add(xSpinner, gbc);
//		gbc.gridy++;
//		gbc.gridx--;
//		
//		teleporterDialog.add(new JLabel("Height"), gbc);
//		gbc.gridx++;
//		SpinnerNumberModel ySpinnerModel = new SpinnerNumberModel(10, 10, 150, 1);
//		JSpinner ySpinner = new JSpinner(ySpinnerModel);
//		teleporterDialog.add(ySpinner, gbc);
//		
//		gbc.gridy++;
//		gbc.gridwidth = 2;
//		gbc.gridx = 0;
////		gbc.weighty = 2;
//		JButton select = new JButton("Select");
////		create.addActionListener(new ActionListener(){
////			@Override
////			public void actionPerformed(ActionEvent e) {
////				int width = ((Integer) xSpinner.getValue());
////				int height = ((Integer) ySpinner.getValue());
////				worldPane.resetWorld(new Dimension(width, height));
////				newWorld.dispose();
////			}
////		});
//		teleporterDialog.add(select, gbc);
		

		//f.repaint();
		//newWorld.pack();
		
		teleporterDialog.setVisible(true);
		
		return action;
	}
	
	public static int getNumberOfModifiers(){
		return tileModImages.size();
	}
	

}
