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
public class Reach extends Arrive {

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
	private Kinematic reachTarget;
	
	public Reach(Kinematic character, Kinematic target, double maxAcceleration, double maxSpeed, double targetRadius, double slowRadius, double timeToTarget, double maxPrediction) {
		super(character,target,maxAcceleration, maxSpeed, targetRadius, slowRadius, timeToTarget);
		this.maxPrediction = maxPrediction;
		this.reachTarget = target;
	}
	
	public Optional<SteeringOutput> getSteering() {
//		1. Calculate the target to delegate to arrive
//		Work out the distance to target
		Vector direction = reachTarget.getPosition().subtract(getCharacter().getPosition());
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
//		setTarget(explicitTarget);	// <-- explicitTarget = pursueTarget ?
		Vector position = getTarget().getPosition();
		position = position.add(reachTarget.getVelocity().multiply(prediction));
		getTarget().setPosition(position);
		
//		2. Delegate to arrive
		return super.getSteering();
	}

}
