package pt.iul.ista.poo.farm.objects;

import java.util.List;
import java.util.Random;

import pt.iul.ista.poo.farm.Farm;
import pt.iul.ista.poo.farm.objects.estados.EstadoLand;
import pt.iul.ista.poo.gui.ImageMatrixGUI;
import pt.iul.ista.poo.utils.Point2D;


public class Land extends FarmObject implements Interactable{
	private EstadoLand estado; 
	private static final double probRock = 0.10;

	public Land(Point2D p) {
		super(p);
		Random r = new Random();
		if(r.nextDouble() < probRock){
			this.estado = EstadoLand.ROCK;
		}else{
			this.estado = EstadoLand.LAND;
		}
	}

	public Land(Point2D p, EstadoLand estado){
		super(p);
		this.estado = estado;
	}
	public int getLayer(){
		return 0;
	}
	
	public String getName(){
		if(estado.equals(EstadoLand.LAND)){
			return "land";
		}else if(estado.equals(EstadoLand.PLOWED)){
			return "plowed";
		}else{
			return "rock";
		}
	}
	@Override
	public void interact() {
		if(estado.equals(EstadoLand.LAND)){ 
			estado = EstadoLand.PLOWED;
			
		}else if(estado.equals(EstadoLand.PLOWED)){   
			generateVegetable(getPosition());		
		}
	}
	
	public EstadoLand getEstado(){ //
		return this.estado;
	}
	
	public void setEstadoLand(EstadoLand e){
		this.estado = e;
	}
	
	public String toString(){
		return "Land;" + this.getPosition() + ";" + this.getName();
	}
	
	/**Funcao para criar um vegetal aleatorio
	 */
	public Vegetable generateVegetable(Point2D p){
		Random r = new Random();
		boolean q = r.nextBoolean();
		Vegetable v = null;
		if(q==true){
			v = new Cabage (p);              
			
		}else{
			v = new Tomato(p);
		}
		List <FarmObject> objects = Farm.getInstance().elementosPos(p);
		for(FarmObject f: objects){
			if(f instanceof Vegetable){ 
				return null;
			}
		}
		ImageMatrixGUI.getInstance().addImage(v);
		Farm.getInstance().getTabuleiro().add(v);
		ImageMatrixGUI.getInstance().update();
		return v; 
	}
}


