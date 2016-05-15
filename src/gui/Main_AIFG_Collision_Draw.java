package gui;

import gui.util.Draw;
import gui.util.DrawListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import movement.dynamics.SteeringOutput;
import movement.kinematics.Kinematic;
import movement.vectors.Vector;
import collisionAvoidance.CollisionAvoidance;

public class Main_AIFG_Collision_Draw implements DrawListener {

	private static int SIZE = 512;

    private Draw draw = new Draw();

    public Main_AIFG_Collision_Draw() {
        draw.setCanvasSize(SIZE, SIZE);
        draw.setXscale(-5, 25);
        draw.setYscale(-5, 25);
        draw.addListener(this);
        draw.show(20);
    }
    
    public void run() {
        Kinematic character = new Kinematic(new Vector(0.0, 0.0), 3.0, new Vector(0.2,0.2), 1.0);
        
        List<Kinematic> targets = new ArrayList<Kinematic>() {{
        	//add(new Kinematic(new Vector(5, 0.0), 0.0, new Vector(0.0,0.0), 0.0));
        	add(new Kinematic(new Vector(0, 10), 0.0, new Vector(0.0,0.0), 0.0));
        	add(new Kinematic(new Vector(5,5), 0.0, new Vector(0.0,0.0), 0.0));
        	add(new Kinematic(new Vector(7,17), 0.0, new Vector(0.0,0.0), 0.0));
        	add(new Kinematic(new Vector(20,6), 0.0, new Vector(0.0,0.0), 0.0));
        }};
    	
        CollisionAvoidance ca = new CollisionAvoidance(character,0.15, targets,2.5);
    	
    	draw.setPenColor(Draw.RED);
    	for(Kinematic k: targets) {
    		draw.filledCircle(k.getPosition().getDoubleX(),k.getPosition().getDoubleZ(),0.5);
    	}
    	
    	for(int i=0; i>=0; i++){
    		Optional<SteeringOutput> steering = ca.getSteering();
//    		if(steering.isPresent()){
    			character.update(steering.orElse(new SteeringOutput(new Vector(Math.random()+1,Math.random()+1), 0)), 0.7, .65);
    			draw.setPenColor(Draw.GREEN);
    			draw.filledCircle(character.getPosition().getDoubleX(),character.getPosition().getDoubleZ(),1);
    			System.out.println("Character: "+character.getPosition()+" - Velocity: "+character.getVelocity());
//    			System.out.println("Target: "+ca.getPath().getPosition(ca.getCurrentParam()));	
//    		}
    		draw.show(60);
    		draw.setPenColor(Draw.WHITE);
    		draw.filledCircle(character.getPosition().getDoubleX(),character.getPosition().getDoubleZ(),1.1);
    		draw.setPenColor(Draw.RED);
        	for(Kinematic k: targets) {
        		draw.filledCircle(k.getPosition().getDoubleX(),k.getPosition().getDoubleZ(),0.5);
        	}
    	}
    
    }
    
	public static void main(String[] args) {
		Main_AIFG_Collision_Draw pippo = new Main_AIFG_Collision_Draw();
		pippo.run();
	}

}
