package gui;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import movement.combined.BlendedSteering;
import movement.combined.BlendedSteering.BehaviorAndWeight;
import movement.kinematics.Kinematic;
import movement.vectors.Vector;

import movement.dynamics.Arrive;
import movement.dynamics.SteeringOutput;

import collisionAvoidance.CollisionAvoidance;
import gui.util.Draw;
import gui.util.DrawListener;

public class Main_AIFG_Blended_Draw implements DrawListener {
	private static int SIZE = 512;

    private Draw draw = new Draw();

    public Main_AIFG_Blended_Draw() {
        draw.setCanvasSize(SIZE, SIZE);
        draw.setXscale(-5, 25);
        draw.setYscale(-5, 25);
        draw.addListener(this);
        draw.show(20);
    }
    
    public void run() {
    	
        Kinematic character = new Kinematic(new Vector(0.0, 0.0), 0.0, new Vector(0.2,0.2), 0.0);
    	Kinematic target = new Kinematic(new Vector(10.0, 10.0), 0.0, new Vector(0.0,0.0), 0.0);
    	
        List<Kinematic> obstacles = new ArrayList<Kinematic>() {{
        	//add(new Kinematic(new Vector(5, 0.0), 0.0, new Vector(0.0,0.0), 0.0));
        	add(new Kinematic(new Vector(0, 10), 0.0, new Vector(0.3,0.3), 0.0));
        	add(new Kinematic(new Vector(5,5), 0.0, new Vector(0.3,0.3), 0.0));
        	add(new Kinematic(new Vector(7,17), 0.0, new Vector(0.3,0.3), 0.0));
        	add(new Kinematic(new Vector(20,6), 0.0, new Vector(0.3,0.3), 0.0));
        }};
    	
    	Arrive arrive = new Arrive(character, target, 0.15, 0.25, 1, 1, 0.7);
    	CollisionAvoidance ca = new CollisionAvoidance(character,0.15,obstacles,1.0);
    	
    	
    	BehaviorAndWeight baw1 = new BehaviorAndWeight(arrive,2);
    	BehaviorAndWeight baw2 = new BehaviorAndWeight(ca, 1);
    	BehaviorAndWeight[] behaviors = new BehaviorAndWeight[]{baw1,baw2};
    	
    	
    	BlendedSteering blended = new BlendedSteering(behaviors, 0.25, 0.25);
    	
    	draw.setPenColor(Draw.BLACK);
    	for(Kinematic c: obstacles) {
    		draw.filledCircle(c.getPosition().getDoubleX(),c.getPosition().getDoubleZ(),0.5);
    	}
    	
    	draw.setPenColor(Draw.RED);
		draw.filledCircle(target.getPosition().getDoubleX(),target.getPosition().getDoubleZ(),0.5);
    	
    	
    	draw.setPenColor(Draw.GREEN);
    	for(int i=0; i>=0; i++){
    		Optional<SteeringOutput> steering = blended.getSteering();
//    		if(steering.isPresent() && steering.get().getLinear() != null){
    			character.update(steering.orElse(new SteeringOutput(new Vector(Math.random(),Math.random()), 0)), 0.35, .65);
    			draw.setPenColor(Draw.GREEN);
    			draw.filledCircle(character.getPosition().getDoubleX(),character.getPosition().getDoubleZ(),1);
//    			System.out.println("Character: "+character.getPosition()+" - Velocity: "+character.getVelocity());
//    			System.out.println("Target: "+fpf.getPath().getPosition(fpf.getCurrentParam()));	
//    		}
//    		else{
////    			System.out.println("Target raggiunto!");
//    			break;
//    		}
    		draw.show(25);
    		draw.setPenColor(Draw.WHITE);
    		draw.filledCircle(character.getPosition().getDoubleX(),character.getPosition().getDoubleZ(),1.1);
    		draw.setPenColor(Draw.BLACK);
        	for(Kinematic c: obstacles) {
        		draw.filledCircle(c.getPosition().getDoubleX(),c.getPosition().getDoubleZ(),0.5);
        	}
        	draw.setPenColor(Draw.RED);
    		draw.filledCircle(target.getPosition().getDoubleX(),target.getPosition().getDoubleZ(),0.5);
    	}
    
    }
    
	public static void main(String[] args) {
		Main_AIFG_Blended_Draw pippo = new Main_AIFG_Blended_Draw();
		pippo.run();
	}
}
