package movement.dynamics;

import lombok.AllArgsConstructor;
import lombok.Data;
import movement.vectors.Vector;

@Data
@AllArgsConstructor
public class SteeringOutput {

	private Vector linear;
	private double angular;
	
}
