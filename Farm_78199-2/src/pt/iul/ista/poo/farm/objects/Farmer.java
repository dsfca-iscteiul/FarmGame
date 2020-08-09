package pt.iul.ista.poo.farm.objects;

import pt.iul.ista.poo.farm.Farm;
import pt.iul.ista.poo.gui.ImageMatrixGUI;
import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Point2D;

public class Farmer extends FarmObject {

	public Farmer(Point2D p) {
		super(p);
	}
	
	public int getLayer(){
		return 3;
	}

	//Objetivo: recebe uma direcao e devolve a proxima posicao do agricultor
	public Point2D nextPosition(Direction d){
		Point2D next = new Point2D(getPosition().getX()+d.asVector().getX(), getPosition().getY()+d.asVector().getY());
		return next;
	}

	public void move(Direction d){
		Point2D pp = nextPosition(d);
		if(pp.getX()>=0 && pp.getX()<ImageMatrixGUI.getInstance().getGridDimension().width && pp.getY()>=0 && 
				pp.getY()<ImageMatrixGUI.getInstance().getGridDimension().height && Farm.getInstance().animalInPos(pp) == null)
			setPosition(pp);
	}

	public String toString(){
		return "Farmer;" + this.getPosition();
	}
}
