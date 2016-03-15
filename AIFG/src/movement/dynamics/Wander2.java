package movement.dynamics;
import java.util.Optional;

import static util.AIFG_Util.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import movement.kinematics.Kinematic;
/*
NOTE:
non sono sicuro dei tipi!!!
il metodo asVector() non è probabilmente esattamente chiamato così...
*/
import movement.vectors.Vector;

@Data
@EqualsAndHashCode(callSuper=true)
public class Wander2 extends Face {

	private Double wanderOffset;
	private Double wanderRadius;

	private Double wanderRate;

	private Double wanderOrientation;

	private Double maxAcceleration;

	public Wander2(Kinematic character, Kinematic target,
				   Double maxAngularAcceleration, Double maxRotation,
				   Double targetRadius, Double slowRadius, Double timeToTarget,
				   Double wanderOffset,
				   Double wanderRadius,
				   Double wanderRate,
				   Double wanderOrientation,
				   Double maxAcceleration) {
		super(character, target, maxAngularAcceleration, maxRotation, targetRadius,
				slowRadius, timeToTarget);
		this.wanderOffset = wanderOffset;
		this.wanderRadius = wanderRadius;
		this.wanderRate = wanderRate;
		this.wanderOrientation = wanderOrientation;
		this.maxAcceleration = maxAcceleration;
		// TODO Auto-generated constructor stub
	}
	
	public Optional<SteeringOutput> getSteering() {
		wanderOrientation += randomBinomial()*wanderRate;

		Double targetOrientation = wanderOrientation+super.getCharacter().getOrientation();

		Vector target = add(super.getCharacter().getPosition(), multiply(angleToVector(getCharacter().getOrientation()).asVector(), wanderOffset));

		target = add(target, multiply(angleToVector(targetOrientation).asVector(), wanderRadius));
		
		getTarget().setPosition(target);

		SteeringOutput steering = super.getSteering().get();

		steering.setLinear(multiply(angleToVector(super.getCharacter().getOrientation()).asVector(), maxAcceleration));

		return super.getSteering();
	}

}