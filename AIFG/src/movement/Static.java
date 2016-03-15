package movement;

import lombok.AllArgsConstructor;
import lombok.Data;
import movement.vectors.Vector2D;

@Data
@AllArgsConstructor
public class Static {

	private Vector2D position;
	private double orientation;
	
}
