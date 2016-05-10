package path;

import java.util.ArrayList;
import java.util.List;

import util.AIFG_Util;
import lombok.experimental.ExtensionMethod;

import movement.vectors.Vector;

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
	public Integer getParam(Integer currParam, Vector position, Vector lastParam) {
		if(position.distance(lastParam) <= 10 && currParam++ < nodes.size()){
			return currParam++;
		}
		return currParam;
	}

	@Override
	public Vector getPosition(Integer param) {
		return nodes.get(param);
	}
	
	public void addNode(Vector node){
		nodes.add(node);
	}

}
