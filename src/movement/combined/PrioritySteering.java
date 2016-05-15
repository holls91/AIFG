package movement.combined;

import java.util.Optional;

import util.AIFG_Util;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.ExtensionMethod;

import movement.dynamics.SteeringOutput;

@Data
@AllArgsConstructor
@ExtensionMethod({AIFG_Util.class})
public class PrioritySteering {

	private BlendedSteering[] groups;
	private double epsilon;
	
	public Optional<SteeringOutput> getSteering() {
		
		SteeringOutput temp = null;
		for(BlendedSteering group : groups){
			temp = group.getSteering().get();
			
			if(temp.getLinear() != null)
				//Check if we're above the threshold, if so return
				if(AIFG_Util.length(temp.getLinear()) > epsilon || Math.abs(temp.getAngular()) > epsilon)
					return temp.asOptional();	
		}
		
		//If we got here, it means that no group had a large enough acceleration, so return
		//the small acceleration from the final group.
		return temp.asOptional();
	}
}
