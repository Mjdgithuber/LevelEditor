package main;

public class World {

	private Tile[][] tiles;
	private Building[][] buildings;
	
	public World(String[][] tileImagePaths, String[][] buildingImagePaths){
		tiles = new Tile[tileImagePaths.length][tileImagePaths[0].length];
		generateTiles(tileImagePaths);
		
		buildings = new Building[buildingImagePaths.length][buildingImagePaths[0].length];
		generateBuilding(buildingImagePaths);
		
//		tileNames = Arrays.copyOf(names, names.length);
//		tiles = new ImageIcon[tileNames.length][tileNames[0].length];
//		generateTileImages();
	}
	
	private void generateBuilding(String[][] buildingImagePaths){
		for(int i = 0; i<buildingImagePaths.length; i++)
			for(int j = 0; j<buildingImagePaths[0].length; j++){
				if(buildingImagePaths[i][j] != null)
					buildings[i][j] = new Building(BuildingManager.getImage(buildingImagePaths[i][j]),
							buildingImagePaths[i][j]);
				else
					buildings[i][j] = new Building();
			}
				
	}
	
	private void generateTiles(String[][] tileImagePaths){
		for(int i = 0; i<tileImagePaths.length; i++)
			for(int j = 0; j<tileImagePaths[0].length; j++)
				tiles[i][j] = new Tile(tileImagePaths[i][j]);
	}
	
	public Building[][] getBuildings(){
		return buildings;
	}
	
	public Tile[][] getTiles(){
		return tiles;
	}

}
