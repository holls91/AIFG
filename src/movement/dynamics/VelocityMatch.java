package movement.dynamics;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.ExtensionMethod;
import movement.IDynamicMovement;
import movement.kinematics.Kinematic;
import movement.vectors.Vector;
import util.AIFG_Util;

@Data
@AllArgsConstructor
@ExtensionMethod({AIFG_Util.class})
public class VelocityMatch implements IDynamicMovement {

//	Holds the kinematic data for the character and target
	private Kinematic character, target;
//	Holds the max acceleration of the character
	private double maxAcceleration;
//	Holds the time over which to achieve target speed
	private double timeToTarget = 0.1;
	
	
	public Optional<SteeringOutput> getSteering() {
//		 Acceleration tries to get to the target velocity
		 Vector linear = target.getPosition().subtract(character.getPosition());
		 linear = linear.divide(timeToTarget);
//		 Check if the acceleration is too fast
		 if(linear.length()>maxAcceleration)
			 linear = linear.normalizeAndMultiply(maxAcceleration);
//		 Output the steering with linear and angular=0
		 return new SteeringOutput(linear,0).asOptional();
	}
	
}
