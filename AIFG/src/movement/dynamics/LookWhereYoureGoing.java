package movement.dynamics;

import java.util.Optional;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.ExtensionMethod;
import movement.kinematics.Kinematic;
import util.AIFG_Util;

@Data
@EqualsAndHashCode(callSuper=true)
@ExtensionMethod({AIFG_Util.class})
public class LookWhereYoureGoing extends Align {

	public LookWhereYoureGoing(Kinematic character, Kinematic target, double maxAngularAcceleration, double maxRotation, double targetRadius, double slowRadius, double timeToTarget){
		super(character, target, maxAngularAcceleration, maxRotation, targetRadius, slowRadius, timeToTarget);
	}
	
	public Optional<SteeringOutput> getSteering() {
		
		//1. Calculate the target to delegae to align
		
		//Check for a zero velocity
		if(getCharacter().getVelocity().length() != 0){
			//Set the target based on the velocity
			double orientation = getCharacter().getVelocity().getWhereYoureGoing();
			getTarget().setOrientation(orientation);
		}
		
		//2. Delegate to align
		return super.getSteering();
	}
}
