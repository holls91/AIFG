package gui.keyboard;

import gui.GUI_Manager;
import gui.util.DrawListener;

import java.awt.event.KeyEvent;

import util.AIFG_Util;

import movement.IDynamicMovement;
import movement.kinematics.Kinematic;
import movement.vectors.Vector;

import movement.dynamics.Arrive;
import movement.dynamics.Face;

public class Main_AIFG_Rincorsa implements DrawListener {
	
	private Kinematic target;
	private GUI_Manager gm;
	
	public Main_AIFG_Rincorsa() {
		Kinematic character = new Kinematic(new Vector(0.0, 0.0), 1.0, new Vector(0.0,0.0), 1.0);
		Kinematic charactarget = new Kinematic(new Vector(15.0, 5.0), 0.0, new Vector(0.0,0.0), 0.0);
		target = new Kinematic(new Vector(15.0, 5.0), 0.0, new Vector(0.0,0.0), 0.0);
		
		IDynamicMovement arrive = new Arrive(character, charactarget, 0.30, 0.20, 0.01, 0.75, 0.03);
		IDynamicMovement arriveInvisibile = new Arrive(charactarget, target, 0.30, 0.40, 0.01, 0.75, 0.03);
		IDynamicMovement face = new Face(character, charactarget, 0.30, 0.20, 0.01, 0.75, 0.03);
		
		gm = new GUI_Manager(character,arrive);
		gm.add(charactarget,arriveInvisibile);
		gm.setCanvasSize(512,512);
		gm.setXscale(-5,25);
		gm.setYscale(-5,25);
		gm.setRefresh(50);
		gm.setTarget(target);
		gm.addListener(this);
	}
	public void run() {
		gm.run();
	}
	public void run(long ms) {
		gm.run(ms);
	}
	
	private long lastPressProcessed = 0;
	
	@Override
	public void keyPressed(int key) {
		if(System.currentTimeMillis()-lastPressProcessed > 200) {
    		switch(key) {
    			case KeyEvent.VK_UP:
    				target.setPosition(AIFG_Util.add(target.getPosition(), new Vector(0,1)));
    				break;
    			case KeyEvent.VK_DOWN:
    				target.setPosition(AIFG_Util.add(target.getPosition(), new Vector(0,-1)));
    				break;
    			case KeyEvent.VK_LEFT:
        			target.setPosition(AIFG_Util.add(target.getPosition(), new Vector(-1,0)));
        			break;
    			case KeyEvent.VK_RIGHT:
        			target.setPosition(AIFG_Util.add(target.getPosition(), new Vector(1,0)));
        			break;
    			default:
    				System.out.println("puzzi, premi una freccia!");
    		}
    		lastPressProcessed = System.currentTimeMillis();
		}
	}
	
	
	public static void main(String[] args) {
		new Main_AIFG_Rincorsa().run();
	}

}
