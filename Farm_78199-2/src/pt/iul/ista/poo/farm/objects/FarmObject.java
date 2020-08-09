package pt.iul.ista.poo.farm.objects;

import java.util.Random;

import pt.iul.ista.poo.gui.ImageMatrixGUI;
import pt.iul.ista.poo.gui.ImageTile;
import pt.iul.ista.poo.utils.Point2D;

public abstract class FarmObject implements ImageTile {

	private Point2D position;
	private String imageName;
	
	
	public FarmObject(Point2D p) {
		this.position = p;
		this.imageName = getClass().getSimpleName().toLowerCase(); 
		
	}

	@Override
	public String getName() {
		return imageName; 
	}

	@Override
	public Point2D getPosition() {
		return position;
	}

	@Override
	public int getLayer() {
		return 0;
	}
	
	public void setPosition(Point2D p){ 
		position = p;
	}
	
	public void setImageName(String img){ 
		this.imageName = img;
	}
	
}
