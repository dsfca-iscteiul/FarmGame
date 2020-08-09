package pt.iul.ista.poo.farm.objects;

import pt.iul.ista.poo.farm.Farm;
import pt.iul.ista.poo.farm.objects.estados.Estado;
import pt.iul.ista.poo.utils.Point2D;

public class Cabage extends Vegetable{

	public Cabage(Point2D p) {
		super(p);
	}
	public Cabage (Point2D p, Estado estado, int ciclo, boolean tratado){
		super(p);
		setEstado(estado);
		setCiclo(ciclo);
		setTratado(tratado);
	}

	@Override
	public void interact() {
		if(getEstado().equals(Estado.PLANTADO)){
			setTratado(true);
			addCiclo();
		}else if(getEstado().equals(Estado.MADURO) || getEstado().equals(Estado.ESTRAGADO)){
			colherVegetal(getPosition());
		}
		
	}
	
	@Override
	public String getName(){
		if(getEstado().equals(Estado.PLANTADO)){
			return "small_cabage";
		}if(getEstado().equals(Estado.MADURO)){
			return "cabage";
		}if(getEstado().equals(Estado.ESTRAGADO)){
			return "bad_cabage";
		}
		return "planted";
	}
	
	public void processaCiclo(){
		if(getEstado().equals(Estado.PLANTADO) && getCiclo() >= 10 && getCiclo() < 30){
			setEstado(Estado.MADURO); //Passa a maduro
		}else if(getEstado().equals(Estado.MADURO) && getCiclo() >= 30){
			setEstado(Estado.ESTRAGADO);
		}
	}
	
	public String toString(){
		return "Cabage;" + this.getPosition() + ";" + this.getEstado() + ";" + this.getCiclo() + ";" + this.getTratado();
	}

}
