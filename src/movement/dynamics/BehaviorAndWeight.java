package movement.dynamics;

import movement.IDynamicMovement;
import util.WeightedObject;

public class BehaviorAndWeight extends WeightedObject<IDynamicMovement> {

	public BehaviorAndWeight(IDynamicMovement data, double weight) {
		super(data,weight);
	}
	
}
