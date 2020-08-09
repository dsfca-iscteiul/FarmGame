package pt.iul.ista.poo.farm.objects;

import java.util.List;

import pt.iul.ista.poo.farm.Farm;
import pt.iul.ista.poo.farm.objects.estados.EstadoLand;
import pt.iul.ista.poo.gui.ImageMatrixGUI;
import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Point2D;

public abstract class Animal extends FarmObject implements Updatable, Interactable{
	private int ciclo;
	
	public Animal(Point2D p) {
		super(p);
		this.ciclo = 0;
	}
	
	public void addCiclo(){
		ciclo++;
	}
	
	public int getCiclo(){
		return this.ciclo;
	}

	public void setCiclo(int ciclo){
		this.ciclo = ciclo;
	}
	
	public Point2D posicaoAleatoria(){
		Point2D p;
		do{
			Direction d = Direction.random();		
			p = new Point2D(getPosition().getX() + d.asVector().getX(), getPosition().getY() + d.asVector().getY());

		}while(validPoint(p) == false);
		
		return p;
	}
	
	public boolean validPoint(Point2D p){
		if(p.getX()>=0 && p.getX()<ImageMatrixGUI.getInstance().getGridDimension().width && p.getY()>=0 && 
				p.getY()<ImageMatrixGUI.getInstance().getGridDimension().height){
			return true;
		}
		return false;
	}
	
	public void move(){
		Point2D pp = posicaoAleatoria();
		if(pp.getX()>=0 && pp.getX()<ImageMatrixGUI.getInstance().getGridDimension().width && pp.getY()>=0 && 
				pp.getY()<ImageMatrixGUI.getInstance().getGridDimension().height && 
				Farm.getInstance().animalInPos(pp) == null && !Farm.getInstance().getFarmer().getPosition().equals(pp)){
				setPosition(pp);
				comerVegetal();
			}
	}
	
	public void comerVegetal(){
		List <FarmObject> vegetais = Farm.getInstance().elementosPos(getPosition());
		for(FarmObject f: vegetais){
			if(f instanceof Vegetable && this instanceof Sheep){
				Farm.getInstance().addToTemporaria(f);
				ImageMatrixGUI.getInstance().removeImage(f);
				setLandNaoLavrada();
				System.out.println("Ovelha comeu vegetal!");
				if(this instanceof Sheep){
					((Sheep)this).interact();
				}
			}else if(f instanceof Tomato && this instanceof Chicken){
				Farm.getInstance().addToTemporaria(f);
				ImageMatrixGUI.getInstance().removeImage(f);
				setLandNaoLavrada();
				System.out.println("Galinha comeu tomate!");

			}
		}
		ImageMatrixGUI.getInstance().update();
	}
	
	public void setLandNaoLavrada(){
		List <FarmObject> vegetais = Farm.getInstance().elementosPos(getPosition());
		for(FarmObject f: vegetais){
			if(f instanceof Land && f.getPosition().equals(getPosition())){
				((Land)f).setEstadoLand(EstadoLand.LAND);
			}
		}
	}

	public int getLayer(){
		return 3;
	}
}
