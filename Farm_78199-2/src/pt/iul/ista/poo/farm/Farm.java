package pt.iul.ista.poo.farm;

import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;
import java.util.Scanner;

import pt.iul.ista.poo.farm.objects.Animal;
import pt.iul.ista.poo.farm.objects.Cabage;
import pt.iul.ista.poo.farm.objects.Chicken;
import pt.iul.ista.poo.farm.objects.FarmObject;
import pt.iul.ista.poo.farm.objects.Farmer;
import pt.iul.ista.poo.farm.objects.Interactable;
import pt.iul.ista.poo.farm.objects.Land;
import pt.iul.ista.poo.farm.objects.Sheep;
import pt.iul.ista.poo.farm.objects.Tomato;
import pt.iul.ista.poo.farm.objects.Updatable;
import pt.iul.ista.poo.farm.objects.Vegetable;
import pt.iul.ista.poo.farm.objects.estados.Estado;
import pt.iul.ista.poo.farm.objects.estados.EstadoChicken;
import pt.iul.ista.poo.farm.objects.estados.EstadoLand;
import pt.iul.ista.poo.farm.objects.estados.EstadoSheep;
import pt.iul.ista.poo.gui.ImageMatrixGUI;
import pt.iul.ista.poo.gui.ImageTile;
import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Point2D;

public class Farm implements Observer {

	// TODO
	private static final String SAVE_FNAME = "config/savedGame";
	private static final String DIMENSIONS_FNAME = "config/dimensions";
	
	private static final int MIN_X = 5;
	private static final int MIN_Y = 5;

	private static Farm INSTANCE;

	private int max_x; //tamanho do cenario atual x
	private int max_y;
	
	
	private List <FarmObject> tabuleiro; 
	private Farmer farmer;  
	private boolean space;   
	private int pontos; 
	private List <FarmObject> temporaria;
	
	
	private Farm() {
		readDimensions();
		
		if (max_x < 5 || max_y < 5)
			throw new IllegalArgumentException();
		
		INSTANCE = this;
		
		ImageMatrixGUI.setSize(max_x, max_y);

		// N�o usar ImageMatrixGUI antes de inicializar o tamanho		
		// TODO
		
		tabuleiro = new ArrayList <FarmObject>(); 
		temporaria = new ArrayList <FarmObject>(); 
		this.pontos = 0; 

		loadScenario();
	}
	
	public int getMax_x(){    
		return this.max_x;
	}
	public int getMax_y(){    
		return this.max_y;
	}
	public Farmer getFarmer(){
		return this.farmer;
	}
	public List<FarmObject> getTabuleiro(){
		return this.tabuleiro;
	}
	
//REMOCAO / ADICAO
	public void addToTemporaria(FarmObject f){
		temporaria.add(f);
	}
	
	public void removeTempFromTabuleiro(){
	
		for(FarmObject f: temporaria){
			if(tabuleiro.contains(f)){
				tabuleiro.remove(f);
			}else{
				tabuleiro.add(f);
			}
		}
		temporaria.clear();
	}

	private void registerAll() {
		// TODO
		List<ImageTile> images = new ArrayList<ImageTile>();

		// images.addAll(...);
		farmer = new Farmer(new Point2D(0,0));
		images.add(farmer);
		tabuleiro.add(farmer);
		
		for(int x=0; x<max_x; x++){
			for(int y=0; y<max_y; y++){
				Land l = new Land(new Point2D(x,y));
				images.add(l);
				tabuleiro.add(l);
			}
		}

		ImageMatrixGUI.getInstance().addImages(images);
		ImageMatrixGUI.getInstance().update();
		generateAnimals();
	}

	private Point2D gerarPonto(){
		Random r = new Random();
		Point2D p = new Point2D(r.nextInt(max_x),r.nextInt(max_y));
		while(animalInPos(p) != null || p.equals(farmer.getPosition())){
			p = new Point2D(r.nextInt(max_x),r.nextInt(max_y));
		}
		return p;
	}
	
	public Animal animalInPos(Point2D p){ //vai devolver o animal de uma det. posicao
		for(FarmObject f: tabuleiro){
			if(p.equals(f.getPosition()) && f instanceof Animal){
				return (Animal) f;
			}
		}
		return null;
	}
	private void generateAnimals(){
		Sheep s1 = new Sheep(gerarPonto(), false);
		tabuleiro.add(s1);
		Sheep s2 = new Sheep(gerarPonto(), false);
		tabuleiro.add(s2);
		Chicken c1 = new Chicken(gerarPonto(), EstadoChicken.CHICKEN);
		tabuleiro.add(c1);
		Chicken c2 = new Chicken(gerarPonto(), EstadoChicken.CHICKEN);
		tabuleiro.add(c2); 

		ImageMatrixGUI.getInstance().addImage(s1);
		ImageMatrixGUI.getInstance().addImage(s2);
		ImageMatrixGUI.getInstance().addImage(c1);
		ImageMatrixGUI.getInstance().addImage(c2);
		ImageMatrixGUI.getInstance().update();

	}

	
	private void loadScenario() {
		// TODO
		registerAll();
	}

