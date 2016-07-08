package movement.dynamics;

import java.util.Optional;

import util.AIFG_Util;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.ExtensionMethod;

import movement.IDynamicMovement;
import movement.kinematics.Kinematic;
import movement.vectors.Vector;

@Data
@AllArgsConstructor
@ExtensionMethod({AIFG_Util.class})
public class Arrive implements IDynamicMovement {

//	Holds the kinematic data for the character and target
	private Kinematic character, target;
//	Holds the max acceleration and speed of the character
	private double maxAcceleration, maxSpeed = 10d;
//	Holds the radius for arriving at the target
	private double targetRadius;
//	Holds the radius for beginning to slow down
	private double slowRadius;
//	Holds the time over which to achieve target speed
	private double timeToTarget = 0.1;
	
	
	public Optional<SteeringOutput> getSteering() {
//		 Get the direction to the target
		 Vector direction = target.getPosition().subtract(character.getPosition());
		 double distance = direction.length();
//		 Check if we are there, return no steering
		 if(distance<targetRadius)
			 return Optional.empty();
		 
		 double targetSpeed = maxSpeed;
//		 If we are intside the slowRadius, then calculate a scaled speed
		 if(distance<slowRadius)
			 targetSpeed *= distance/slowRadius;
		 
//		 The target velocity combines speed and direction
		 Vector targetVelocity = direction;
		 targetVelocity = targetVelocity.normalizeAndMultiply(targetSpeed);
		 
//		 Acceleration tries to get to the target velocity
		 Vector linear = targetVelocity.subtract(character.getVelocity());
		 linear = linear.divide(timeToTarget);
		 
//		 Check if the acceleration is too fast
		 if(linear.length()>maxAcceleration)
			 linear = linear.normalizeAndMultiply(maxAcceleration);
		 
//		 Output the steering with linear and angular=0
		 return new SteeringOutput(linear,0).asOptional();
	}
	
}
