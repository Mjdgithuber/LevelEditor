package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class WorldPane extends JPanel implements MouseListener, MouseMotionListener, KeyListener {

	private OptionPanel op;
	private TopPanel tp;

	private final int startingSize = 10;
	private Tile[][] tiles = new Tile[startingSize][startingSize];
	private Building[][] buildings = new Building[startingSize][startingSize];
	private TileModifier[][] modifiers = new TileModifier[startingSize][startingSize];
//	private ImageIcon[][] tiles = new ImageIcon[10][10];
//	private String[][] tileNames = new String[10][10];
	private final int size = 60;
	private boolean inited;
	private boolean showLines, showModifiers;
	private int xOffset=0, yOffset=0;
	private int x, y; // this is for displaying only on the top panel

	public WorldPane(OptionPanel optionPanel) {
		op = optionPanel;
		showLines = true;

		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		this.addKeyListener(this);
		//this.setPreferredSize(new Dimension(602, 601));
		init();
	}
	
	public void sendTopPanel(TopPanel panel){
		tp = panel;
	}
	
	public void init(){
		if(!inited){
			String transModPath = TileModifierManager.getTransparentModPath();
			for (int i = 0; i < tiles.length; i++)
				for (int j = 0; j < tiles[0].length; j++){
					tiles[i][j] = new Tile(TileManager.getVoidTilePath());
					buildings[i][j] = new Building();
					modifiers[i][j] = new TileModifier(transModPath, TileModifierManager.getModifierAction(transModPath));
				}
			inited = true;
		}
	}
	
	public void resetWorld(Dimension size){
		int x = size.width;
		int y = size.height;
		
		// resets the arrays
		tiles = new Tile[x][y];
		buildings = new Building[x][y];
		modifiers = new TileModifier[x][y];
		
		// reset offsets to make it (0,0)
		xOffset=0;
		yOffset=0;
		
		inited = false;
		init();
		repaint();
	}
	
	public Tile[][] getTiles(){
		return tiles;
	}
	
	public Building[][] getBuildings(){
		return buildings;
	}
	
	public TileModifier[][] getModifiers(){
		return modifiers;
	}
	
	public void loadWorld(World w){
		xOffset=0;
		yOffset=0;
		tiles = w.getTiles();
		buildings = w.getBuildings();
		//TODO put in modifiers
		repaint();
	}

	static Image iconToImage(Icon icon) {
		if (icon instanceof ImageIcon) {
			return ((ImageIcon) icon).getImage();
		} else {
			int w = icon.getIconWidth();
			int h = icon.getIconHeight();
			GraphicsEnvironment ge = GraphicsEnvironment
					.getLocalGraphicsEnvironment();
			GraphicsDevice gd = ge.getDefaultScreenDevice();
			GraphicsConfiguration gc = gd.getDefaultConfiguration();
			BufferedImage image = gc.createCompatibleImage(w, h);
			Graphics2D g = image.createGraphics();
			icon.paintIcon(null, g, 0, 0);
			g.dispose();
			return image;
		}
	}
	
	public void setShowLines(boolean b){
		showLines = b;
		repaint();
	}
	
	public void setShowModifiers(boolean b){
		showModifiers = b;
		repaint();
	}

	public void paintComponent(Graphics g1) {
		init();
		
		System.out.println(this.getWidth()/10 + " " + this.getHeight()/10);
		BufferedImage img = new BufferedImage(this.getWidth(),
				this.getHeight(), BufferedImage.TYPE_INT_RGB);

		Graphics2D g = img.createGraphics();
		Color oldColor = g.getColor();
		// g.setPaint(Color.black);
		// g.fillRect(0, 0, this.getWidth(), this.getHeight());
		
		for (int i = 0; i < tiles.length; i++)
			for (int j = 0; j < tiles[0].length; j++) {
				g.drawImage(iconToImage(tiles[i][j].getImage()), (i-xOffset) * size, (j-yOffset) * size, size,
						size, null);
			}
		
		for (int i = 0; i < buildings.length; i++)
			for (int j = 0; j < buildings[0].length; j++) {
				Building b = buildings[i][j];
				if(!b.isNull()){
					g.drawImage(iconToImage(buildings[i][j].getImage()), (i-xOffset) * size, (j-yOffset) * size, b.getWidth()*size,
						b.getHeight()*size, null);
				}
				
			}
		
		if(showModifiers){
			for (int i = 0; i < modifiers.length; i++)
				for (int j = 0; j < modifiers[0].length; j++) {
					g.drawImage(iconToImage(modifiers[i][j].getImage()), (i-xOffset) * size, (j-yOffset) * size, size,
							size, null);
				}
		}

		if(showLines){
			g.setPaint(Color.red);
			for (int i = 0; i < 10; i++) {
				int start = (this.getWidth() / 10) * i;
				g.drawLine(start, 0, start, this.getHeight());
			}
			for (int i = 0; i < 10; i++) {
				int start = (this.getHeight() / 10) * i;
				g.drawLine(0, start, this.getWidth(), start);
			}
		}
		
		g.setColor(oldColor);
		
		g1.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), null);
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {}

	@Override
	public void mouseEntered(MouseEvent arg0) {}

	@Override
	public void mouseExited(MouseEvent arg0) {}

	@Override
	public void mousePressed(MouseEvent e) {
//		System.out.println(this.getWidth() + " " + this.getHeight());
		requestFocusInWindow();
		
		if(!op.isSelectionNull()){
			int cellX = e.getX()/size + xOffset;
			int cellY = e.getY()/size + yOffset;
			//System.out.println(cellX + " " + cellY);
			switch(op.getTool()){
				case "Block": {
					tiles[cellX][cellY].setName(op.getCurrentAssetName());
					break;
				}
				case "Building": {
					if(op.getCurrentAssetName().equals(BuildingManager.getNullBuildingPath()))
						buildings[cellX][cellY].makeNull();
					else
						buildings[cellX][cellY].setName(op.getCurrentAssetName());
					break;
				}
				case "Tile_Modifier": {
					modifiers[cellX][cellY].setName(op.getCurrentAssetName(), op.getCurrentAction());
					break;
				}
			}
			repaint();
		}
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {}

	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {}

	@Override
	public void keyReleased(KeyEvent e) {
		//TODO make these update x and y coord on top panel
		if(e.getKeyCode() == KeyEvent.VK_RIGHT)
			if(10+xOffset<tiles.length){
				xOffset++;
				x++;
				tp.refreshCoords(x, y);
			}
		if(e.getKeyCode() == KeyEvent.VK_LEFT)
			if(xOffset>0){
				xOffset--;
				x--;
				tp.refreshCoords(x, y);
			}
		if(e.getKeyCode() == KeyEvent.VK_UP)
			if(yOffset>0){
				yOffset--;
				y--;
				tp.refreshCoords(x, y);
			}
		if(e.getKeyCode() == KeyEvent.VK_DOWN)
			if(10+yOffset<tiles[0].length){
				yOffset++;
				y++;
				tp.refreshCoords(x, y);
			}
		
		repaint();
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {}

	public int getXCoord(){
		return x;
	}
	
	public int getYCoord(){
		return y;
	}
	
	@Override
	public void mouseMoved(MouseEvent e) {
		x = e.getX()/size + xOffset;
		y = e.getY()/size + yOffset;
		tp.refreshCoords(x, y);
	}

}
