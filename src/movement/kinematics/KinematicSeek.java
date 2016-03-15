package movement.kinematics;

import static util.AIFG_Util.getNewOrientation;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.ExtensionMethod;
import movement.IKinematicMovement;
import movement.Static;
import movement.vectors.Vector;
import util.AIFG_Util;

@Data
@AllArgsConstructor
@ExtensionMethod({AIFG_Util.class})
public class KinematicSeek implements IKinematicMovement {

	private Static character;
	private Static target;
	private double maxSpeed;

	public Optional<KinematicSteeringOutput> getSteering() {
		KinematicSteeringOutput steering = new KinematicSteeringOutput();

		// get direction to the target
		Vector velocity = target.getPosition().asVector().subtract(character.getPosition().asVector());
		velocity.normalize().multiply(maxSpeed);
		steering.setVelocity(velocity);

		double orientation = getNewOrientation(character.getOrientation(),steering.getVelocity());
		character.setOrientation(orientation);

		steering.setRotation(0);
		return steering.asOptional();
	}

}