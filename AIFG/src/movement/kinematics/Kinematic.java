package movement.kinematics;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.ExtensionMethod;
import movement.dynamics.SteeringOutput;
import movement.vectors.Vector;
import util.AIFG_Util;

@Data
@AllArgsConstructor
@ExtensionMethod({AIFG_Util.class})
public class Kinematic {
	
	private Vector position;
	private Double orientation;
	private Vector velocity;
	private Double rotation; // velocita' angolare

//	update tramite metodo di Newton
//	che è "time"? Unità per secondo (inverso di FPS)
	public void update(SteeringOutput steering, double time) {
//		aggiorna posizione, orientamento velocita' e rotazione
		position = position.add(velocity.multiply(time));
		orientation += rotation*time;
		velocity = velocity.add(steering.getLinear().multiply(time));
		rotation += steering.getAngular()*time;
	}

	public void update(SteeringOutput steering, double maxSpeed, double time) {
//		Update the position and orientation
		position = position.add(velocity.multiply(time));
		orientation += rotation*time;
//		and the velocity and rotation
		velocity = velocity.add(steering.getLinear().multiply(time));
		rotation += steering.getAngular()*time;
//		Check for speeding and clip
		if(velocity.length()>maxSpeed) {
			velocity.normalize();
			velocity = velocity.multiply(maxSpeed);
		}
	}
	
}
