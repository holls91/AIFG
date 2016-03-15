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
public class Flee implements IDynamicMovement {

//	Holds the kinematic data for the character and target
	private Kinematic character, target;
//	Holds the maximum acceleration of the character
	private double maxAcceleration;
	
	public Optional<SteeringOutput> getSteering() {
//		 Get the direction to the target
		 Vector linear = character.getPosition().subtract(target.getPosition());
//		 Give full acceleration along this direction
		 linear = linear.normalizeAndMultiply(maxAcceleration);
		 
//		 Output the steering with linear and angular=0
		 return new SteeringOutput(linear,0).asOptional();
	}
}
