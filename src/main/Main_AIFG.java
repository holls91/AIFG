package main;

import java.util.Optional;

import movement.dynamics.Arrive;
import movement.dynamics.Seek;
import movement.dynamics.SteeringOutput;
import movement.kinematics.Kinematic;
import movement.vectors.Vector;

public class Main_AIFG {

	public static void main(String[] args) {
		
		Kinematic character = new Kinematic(new Vector(1.0, 1.0), 0.0, new Vector(0.0,0.25), 0.0);
		Kinematic target = new Kinematic(new Vector(1.0, 2.0), 0.0, new Vector(0.0,0.0), 0.0);
		
		Seek seek = new Seek(character, target, 0.10);
		Arrive arrive = new Arrive(character, target, 0.1, 0.1, 0.1, 0.5, 0.1);
		
		for(int i=0; i<50; i++){
			Optional<SteeringOutput> steering = arrive.getSteering();
			if(steering.isPresent()){
				character.update(steering.get(), 1);
				System.out.println("Character: "+character.getPosition()+" - Velocity: "+character.getVelocity());
				System.out.println("Target: "+target.getPosition());
			}
			else{
				System.out.println("Target raggiunto!");
				break;
			}
		}
	}

}
