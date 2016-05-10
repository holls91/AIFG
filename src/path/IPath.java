package path;

import movement.vectors.Vector;

public interface IPath {

	public Integer getParam(Integer currParam, Vector position, Vector lastParam);
	public Vector getPosition(Integer param);
	
}
