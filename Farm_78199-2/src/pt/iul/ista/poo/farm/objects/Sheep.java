package pt.iul.ista.poo.farm.objects;

import pt.iul.ista.poo.farm.Farm;
import pt.iul.ista.poo.farm.objects.estados.EstadoSheep;
import pt.iul.ista.poo.utils.Point2D;

public class Sheep extends Animal{
	private boolean alimentada;
	private EstadoSheep estado;

	public Sheep(Point2D p, boolean alimentada) {
		super(p);
		this.alimentada = alimentada;
		this.estado = EstadoSheep.PARADA;
	}
	
	public Sheep(Point2D p, boolean alimentada, EstadoSheep estado, int ciclo){
		super(p);
		this.alimentada = alimentada;
		setCiclo(ciclo);
		setEstadoSheep(estado);
	}

	public void processaCiclo(){
		if(getAlimentada() == true){
			Farm.getInstance().addPontos(1);
		}if(getCiclo()==10 && getAlimentada() == true){
			setAlimentada(false);
		}if(getCiclo() > 10 && getCiclo() <= 20 && getAlimentada() == false){
			move();     
		}else if(getCiclo() > 20){
			setEstadoSheep(EstadoSheep.FAMINTA); 
			alimentada = false;
			
		}
	}

	public void setEstadoSheep(EstadoSheep estado){
		this.estado = estado;
	}

	public EstadoSheep getEstadoSheep(){
		return this.estado;
	}
	public boolean getAlimentada(){
		return this.alimentada;
	}
	
	public void setAlimentada(boolean b){
		this.alimentada = b;
	}
	@Override
	public void interact() {
		setAlimentada(true);;
		setEstadoSheep(EstadoSheep.PARADA);
		setCiclo(0);
		System.out.println("ovelha alimentada!");
	}
	
	@Override
	public String getName(){
		if(getEstadoSheep().equals(EstadoSheep.PARADA) || getEstadoSheep().equals(EstadoSheep.MOVER)){
			return "sheep";
		}else if(getEstadoSheep().equals(EstadoSheep.FAMINTA)){
			return "famished_sheep";
		}
		return "sheep";
	}
	
	
	
	public String toString(){
		return "Sheep;" + this.getPosition() + ";" + this.getAlimentada() + ";" + this.getEstadoSheep() + ";" + this.getCiclo();
	}
	
	
	

}
