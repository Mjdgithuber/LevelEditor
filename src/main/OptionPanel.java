package main;

import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class OptionPanel extends JPanel {
	
	GridBagConstraints gbc = new GridBagConstraints();
	//ArrayList<JButton> buttons = new ArrayList<JButton>();
	//private ImageIcon img;
	private String name = null;
	private String tool;
	private Action action;
	
	private final Color DEFUALT_COLOR = new JButton().getBackground();
	private final Color SELECTED_COLOR = Color.GREEN;
	
	private static final int BUTTON_SIZE = 75;
	
	public boolean isSelectionNull(){
		return name == null;
	}
	
	public OptionPanel(){
//		Dimension size = getPreferredSize();
//		size.width = 150;
//		//size.height = 25;
//		setPreferredSize(size);
		
		setBorder(BorderFactory.createTitledBorder("Selector"));
		setLayout(new GridBagLayout());
		gbc.gridx = 0;
		gbc.gridy = 0;
		
		this.setFocusable(false);
		
		tool = "Block";
		String[] typeStrings = {"Blocks", "Buildings", "Modifiers"};
		JComboBox<String> typeList = new JComboBox<String>(typeStrings);
		typeList.setSelectedIndex(0);
		typeList.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				int index = typeList.getSelectedIndex();
				name = null;
				action = null;
				switch(index){
					case 0: {
						tool = "Block";
						hideButtonsBut("Blocks");
						break;
					}
					case 1: {
						tool = "Building";
						hideButtonsBut("Buildings");
						break;
					}
					case 2: {
						tool = "Tile_Modifier";
						hideButtonsBut("Tile_Modifiers");
					}
				}
			}
		});
		add(typeList, gbc);
		
		for(int i = 0; i<TileManager.getNumberOfBlocks(); i++){
			addButton(getScaledImage(TileManager.getTileImage(i)), TileManager.getTileName(i), "Blocks");
		}
		
		for(int i = 0; i<BuildingManager.getNumberOfBuildings(); i++){
			addButton(getScaledImage(BuildingManager.getBuildingImage(i)), BuildingManager.getBlockName(i), "Buildings");
		}
		
		for(int i = 0; i<TileModifierManager.getNumberOfModifiers(); i++){
			addButton(getScaledImage(TileModifierManager.getModifierImage(i)), TileModifierManager.getModifierName(i), "Tile_Modifiers");
		}
		
		hideButtonsBut("Blocks");
	}
	
	private ImageIcon getScaledImage(ImageIcon icon){
		ImageIcon imageIcon = icon; // load the image to a imageIcon
		Image image = imageIcon.getImage(); // transform it 
		Image newimg = image.getScaledInstance(BUTTON_SIZE, BUTTON_SIZE,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
		return new ImageIcon(newimg);
	}
	
	public String getTool(){
		return tool;
	}
	
	public Action getCurrentAction(){
		return action;
	}
	
	public String getCurrentAssetName(){
		return name;
	}
	
	private void hideButtonsBut(String name){
		// hides all buttons
		for(JButton b : blockButtons)
			b.setVisible(false);
		for(JButton b : buildingButtons)
			b.setVisible(false);
		for(JButton b : modifierButtons)
			b.setVisible(false);
		
		// reveals one type of button
		switch(name){
			case "Blocks": {
				for(JButton b : blockButtons)
					b.setVisible(true);
				break;
			}
			case "Buildings": {
				for(JButton b : buildingButtons)
					b.setVisible(true);
				break;
			}
			case "Tile_Modifiers": {
				for(JButton b : modifierButtons)
					b.setVisible(true);
				break;
			}
		}
		
		resetButtonColors();
	}
	
	private void resetButtonColors(){
		for(JButton b : blockButtons)
			b.setBackground(DEFUALT_COLOR);
		for(JButton b : buildingButtons)
			b.setBackground(DEFUALT_COLOR);
		for(JButton b : modifierButtons)
			b.setBackground(DEFUALT_COLOR);
	}
	
	private ArrayList<JButton> blockButtons = new ArrayList<JButton>();
	private ArrayList<JButton> buildingButtons = new ArrayList<JButton>();
	private ArrayList<JButton> modifierButtons = new ArrayList<JButton>();
	public void addButton(ImageIcon image, String buttonName, String type){
		gbc.gridx = 0;
		gbc.gridy += 1;
		gbc.anchor = gbc.CENTER;
		
		JButton b = new JButton(image);
		
		switch(type){
			case "Blocks": {
				blockButtons.add(b);
				break;
			}
			case "Buildings": {
				buildingButtons.add(b);
				break;
			}
			case "Tile_Modifiers": {
				modifierButtons.add(b);
				break;
			}
		}
		
		b.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				//img = image;
				name = buttonName;
				if(modifierButtons.contains(b))
					action = TileModifierManager.getModifierAction(buttonName);
				else
					action = null;
				System.out.println(action);
				resetButtonColors();
				b.setBackground(SELECTED_COLOR);
			}
		});

		
		
		//buttons.add(b);
		add(b, gbc);
	}
	
	public void reset(){
		gbc.gridx = 0;
		gbc.gridy = 0;
			
		removeAll();
		revalidate();
		repaint();
	}
	public void setUp(){} // this will be overridden
	
	public void setSize(int width){
		Dimension size = getPreferredSize();
		
		size.width = width;
		setPreferredSize(size);
	}
	
}
