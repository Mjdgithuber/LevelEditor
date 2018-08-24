package main;
import java.awt.Image;
import java.io.File;
import java.util.ArrayList;

import javax.swing.ImageIcon;

public class BuildingManager {

	private static ArrayList<ImageIcon> buildingImages;
	private static ArrayList<String> buildingNames;
	
	public static String nullPath;

	static {
		init();
	}
	
	public static ImageIcon getImage(String path){
		int index = buildingNames.indexOf(path);
		return buildingImages.get(index);
	}
	
	public static String getNullBuildingPath(){
		return nullPath;
	}

	private static void init() {
		buildingImages = new ArrayList<ImageIcon>();
		buildingNames = new ArrayList<String>();

		final File assetFolder = new File("assets/buildings");
		final File[] assets = assetFolder.listFiles();

		for (File f : assets){
			try{
				String path = f.toString();
				buildingNames.add(path);
				
				// just checking to see if the building is the null one
				if(path.endsWith("aaanull.png"))
					nullPath = path;
				
				buildingImages.add(new ImageIcon(path));
			}catch(Exception e){}
			//System.out.println(f.toString());
		}
	}
	
	public static ImageIcon getBuildingImage(int i){
		return buildingImages.get(i);
	}
	
	public static String getBlockName(int i){
		return buildingNames.get(i);
	}
	
//	public static ImageIcon getBuildingImage(int i, int width, int height){
//		ImageIcon imageIcon = buildingImages.get(i); // load the image to a imageIcon
//		Image image = imageIcon.getImage(); // transform it 
//		Image newimg = image.getScaledInstance(width, height,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
//		imageIcon = new ImageIcon(newimg);
//		return imageIcon;
//	}
//	
	public static int getNumberOfBuildings(){
		return buildingImages.size();
	}

}
