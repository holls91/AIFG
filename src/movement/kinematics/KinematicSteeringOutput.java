package movement.kinematics;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import movement.vectors.Vector;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class KinematicSteeringOutput {

	private Vector velocity;
	private double rotation;

}