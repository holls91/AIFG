package collisionAvoidance;

import java.util.List;
import java.util.Optional;

import util.AIFG_Util;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.ExtensionMethod;

import movement.IDynamicMovement;
import movement.kinematics.Kinematic;
import movement.vectors.Vector;

import movement.dynamics.SteeringOutput;

@AllArgsConstructor
@Data
@ExtensionMethod({ AIFG_Util.class })
public class CollisionAvoidance implements IDynamicMovement {
	// Holds the kinematic data for the character and target
	private Kinematic character;

	// Holds the maximum acceleraton
	private Double maxAcceleration;

	// Holds a list of potential targets
	private List<Kinematic> targets;

	// Holds the collision radius of a character (we assume all character have
	// the same radius here)
	private Double radius;

	@Override
	public Optional<SteeringOutput> getSteering() {

		// 1. Find the target that's closest to collision
		// Store the first collision time
		Double shortestTime = Double.POSITIVE_INFINITY;

		// Store the target that collides then, and other data that we will need
		// and can avoid recalculating
		Kinematic firstTarget = null;
		Double firstMinSeparation = 0.0, minSeparation;
		Double firstDistance = 0.0, distance;
		Vector firstRelativePosition = null, relativePos;
		Vector firstRelativeVel = null, relativeVel;

		Double relativeSpeed, timeToCollision;

		for (Kinematic target : targets) {
			// Calculate the time to collision
			relativePos = target.getPosition().subtract(character.getPosition());
			relativeVel = target.getVelocity().subtract(character.getVelocity());
			relativeSpeed = relativeVel.length();
			// se c'è, il segno "-" nel rigo successivo non è presente nel libro
			timeToCollision = (relativePos.multiplyDot(relativeVel))/(relativeSpeed*relativeSpeed);

			// Check if it is going to be a collision at all
			distance = relativePos.length();
			minSeparation = distance - relativeSpeed * timeToCollision;

			if (minSeparation > 2 * radius) {
				continue;
			}

			// Check if it is the shortest
			if (timeToCollision > 0 && timeToCollision < shortestTime) {
System.out.println("devo schivare ("+target.getPosition()+")");
				// Store the time, target and other data
				shortestTime = timeToCollision;
				firstTarget = target;
				firstMinSeparation = minSeparation;
				firstDistance = distance;
				firstRelativePosition = relativePos;
				firstRelativeVel = relativeVel;
			}
		}

		// 2. Calculate the steering
		// If we have no target, then exit
		if (firstTarget == null) {
			return Optional.empty();
		}
		// If we're going to hit exactly, or if we're already colliding,
		// then do the steering based on current position.
		if (firstMinSeparation <= 0 || firstDistance < 2 * radius) {
			relativePos = firstTarget.getPosition().subtract(character.getPosition());
		}
		// Otherwise calculate the future relative position
		else {
			relativePos = firstRelativePosition.add(firstRelativeVel.multiply(shortestTime));
		}

		// Avoid the target
		relativePos.normalize();
		Vector linear = relativePos.multiply(maxAcceleration);

		return new SteeringOutput(linear, 0).asOptional();

	}

}
