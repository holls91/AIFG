package movement.kinematics;

import static util.AIFG_Util.randomBinomial;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.ExtensionMethod;
import movement.IKinematicMovement;
import movement.Static;
import util.AIFG_Util;

@Data
@AllArgsConstructor
@ExtensionMethod({AIFG_Util.class})
public class KinematicWander implements IKinematicMovement {

	private Static character;
	private double maxSpeed;
	private double maxRotation;
	
	public Optional<KinematicSteeringOutput> getSteering() {
		KinematicSteeringOutput steering = new KinematicSteeringOutput();
		
		// Get velocity from the vector form of the orientation
		character.getOrientation().angleToVector().asVector().multiply(maxSpeed);
		
		//  Change our orientation randomly
		double rotation = randomBinomial()*maxRotation;
		steering.setRotation(rotation);
		
		return steering.asOptional();
	}

}
