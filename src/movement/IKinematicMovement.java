package movement;

import java.util.Optional;

import movement.kinematics.KinematicSteeringOutput;

public interface IKinematicMovement {

	public Optional<KinematicSteeringOutput> getSteering();

}
