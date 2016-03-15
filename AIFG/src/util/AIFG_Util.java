package util;

import static java.lang.Math.PI;
import static java.lang.Math.atan2;
import static java.lang.Math.cos;
import static java.lang.Math.sin;

import java.util.Optional;
import java.util.Random;

import lombok.experimental.ExtensionMethod;
import lombok.experimental.UtilityClass;
import movement.dynamics.SteeringOutput;
import movement.vectors.Vector;
import movement.vectors.Vector2D;

@UtilityClass
@ExtensionMethod({Math.class,AIFG_Util.class})
public class AIFG_Util {

	/* restituisce un vettore normalizzato */
	public Vector2D angleToVector(double angle) {
		return new Vector2D(sin(angle),cos(angle));
	}
	/* restituisce l'angolo tra l'asse delle Z e il vettore */
	public Double vectorToAngle(Vector2D v) {	// vector = [sin,cos]
		return Math.PI+atan2(v.getDoubleX(),v.getDoubleZ());
	}
	
	public Vector add(Vector v1, Vector v2) {
		return new Vector(v1.getDoubleX()+v2.getDoubleX(),
						  v1.getDoubleY()+v2.getDoubleY(),
						  v1.getDoubleZ()+v2.getDoubleZ());
	}
	public Vector subtract(Vector v1, Vector v2) {
		return new Vector(v1.getDoubleX()-v2.getDoubleX(),
						  v1.getDoubleY()-v2.getDoubleY(),
						  v1.getDoubleZ()-v2.getDoubleZ());
	}
	public Vector multiply(Vector v, Double x) {
		return new Vector(v.getDoubleX()*x,
						  v.getDoubleY()*x,
						  v.getDoubleZ()*x);
	}
	public Vector multiplyVectorial(Vector v1, Vector v2) {
		return new Vector(v1.getDoubleY()*v2.getDoubleZ()-v1.getDoubleZ()*v2.getDoubleY(),
						  v1.getDoubleZ()*v2.getDoubleX()-v1.getDoubleX()*v2.getDoubleZ(),
						  v1.getDoubleX()*v2.getDoubleY()-v1.getDoubleY()*v2.getDoubleX());
	}
	public Double multiplyDot(Vector v1, Vector v2) {	// prodotto scalare: a·b = |a|*|b|*cos(theta) = a1*b1+a2*b2+...+an*bn
		return v1.getDoubleX()*v2.getDoubleX()
			  +v1.getDoubleY()*v2.getDoubleY()
			  +v1.getDoubleZ()*v2.getDoubleZ();
	}
	public Vector divide(Vector v, Double x) {
		return v.multiply(1/x);
	}
	
//	public Double length(Vector v) {
//		return multiplyDot(v,v);
//	}
	
	public Vector min(Vector a, Vector b) {
		return a.length()<b.length() ? a : b;
	}
	public Vector max(Vector a, Vector b) {
		return a.length()>b.length() ? a : b;
	}
	
	public Double length(Vector v) {
		return Math.sqrt(multiplyDot(v,v));
	}	
	
	public SteeringOutput add(SteeringOutput a, SteeringOutput b) {
		return new SteeringOutput(a.getLinear().add(b.getLinear()),a.getAngular()+b.getAngular());
	}
	public SteeringOutput multiply(SteeringOutput steering, double mult) {
		return new SteeringOutput(steering.getLinear().multiply(mult),mult*steering.getAngular());
	}
	public SteeringOutput add(SteeringOutput a, SteeringOutput b, double mult) {
		return a.add(b.multiply(mult));
	}
	public SteeringOutput crop(SteeringOutput steer, SteeringOutput crop) {
		return new SteeringOutput(min(steer.getLinear(), crop.getLinear()),Math.min(steer.getAngular(), crop.getAngular()));
	}
	
	public Double normalize(Double x) {
		return x/Math.abs(x);
	}
	public Vector normalize(Vector v) {
		return v.divide(v.length());
	}
	public Vector normalizeAndMultiply(Vector v, Double x) {
		return v.normalize().multiply(x);
	}
	
	public Double getNewOrientation(Double currentOrientation, Vector velocity) {
//		if(velocity!=null) {
//			 sarà questo? vedi pag 49
//			 return Math.atan2(-velocity.getDoubleX(),velocity.getDoubleZ());
//			 oppure
//			return velocity.getAsVector2D().vectorToAngle(); 
//		}
//		else
//			return currentOrientation;
		Vector2D v = velocity.getAsVector2D();
		return velocity==null ? currentOrientation : atan2(-v.getDoubleX(),v.getDoubleZ());
//		return velocity==null ? currentOrientation : velocity.getAsVector2D().vectorToAngle();
	}
	
	public Double getWhereYoureGoing(Vector velocity) {
		if(velocity!=null) {
			 return Math.atan2(-velocity.getDoubleX(),velocity.getDoubleZ());
		}
		return null;
	}	

	public <T> Optional<T> asOptional(T elem) {
		return elem.asOptional(true);
	}
	public <T> Optional<T> asOptional(T elem, boolean allowNull) {
		return allowNull ? Optional.ofNullable(elem) : Optional.of(elem);
	}
	
	public Double randomBinomial() {
		return Math.random()-Math.random();
	}
	
	public double randomBinomial(Random rnd) {
	    return rnd.nextDouble()-rnd.nextDouble();
	}
	
	/* angolo in [-pi,pi] */
	public Double mapToRange(Double angle) {
		while(angle>PI)
			angle -= 2*PI;
		while(angle<-PI)
			angle += 2*PI;
		return angle;
	}
	
}