	@Override
	public void update(Observable gui, Object a) { //N M
		System.out.println("Update sent " + a);
		// TODO
		if (a instanceof Integer) {
			int key = (Integer) a;
			if (Direction.isDirection(key)) {
				
				if(space ==false){
					farmer.move(Direction.directionFor(key));//
					
				}else{/**interacao com o objecto*/
					Interactable i = getInteractable(farmer.nextPosition(Direction.directionFor(key))); 
					if(i != null){																		
						i.interact();																	
					}
					space = false;
				}
				addCiclos(); //adiciona +1 ciclo a todos os elementos do jogo
				removeTempFromTabuleiro();
				
			}else if(key == KeyEvent.VK_SPACE){
				space = true;
			}else if(key == KeyEvent.VK_S){
				save();
			}else if(key == KeyEvent.VK_L){
				load();
			}
		}
		
		ImageMatrixGUI.getInstance().setStatusMessage("Points: " + pontos );
		ImageMatrixGUI.getInstance().update();
	}
	/**** Definir as prioridades das interacoes */
	public Interactable getInteractable(Point2D p){ // N M
		for(FarmObject f: tabuleiro){
			if(f.getPosition().equals(p) && f instanceof Interactable && f instanceof Animal){
				return (Interactable) f;
			}
		}
		for(FarmObject f: tabuleiro){
			if(f.getPosition().equals(p) && f instanceof Interactable && f instanceof Vegetable){
				return (Interactable) f;
			}
		}
		for(FarmObject f: tabuleiro){
			if(f.getPosition().equals(p) && f instanceof Interactable && f instanceof Land){
				return (Interactable) f;
			}
		}
		return null;
	}

	// N�o precisa de alterar nada a partir deste ponto
	private void play() {
		ImageMatrixGUI.getInstance().addObserver(this);
		ImageMatrixGUI.getInstance().go();
	}
	
	public static Farm getInstance() {
		assert (INSTANCE != null);
		return INSTANCE;
	}
	public static void main(String[] args) {
		Farm f = new Farm();
		f.play();
	}
	
	public void readDimensions(){
		try {
			Scanner fileScanner = new Scanner(new File(DIMENSIONS_FNAME));
			this.max_y = fileScanner.nextInt();
			this.max_x = fileScanner.nextInt();
			fileScanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	
	public List<FarmObject> elementosPos(Point2D p){ 
		List <FarmObject> objetos = new ArrayList <FarmObject> ();
		for(FarmObject f: tabuleiro){
			if(f.getPosition().equals(p)){
				objetos.add(f);
			}
		}
		return objetos; 
	}
	
	public void addPontos(int pontos){
		this.pontos += pontos;
	}
	
	public void addCiclos(){
		for(FarmObject f: tabuleiro){
			if(f instanceof Updatable){
				((Updatable)f).addCiclo();
				((Updatable)f).processaCiclo();
			}
		}
	}
	
	public void save(){
		try {
			PrintWriter fileWriter = new PrintWriter(new File(SAVE_FNAME));
			fileWriter.println(max_x + " " + max_y);
			fileWriter.println(this.pontos);
			for(FarmObject f: tabuleiro){
				fileWriter.println(f);
			}
			fileWriter.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void load(){
		try {
			List<ImageTile> images = new ArrayList<ImageTile>();
			tabuleiro.clear();
			ImageMatrixGUI.getInstance().clearImages();
			Scanner s = new Scanner(new File(SAVE_FNAME));
			this.max_x = s.nextInt();
			this.max_y = s.nextInt();
			this.pontos = s.nextInt();
			String linha;
			FarmObject f;
			while(s.hasNextLine()){
				linha = s.nextLine();
				f = objetos(linha.split(";"));
				if(f != null){
					tabuleiro.add(f);
					images.add(f);
				}
			}
			s.close();
			ImageMatrixGUI.getInstance().addImages(images);
			ImageMatrixGUI.getInstance().update();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	public FarmObject objetos(String [] l){
		switch(l[0]){
		case "Land":
			return new Land (Point2D.readFrom(new Scanner(l[1])), EstadoLand.valueOf(l[2].toUpperCase()));
		case "Tomato":
			return new Tomato (Point2D.readFrom(new Scanner(l[1])), Estado.valueOf(l[2].toUpperCase()), Integer.valueOf(l[3]), Boolean.valueOf(l[4]));
		case "Cabage":
			return new Cabage (Point2D.readFrom(new Scanner(l[1])), Estado.valueOf(l[2].toUpperCase()), Integer.valueOf(l[3]), Boolean.valueOf(l[4]));
		case "Sheep":
			return new Sheep (Point2D.readFrom(new Scanner(l[1])), Boolean.valueOf(l[2]), EstadoSheep.valueOf(l[3].toUpperCase()), Integer.valueOf(l[4]));
		case "Chicken":
			return new Chicken (Point2D.readFrom(new Scanner(l[1])), EstadoChicken.valueOf(l[2].toUpperCase()), Integer.valueOf(l[3]));
		case "Farmer":
			farmer = new Farmer (Point2D.readFrom(new Scanner(l[1])));
			return farmer;
		default: return null;
		}
	}
}
