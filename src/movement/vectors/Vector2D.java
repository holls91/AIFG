package movement.vectors;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Vector2D {

	private Number x;
	private Number z;
	
	public Vector asVector() {
		return new Vector(x,z);
	}

	public double getDoubleX() {
		return x.doubleValue();
	}
	public double getDoubleZ() {
		return z.doubleValue();
	}
	
}
