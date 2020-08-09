package pt.iul.ista.poo.farm.objects;

import pt.iul.ista.poo.farm.Farm;
import pt.iul.ista.poo.farm.objects.estados.Estado;
import pt.iul.ista.poo.utils.Point2D;

public class Tomato extends Vegetable{

	public Tomato(Point2D p) {
		super(p);		
	}
	
	public Tomato (Point2D p, Estado estado, int ciclo, boolean tratado){
		super(p);
		setEstado(estado);
		setCiclo(ciclo);
		setTratado(tratado);
	}
	
	@Override
	public void interact() { 
		if(getEstado().equals(Estado.PLANTADO)){ 
			setTratado(true);
		}else if(getEstado().equals(Estado.MADURO) || getEstado().equals(Estado.ESTRAGADO)){
			colherVegetal(getPosition());
		}	
	}
	
	@Override
	public String getName(){
		if(getEstado().equals(Estado.PLANTADO)){
			return "small_tomato";
		}if(getEstado().equals(Estado.MADURO)){
			return "tomato";
		}if(getEstado().equals(Estado.ESTRAGADO)){
			return "bad_tomato";
		}
		return "planted";
	}
	
	public void processaCiclo(){
		System.out.println(getTratado());
		if(getEstado().equals(Estado.PLANTADO) && getCiclo() >= 15 && getCiclo() < 25 && getTratado() == true){
			setEstado(Estado.MADURO);
		}else if(getEstado().equals(Estado.MADURO) && getCiclo() >= 25){
			setEstado(Estado.ESTRAGADO);
		}else if(getEstado().equals(Estado.PLANTADO) && getCiclo() >= 25 ){
			setEstado(Estado.ESTRAGADO);
		}
	}
	
	public String toString(){
		return "Tomato;" + this.getPosition() + ";" + this.getEstado() + ";" + this.getCiclo() + ";" + this.getTratado();
	}

}
