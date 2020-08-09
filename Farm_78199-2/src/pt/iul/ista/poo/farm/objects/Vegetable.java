package pt.iul.ista.poo.farm.objects;

import java.util.Iterator;
import java.util.List;

import pt.iul.ista.poo.farm.Farm;
import pt.iul.ista.poo.farm.objects.estados.Estado;
import pt.iul.ista.poo.farm.objects.estados.EstadoLand;
import pt.iul.ista.poo.gui.ImageMatrixGUI;
import pt.iul.ista.poo.utils.Point2D;

public abstract class Vegetable extends FarmObject implements Updatable, Interactable{
	private Estado estado;
	private boolean tratado; 
	private int ciclo;

	public Vegetable(Point2D p) {
		super(p);
		this.estado = Estado.PLANTADO;
		this.tratado = false;
		this.ciclo = 0;
			}

	@Override
	public void interact() {
	}
	
	public int getLayer(){
		return 2;
	}
	
	public void setEstado(Estado e){
		estado = e;
	}
	
	public Estado getEstado(){
		return this.estado;
	}
	
	public void setTratado(boolean b){ 
		this.tratado = b;
	}
	
	public boolean getTratado(){
		return this.tratado;
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
	
	public void colherVegetal(Point2D p){
		Iterator <FarmObject> it = Farm.getInstance().getTabuleiro().iterator();
		while(it.hasNext()){
			FarmObject f = it.next();  //f é o vegetal que vamos colher
			
			if (f.getPosition().equals(p) && f instanceof Vegetable){ 
				if(((Vegetable)f).getEstado().equals(Estado.MADURO) || ((Vegetable)f).getEstado().equals(Estado.ESTRAGADO)){
					it.remove();
					ImageMatrixGUI.getInstance().removeImage(f);
					ImageMatrixGUI.getInstance().update();
					List<FarmObject> land = Farm.getInstance().elementosPos(p);
					((Land)land.get(0)).setEstadoLand(EstadoLand.LAND);  
					
					if(f instanceof Tomato && ((Vegetable)f).getEstado().equals(Estado.MADURO)){
						Farm.getInstance().addPontos(3);
					}else if(((Vegetable)f).getEstado().equals(Estado.MADURO)){
						Farm.getInstance().addPontos(2);

					}
				}
			}
		}
	}

}
