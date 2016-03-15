package movement.vectors;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class Vector {

	private Number x;
	private Number y = 0;
	private Number z;
	
	public Vector(Number x, Number z) {
		this.x = x;
		this.z = z;
	}
	
	public Vector2D getAsVector2D() {
		return new Vector2D(x,z);
	}
	public Vector3D getAsVector3D() {
		return new Vector3D(x,y,z);
	}

	public Double getDoubleX() {
		return x.doubleValue();
	}
	public Double getDoubleY() {
		return y.doubleValue();
	}
	public Double getDoubleZ() {
		return z.doubleValue();
	}
	
	public String toString(){
		return "x: "+x+" - y: "+y+" - z: "+z;
	}
	
}
