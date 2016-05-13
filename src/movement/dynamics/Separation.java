package movement.dynamics;

import java.util.Optional;

import util.AIFG_Util;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.ExtensionMethod;

import movement.kinematics.Kinematic;
import movement.vectors.Vector;

@Data 
@AllArgsConstructor
@ExtensionMethod({AIFG_Util.class})
public class Separation {

	private Kinematic character;
	private Kinematic[] targets;
	private double threshold, decayCoefficient, maxAcceleration;
	
	public Optional<SteeringOutput> getSteering() {
		double distance, strength;
		Vector linear = new Vector(0,0);
		for(Kinematic target : targets){
			Vector direction = target.getPosition().subtract(character.getPosition());
			distance = direction.length();
			if (distance < threshold){
				strength = Math.min(decayCoefficient/(distance*distance), maxAcceleration);
				direction = direction.normalize();
				linear = linear.add(direction.multiply(strength));
			}
		}
		return new SteeringOutput(linear,0).asOptional();
	}
}
