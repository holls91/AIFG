package movement.pathFollowing;

import java.util.Optional;

import path.IPath;
import util.AIFG_Util;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.ExtensionMethod;

import movement.IDynamicMovement;

import movement.dynamics.Seek;
import movement.dynamics.SteeringOutput;

@Data
@AllArgsConstructor
@ExtensionMethod({AIFG_Util.class})
public class FollowPathFirst implements IDynamicMovement {

	// variabile Seek
	private Seek seek;
	
	// Holds the path to follow
	private IPath path;
	
	//  Holds the distance along the path to generate the target. Can be negative if the character is to move along the reverse direction.
	private Integer pathOffset;
	
	// Holds the current position on the path
	private Integer currentParam = 0;	// currentParam
	
	
	public Optional<SteeringOutput> getSteering() {
//		1. Calculate the target to delegate to face (non "... to seek"?)
//		Find the current position on the path
		currentParam = path.getParam(currentParam, seek.getCharacter().getPosition(), path.getPosition(currentParam));
		
		// Offset it
		Integer targetParam = currentParam+pathOffset;// = currentParam.add(pathOffset);
		
		// Get the target position
		seek.getTarget().setPosition(path.getPosition(targetParam));
		
		// 2. Delegate to seek
		return seek.getSteering();
	}

}
