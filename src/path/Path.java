package path;

import java.util.ArrayList;
import java.util.List;

import lombok.experimental.ExtensionMethod;
import movement.vectors.Vector;
import util.AIFG_Util;

/**
 * Consigliabile implementare il Path come un insieme di segmenti
 * @author Emmanuel
 *
 */
@ExtensionMethod({AIFG_Util.class})
public class Path implements IPath {

	private List<Vector> nodes;
	
	public Path(){
		nodes = new ArrayList<>();
	}
	
	@Override
	public Integer getParam(Integer pathOffset, Integer currParam, Vector position, Vector lastParam) {
		if(AIFG_Util.distance(position, lastParam) <= 5){
//System.out.println("Distanza <=5: "+AIFG_Util.distance(position, lastParam));
			return currParam+pathOffset;
		}
//System.out.println("Distanza >5: "+AIFG_Util.distance(position, lastParam));
		return currParam;
	}

	@Override
	public Vector getPosition(Integer param) {
		return nodes.get(param);
	}
	
	public void addNode(Vector node){
		nodes.add(node);
	}
	
	public List<Vector> getNodes(){
		return nodes;
	}

}
