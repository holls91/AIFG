package main;

import java.util.Optional;
import java.util.Random;

import util.AIFG_Util;
import movement.dynamics.Arrive;
import movement.dynamics.Face;
import movement.dynamics.Pursue;
import movement.dynamics.Reach;
import movement.dynamics.Seek;
import movement.dynamics.SteeringOutput;
import movement.dynamics.Wander;
import movement.dynamics.Wander2;
import movement.kinematics.Kinematic;
import movement.vectors.Vector;

public class Main_AIFG_Reach {

	public static void main(String[] args) {
		
		Kinematic character = new Kinematic(new Vector(1.0, 1.0), 1.0, new Vector(0.4,0.5), 1.0);
		Kinematic target = new Kinematic(new Vector(10.0, 10.0), 1.0, new Vector(0.2,0.2), 1.0);
		
		Seek seek = new Seek(character, target, 0.01);
		Arrive arrive = new Arrive(character, target, 0.10, 0.40, 0.01, 0.75, 0.03);
		
		Reach reach = new Reach(character, target, 0.25, 0.50, 0.01, 0.2, 0.1, 1);
		
//		Pursue pursue = new Pursue(character, target, 0.25);
//		pursue.setMaxPrediction(1);
		
		Wander2 wander = new Wander2(character, target, 0.10, 0.50, 0.01, 0.2, 0.1, 1.0 , 2.0, 0.5, 0.5, 0.25);
		
		Face face = new Face(character, target, 0.10, 0.50, 0.01, 0.2, 0.1);
//		wander.setVolatility(0.1);
//		wander.setTurnSpeed(1);
		
//		Random random = new Random(wander.getTurnSpeed());
//		AIFG_Util.createRandom(random);
		
		for(int i=0; i<50; i++){
			Optional<SteeringOutput> steering = reach.getSteering();
			Optional<SteeringOutput> steeringT = wander.getSteering();
			if(steering.isPresent()){
				character.update(steering.get(), 1);
				target.update(steeringT.get(), 1);
				System.out.println("Character: "+character.getPosition()+" - Velocity: "+character.getVelocity());
				System.out.println("Target: "+target.getPosition()+" - Velocity: "+target.getVelocity());				
			}
			else{
				System.out.println("Target raggiunto!");
				break;
			}
		}	
	}

}
