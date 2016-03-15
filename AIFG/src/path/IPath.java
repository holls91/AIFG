package path;

import movement.vectors.Vector;

public interface IPath {

	public Vector getParam(Vector position, Vector lastParam);
	public Vector getPosition(Vector param);
	
}
