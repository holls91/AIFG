package path;

import java.util.List;

import movement.vectors.Vector;

public interface IPath {

	public Integer getParam(Integer pathOffset, Integer currParam, Vector position, Vector lastParam);
	public Vector getPosition(Integer param);
	public List<Vector> getNodes();
	
}
