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
	
	
	public Vector limit(Double limit){
		return limit2(limit*limit);
	}
	
	private Vector limit2(Double limit2){
		float len2 = x.floatValue()*x.floatValue()+z.floatValue()*z.floatValue();
		if(len2 > limit2){
			x = x.doubleValue()*(float)Math.sqrt(limit2 / len2);
			z = z.doubleValue()*(float)Math.sqrt(limit2 / len2);
		}
		return this;
	}
	
}
