package movement.vectors;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Vector3D {

	private Number x;
	private Number y;
	private Number z;

	public Vector asVector() {
		return new Vector(x,y,z);
	}

	public double getDoubleX() {
		return x.doubleValue();
	}
	public double getDoubleY() {
		return y.doubleValue();
	}
	public double getDoubleZ() {
		return z.doubleValue();
	}
}
