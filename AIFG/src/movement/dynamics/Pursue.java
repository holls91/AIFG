package movement.dynamics;

import java.util.Optional;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.ExtensionMethod;
import movement.kinematics.Kinematic;
import movement.vectors.Vector;
import util.AIFG_Util;

@Data
@EqualsAndHashCode(callSuper=true)
@ExtensionMethod({AIFG_Util.class})
public class Pursue extends Seek {

//	Holds the maximum prediction time
	private double maxPrediction;
	/*
	 * OVERRIDES the target data in seek (in other words
 	 * this class has two bits of data called target:
 	 * Seek.target is the superclass target which
 	 * will be automatically calculated and shouldn’t
 	 * be set, and Pursue.target is the target we’re
 	 * pursuing).
	 */
	private Kinematic pursueTarget;
	
	public Pursue(Kinematic character, Kinematic target, double maxAcceleration, double maxPrediction) {
		super(character,target,maxAcceleration);
		this.maxPrediction = maxPrediction;
		this.pursueTarget = target;
	}
	
	public Optional<SteeringOutput> getSteering() {
//		1. Calculate the target to delegate to seek
//		Work out the distance to target
		Vector direction = pursueTarget.getPosition().subtract(getCharacter().getPosition());
		double distance = direction.length();
		
//		Work out our current speed
		double speed = getCharacter().getVelocity().length();
		
//		Check if speed is too small to give a reasonable prediction time
		double prediction;
		if(speed<=distance/maxPrediction)
			prediction = maxPrediction;
//		Otherwise calculate the prediction time
		else
			prediction = distance/speed;
		
//		Put the target together
//		Position sarebbe la posizione dell'explicitTarget
		Vector position = getTarget().getPosition();
		position = position.add(pursueTarget.getVelocity().multiply(prediction));
		getTarget().setPosition(position);
		
//		2. Delegate to seek
		return super.getSteering();
	}

}
