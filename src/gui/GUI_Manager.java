package gui;

import gui.util.Draw;
import gui.util.DrawListener;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import movement.IDynamicMovement;
import movement.dynamics.SteeringOutput;
import movement.kinematics.Kinematic;
import movement.vectors.Vector;

public class GUI_Manager {

	private Map<Kinematic, IDynamicMovement> map;
	
	private Kinematic character;
	private IDynamicMovement movement;
	private Kinematic target;
	
	private Draw draw;
	private double timeUpdate = 0.65;
	private double maxSpeedCharacter = 0.5;
	private int refresh = 25;
	
	private long endOfExecution;
	
	private Object[] extraDraw;
	
	
	public GUI_Manager(){
		init();
	}
	public GUI_Manager(Kinematic character, IDynamicMovement movement) {
//		this.character = character;
//		this.movement = movement;
		init();
		map.put(character,movement);
	}
	public GUI_Manager(Kinematic character, IDynamicMovement movement, Kinematic target) {
//		this.character = character;
//		this.movement = movement;
//		this.target = target;
		init();
		map.put(character,movement);
		setTarget(target);
	}
	private void init() {
		map = new HashMap<>();
		draw = new Draw();
		draw.xorOn();
		draw.setCanvasSize(512,512);
		draw.setXscale(0,512);
		draw.setYscale(0,512);
		endOfExecution = Long.MAX_VALUE;
		setExtraObject();
	}

	public void setCanvasSize(int w, int h) {
		draw.setCanvasSize(w,h);
	}
	public void setScales(double xMin, double xMax, double yMin, double yMax) {
		setXscale(xMin,xMax);
		setYscale(yMin,yMax);
	}
	public void setXscale(double min, double max) {
		draw.setXscale(min,max);
	}
	public void setYscale(double min, double max) {
		draw.setYscale(min,max);
	}

//	public Kinematic setCharacter(Kinematic character) {
//		Kinematic old = this.character;
//		this.character = character;
//		return old;
//	}
//	public IDynamicMovement setMovement(IDynamicMovement movement) {
//		IDynamicMovement old = this.movement;
//		this.movement = movement;
//		return old;
//	}
	public void add(Kinematic character, IDynamicMovement movement) {
		map.put(character,movement);
	}
	public Kinematic setTarget(Kinematic target) {
		Kinematic old = this.target;
		this.target = target;
		return old;
	}
	
	public void addListener(DrawListener listener) {
		draw.addListener(listener);
	}
	
	public void setTimeUpdate(double time) {
		this.timeUpdate = time;
	}
	public void setMaxSpeedCharacter(double maxSpeed) {
		this.maxSpeedCharacter = maxSpeed;
	}
	
	public void setRefresh(int refreshMs) {
		this.refresh = refreshMs;
	}
	public void setExtraObject(Object... extraDraw) {
		this.extraDraw = extraDraw;
	}
	
	public void run() {
		do {
			draw.clear();
			drawTarget();
    		drawExtra();
    		for(Entry<Kinematic,IDynamicMovement> e : map.entrySet()) {
        		Optional<SteeringOutput> steering = e.getValue().getSteering();
        		if(steering.isPresent()) {
        			e.getKey().update(steering.get(), maxSpeedCharacter, timeUpdate);
        		}
        		else {
    //    			System.out.println("No steering found");
    //    			character.update(new SteeringOutput(new Vector(Math.random()-1,Math.random()-1),Math.random()-1),0.7,0.65);
        		}
        		drawCharacter(e.getKey());
    		}
    		draw.show(refresh);
		} while(System.currentTimeMillis()<endOfExecution);
	}
	public void run(long ms) {
		endOfExecution = System.currentTimeMillis()+ms;
		run();
	}

	private void drawCharacter(Kinematic character) {
//System.out.println("Disegno Character");
		draw(character,Draw.GREEN,1);
	}
	private void drawTarget() {
		if(target!=null) {
//System.out.println("Disegno Target");
			draw(target,Draw.MAGENTA,0.7);
		} else {
//System.out.println("Target non settato");			
		}
	}
	private void draw(Kinematic k, Color c, double size) {
		draw.setPenColor(c);
		draw.filledCircle(k.getPosition().getDoubleX(),k.getPosition().getDoubleZ(),size);		
	}
	private void draw(Vector v, Color c, double size) {
//System.out.println("Disegno Vector");
		draw.setPenColor(c);
		draw.filledCircle(v.getDoubleX(),v.getDoubleZ(),size);
	}

	private void drawExtra() {
		for(Object obj : extraDraw) {
			if(obj instanceof Kinematic)
				draw((Kinematic)obj,Draw.BLUE,0.5);
			if(obj instanceof Vector)
				draw((Vector)obj,Draw.BLACK,0.5);
			else continue;
		}
	}
	
}
