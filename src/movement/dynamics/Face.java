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
public class Face extends Align {

	private Kinematic faceTarget;
	
	public Face(Kinematic character, Kinematic target, Double maxAngularAcceleration, Double maxRotation, Double targetRadius, Double slowRadius, Double timeToTarget){
		super(character, target, maxAngularAcceleration, maxRotation, targetRadius, slowRadius, timeToTarget);
		this.faceTarget = target;
	}
	
	public Optional<SteeringOutput> getSteering() {
		//1. Calculate the target to delegate to align
		
		//Work out the direction to target
		Vector direction = faceTarget.getPosition().subtract(getCharacter().getPosition());
		
		//Put the target together
		if(direction.length() != 0){
			Double orientation = getTarget().getOrientation().getNewOrientation(getTarget().getVelocity());
			getTarget().setOrientation(orientation);
		}
		
		//Delegate to Align
		return super.getSteering();
	}
}
