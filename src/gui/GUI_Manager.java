package gui;

import gui.util.Draw;

import java.awt.Color;
import java.util.Optional;

import movement.IDynamicMovement;
import movement.dynamics.SteeringOutput;
import movement.kinematics.Kinematic;
import movement.vectors.Vector;

public class GUI_Manager {

	private Kinematic character;
	private IDynamicMovement movement;
	private Kinematic target;
	
	private Draw draw;
	
	private long endOfExecution;
	
	private Object[] extraDraw;
	
	
	public GUI_Manager(){;
		init();
	}
	public GUI_Manager(Kinematic character, IDynamicMovement movement, Kinematic target) {
		this.character = character;
		this.movement = movement;
		this.target = target;
		init();
	}
	private void init() {
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

	public Kinematic setCharacter(Kinematic character) {
		Kinematic old = this.character;
		this.character = character;
		return old;
	}
	public IDynamicMovement setMovement(IDynamicMovement movement) {
		IDynamicMovement old = this.movement;
		this.movement = movement;
		return old;
	}
	public Kinematic setTarget(Kinematic target) {
		Kinematic old = this.target;
		this.target = target;
		return old;
	}
	
	public void setExtraObject(Object... extraDraw) {
		this.extraDraw = extraDraw;
	}
	
	public void run() {
		do {
			draw.clear();
			drawTarget();
    		drawExtra();
    		Optional<SteeringOutput> steering = movement.getSteering();
    		if(steering.isPresent()) {
    			character.update(steering.get(), 0.7, 0.65);
    			drawCharacter();
    		}
    		else {
    			character.update(new SteeringOutput(new Vector(Math.random()-1,Math.random()-1),Math.random()-1),0.7,0.65);
    		}
    		draw.show(25);
		} while(System.currentTimeMillis()<endOfExecution);
	}
	public void run(long ms) {
		endOfExecution = System.currentTimeMillis()+ms;
		run();
	}

	private void drawCharacter() {
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

	public void drawExtra() {
		for(Object obj : extraDraw) {
			if(obj instanceof Kinematic)
				draw((Kinematic)obj,Draw.BLUE,0.5);
			if(obj instanceof Vector)
				draw((Vector)obj,Draw.BLACK,0.5);
			else continue;
		}
	}
	
}
