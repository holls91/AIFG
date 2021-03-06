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
public class Align implements IDynamicMovement {

//	Holds the kinematic data for the character and target
	private Kinematic character, target;
//	Holds the max angular acceleration and rotation of the character
	private Double maxAngularAcceleration, maxRotation;
//	Holds the radius for arriving at the target
	private Double targetRadius;
//	Holds the radius for beginning to slow down
	private Double slowRadius;
//	Holds the time over which to achieve target speed
	private Double timeToTarget = 0.1;
	
	
	public Optional<SteeringOutput> getSteering() {
//		 Get the direction to the target
		 Double rotation = target.getOrientation() - character.getOrientation();
//		 Map the result to the (-pi, pi) interval
		 rotation = rotation.mapToRange();
		 Double rotationSize = Math.abs(rotation);
		 
//		 Check if we are there, return no steering
		 if(rotationSize<targetRadius)
			 return Optional.empty();
		 Double targetRotation = maxRotation;
//		 If we are inside the slowRadius, then use calculate a scaled rotation
		 if(rotationSize<slowRadius)
			 targetRotation *= rotationSize/slowRadius;
		
//		 The final target rotation combines speed (already in the variable) and direction
		 targetRotation *= rotation/rotationSize;
		 
//		 Acceleration tries to get to the target rotation
		 Double angular = targetRotation-character.getRotation();
		 angular /= timeToTarget;
		 
//		 Check if the acceleration is too great
		 Double angularAcceleration = Math.abs(angular);
		 if(angularAcceleration>maxAngularAcceleration)
			 angular *= maxAngularAcceleration/angularAcceleration;
		 
//		 Output the steering with linear=0 and angular
		 return new SteeringOutput(new Vector(0,0),angular).asOptional();
	}
}
