package pt.iul.ista.poo.farm.objects;

import pt.iul.ista.poo.farm.Farm;
import pt.iul.ista.poo.farm.objects.estados.EstadoChicken;
import pt.iul.ista.poo.farm.objects.estados.EstadoSheep;
import pt.iul.ista.poo.gui.ImageMatrixGUI;
import pt.iul.ista.poo.utils.Point2D;

public class Chicken extends Animal{  
	private EstadoChicken estado;

	public Chicken(Point2D p, EstadoChicken e) {
		super(p);
		this.estado = e;
	}
	
	public Chicken(Point2D p, EstadoChicken estado, int ciclo){
		super(p);
		setCiclo(ciclo);
		setEstadoChicken(estado);
	}

	public void processaCiclo(){
		if(getCiclo() % 10 == 0 && getCiclo() != 0 && getEstadoChicken().equals(EstadoChicken.CHICKEN) && existEgg() == false){
			Chicken c = new Chicken(getPosition(), EstadoChicken.EGG);
			ImageMatrixGUI.getInstance().addImage(c);
			Farm.getInstance().addToTemporaria(c);
			ImageMatrixGUI.getInstance().update();
		}
		else if(getCiclo() % 2 == 0 && getEstadoChicken().equals(EstadoChicken.CHICKEN)){
			move();
		}else if(getCiclo() == 20){
			setEstadoChicken(EstadoChicken.CHICKEN); 
			move();
		}
	}
	
	public boolean existEgg(){
		for(FarmObject f: Farm.getInstance().getTabuleiro()){
			if(f instanceof Chicken && f.getPosition().equals(getPosition())){
				if(((Chicken) f).getEstadoChicken().equals(EstadoChicken.EGG)){
					return true;
				}
			}
		}
		return false;
	}
	@Override
	public void interact() {
		if(getEstadoChicken().equals(EstadoChicken.CHICKEN)){
			Farm.getInstance().addToTemporaria(this);
			ImageMatrixGUI.getInstance().removeImage(this);
			Farm.getInstance().addPontos(2);
			System.out.println("Galinha apanhada!");
		}else{
			Farm.getInstance().addToTemporaria(this);
			ImageMatrixGUI.getInstance().removeImage(this);
			Farm.getInstance().addPontos(1);
			System.out.println("Ovo apanhado!");

		}
		ImageMatrixGUI.getInstance().update();
		
	}
	
	@Override
	public String getName(){
		if(getEstadoChicken().equals(EstadoChicken.CHICKEN)){
			return "chicken";
		}else{
			return "egg";
		}
	}
	
	public EstadoChicken getEstadoChicken(){
		return this.estado;
	}
	
	public String toString(){
		return "Chicken;" + this.getPosition() + ";" + this.getEstadoChicken() + ";" + this.getCiclo();
	}
	
	public void setEstadoChicken(EstadoChicken e){
		this.estado = e;
	}
	
}
