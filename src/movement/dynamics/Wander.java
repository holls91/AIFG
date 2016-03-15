package movement.dynamics;

import java.util.Optional;
import lombok.Data;
import lombok.EqualsAndHashCode;
import movement.kinematics.Kinematic;
import movement.vectors.Vector;
import static util.AIFG_Util.*;

@Data
@EqualsAndHashCode(callSuper=true)
public class Wander extends Seek {
	
	/*
	 * This controls the degree to which the character moves in a 
	 * straight line. Specifically it controls how far ahead to aim
	 * when the character is in motion. Too small values means the 
	 * character may be able to overshoot their target, and so will
	 * oscillate wildly.
	 */
	double volatility;
	
	/*
	 * This controls how fast the character may turn.
	 */
	long turnSpeed;	
	
	public Wander(Kinematic character, Kinematic target, double maxAngularAcceleration){
		super(character, target, maxAngularAcceleration);
	}

	public Optional<SteeringOutput> getSteering() {
		
		// Make sure we have a target
		if(length(getTarget().getPosition()) == 0){
			Vector position = getCharacter().getPosition();
			getTarget().setPosition(position);
			getTarget().getPosition().setX(position.getDoubleX()+volatility);
		}
		
		Vector offset = subtract(getTarget().getPosition(),getCharacter().getPosition());
		double angle;
		
		if(Math.pow(offset.getDoubleX(), 2)+Math.pow(offset.getDoubleZ(),2) > 0){
			// Work out the angle to the target from the character
			angle = Math.atan2(offset.getDoubleZ(), offset.getDoubleX());
		}
		else
			angle = 0;
		
		// Move the target to the boundary of the volatility circle and add the turn to the target
		Vector targetPosition = getTarget().getPosition();
		targetPosition.setX((targetPosition.getDoubleX()+volatility*Math.cos(angle))+randomBinomial());
		targetPosition.setZ((targetPosition.getDoubleZ()+volatility*Math.cos(angle))+randomBinomial());
		
		return super.getSteering(); 	
	}
}
