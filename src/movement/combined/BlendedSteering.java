package movement.combined;

import java.util.Optional;

import util.AIFG_Util;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.ExtensionMethod;

import movement.IDynamicMovement;
import movement.vectors.Vector;

import movement.dynamics.SteeringOutput;

@Data
@AllArgsConstructor
@ExtensionMethod({AIFG_Util.class})
public class BlendedSteering {

	private BehaviorAndWeight[] behaviors;
	
	public Optional<SteeringOutput> getSteering() {
		
		double totalWeight = 0, angular = 0;
		SteeringOutput temp = null;
		Vector linear = null;
		
		for(BehaviorAndWeight behavior : behaviors){
			temp = behavior.behavior.getSteering().get();
			linear = temp.getLinear();
			angular = temp.getAngular();
			linear = AIFG_Util.add(linear, AIFG_Util.multiply(linear,behavior.weight));
			angular += angular * behavior.weight;
			
			totalWeight += behavior.weight;
		}
		
		if(totalWeight > 0.0){
			totalWeight = 1.0/totalWeight;
			linear = AIFG_Util.multiply(linear, totalWeight);
			angular = angular*totalWeight;
		}
		return new SteeringOutput(linear,angular).asOptional();
	}
	
	@Data
	@AllArgsConstructor
	class BehaviorAndWeight{
		IDynamicMovement behavior;
		double weight;
	}
}
