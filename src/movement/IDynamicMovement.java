package movement;

import java.util.Optional;

import movement.dynamics.SteeringOutput;

public interface IDynamicMovement {

	public Optional<SteeringOutput> getSteering();

}
