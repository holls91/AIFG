package main;

import java.util.Optional;

import path.Path;

import movement.kinematics.Kinematic;
import movement.pathFollowing.FollowPathFirst;
import movement.vectors.Vector;

import movement.dynamics.Seek;
import movement.dynamics.SteeringOutput;

public class Main_AIFG_Path {

	public static void main(String[] args) {
		Kinematic character = new Kinematic(new Vector(0.0, 0.0), 3.0, new Vector(0.2,0.2), 1.0);
		Kinematic target = new Kinematic(new Vector(0.0, 0.0), 0.0, new Vector(0.0,0.0), 0.0);
		
		Seek seek = new Seek(character, target, 0.15);
		
		Path path = new Path();
		path.addNode(new Vector(0.0, 0.0));
		path.addNode(new Vector(1.0, 2.0));
		path.addNode(new Vector(10.0, 10.0));
		path.addNode(new Vector(17.0, 21.0));
		
		FollowPathFirst fpf = new FollowPathFirst(seek, path, 1, 1);
		
		for(int i=0; i<100; i++){
			Optional<SteeringOutput> steering = fpf.getSteering();
			if(steering.isPresent()){
				character.update(steering.get(), 0.7, 1.3);
				System.out.println("Character: "+character.getPosition()+" - Velocity: "+character.getVelocity());
				System.out.println("Target: "+fpf.getPath().getPosition(fpf.getCurrentParam()));	
			}
			else{
				System.out.println("Target raggiunto!");
				break;
			}
				
		}

	}

}
