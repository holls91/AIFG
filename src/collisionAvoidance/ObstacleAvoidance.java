package collisionAvoidance;

import java.util.Optional;

import javafx.scene.shape.Circle;
import util.AIFG_Util;
import lombok.experimental.ExtensionMethod;

import movement.kinematics.Kinematic;
import movement.vectors.Vector;

import movement.dynamics.Seek;
import movement.dynamics.SteeringOutput;

@ExtensionMethod({ AIFG_Util.class })
public class ObstacleAvoidance extends Seek {
	
	// The spherical obstacle we're avoiding.
	Circle obstacle;
	
	// By how much do we want to avoid the collision?
	Double avoidMargin;
	
	// How far ahead do we want to look for collisions?
	Double maxLookahead;
	
	public ObstacleAvoidance(Kinematic character, Kinematic target, double maxAcceleration, Circle obstacle, Double avoidMargin, Double maxLookahead) {
		super(character, target, maxAcceleration);
		this.obstacle = obstacle;
		this.avoidMargin = avoidMargin;
		this.maxLookahead = maxLookahead;
	}
	
	@Override
	public Optional<SteeringOutput> getSteering() {
		
		Vector circlePosition = new Vector(obstacle.getCenterX(), obstacle.getCenterY());
		
		// Make sure we're moving
		if(getCharacter().getVelocity().multiplyDot(getCharacter().getVelocity()) > 0){
			
			// Find the distance from the line we're moving along to the obstacle.
			Vector movementNormal = getCharacter().getVelocity().normalize();
			Vector characterToObstacle = circlePosition.subtract(getCharacter().getPosition());
			
			Double distanceSquared = movementNormal.multiplyDot(characterToObstacle);
			distanceSquared = characterToObstacle.squareMagnitude() - (distanceSquared*distanceSquared);
			
			// Check for collision
			Double radius = obstacle.getRadius() + avoidMargin;
			
			if(distanceSquared < radius*radius){
				
				// Find how far along our movement vector the closest pass is
				Double distanceToClosest = movementNormal.multiplyDot(characterToObstacle);
				
				// Make sure this isn't behind us and is closer than our lookahead.
				if(distanceToClosest > 0 && distanceToClosest < maxLookahead){
					
					// Find the closest point
					Vector closestPoint = getCharacter().getPosition().add(movementNormal.multiply(distanceToClosest));
					
					// Find the point of avoidance
					Vector target1 = closestPoint.subtract(circlePosition).normalize();
					Vector secondMember = target1.multiply(obstacle.getRadius()+avoidMargin);
					
					getTarget().setPosition(circlePosition.add(secondMember));	
					
					// Seek this point
					return super.getSteering();
				}
			}
		}
		
		return super.getSteering();
	
	}

}
