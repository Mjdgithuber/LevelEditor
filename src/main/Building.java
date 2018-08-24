package main;

import javax.swing.ImageIcon;

public class Building {
	
	private ImageIcon image;
	private String name;
	private int width, height;
	
	public Building(ImageIcon image, String name){
		this.image = image;
		this.name = name;
		
		ImageIcon nonScaledImage = BuildingManager.getImage(name);
		width = (int)Math.ceil(((nonScaledImage.getIconWidth()*1d)/10d));
		height = (int)Math.ceil(((nonScaledImage.getIconHeight()*1d)/10d));
	}
	
	public Building(){
		makeNull();
	}
	
	public void makeNull(){
		image = null;
		name = null;
		width = 0;
		height = 0;
	}
	
	public ImageIcon getImage(){
		return image;
	}
	
	public String getName(){
		return name;
	}
	
	public int getWidth(){
		return width;
	}
	
	public int getHeight(){
		return height;
	}
	
	private void setImage(ImageIcon img){
		image = img;
//		width = (int)Math.ceil(((image.getIconWidth()*1d)/10d));
//		System.out.println(width);
//		height = (int)Math.ceil(((image.getIconHeight()*1d)/10d));
		ImageIcon nonScaledImage = BuildingManager.getImage(name);
		System.out.println(nonScaledImage.getIconWidth());
		width = (int)Math.ceil(((nonScaledImage.getIconWidth()*1d)/10d));
		height = (int)Math.ceil(((nonScaledImage.getIconHeight()*1d)/10d));
	}
	
	public void setName(String s){
		name = s;
		setImage(BuildingManager.getImage(name));
	}
	
	public boolean isNull(){
		return image == null ? true : false;
	}

}
