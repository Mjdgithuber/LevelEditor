package main;

import javax.swing.ImageIcon;

public class Tile {
	
	private ImageIcon image;
	private String name;
	
//	public Tile(ImageIcon image, String name){
//		this.image = image;
//		this.name = name;
//	}
	
	public Tile(String name){
		setName(name);
	}
	
	public ImageIcon getImage(){
		return image;
	}
	
	public String getName(){
		return name;
	}
	
	private void setImage(ImageIcon img){
		image = img;
	}
	
	public void setName(String s){
		name = s;
		setImage(TileManager.getImage(s));
	}

}
