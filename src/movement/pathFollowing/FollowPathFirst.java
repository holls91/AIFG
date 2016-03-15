package movement.pathFollowing;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.ExtensionMethod;
import movement.IDynamicMovement;
import movement.dynamics.Seek;
import movement.dynamics.SteeringOutput;
import movement.vectors.Vector;
import path.IPath;
import util.AIFG_Util;

@Data
@AllArgsConstructor
@ExtensionMethod({AIFG_Util.class})
public class FollowPathFirst implements IDynamicMovement {

	// variabile Seek
	private Seek seek;
	
	// Holds the path to follow
	private IPath path;
	
	//  Holds the distance along the path to generate the target. Can be negative if the character is to move along the reverse direction.
	private Double pathOffset;
	
	// Holds the current position on the path
	private Vector currentPos;	// currentParam
	
	
	public Optional<SteeringOutput> getSteering() {
//		1. Calculate the target to delegate to face (non "... to seek"?)
//		Find the current position on the path
		Vector currentParam = path.getParam(seek.getCharacter().getPosition(), currentPos);
		
		// Offset it
		Vector targetParam = null;// = currentParam.add(pathOffset);
		
		// Get the target position
		seek.getTarget().setPosition(path.getPosition(targetParam));
		
		// 2. Delegate to seek
		return seek.getSteering();
	}

}
