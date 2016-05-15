package gui;

import java.util.ArrayList;
import java.util.List;

import movement.IDynamicMovement;
import movement.dynamics.Seek;
import movement.kinematics.Kinematic;
import movement.pathFollowing.FollowPathFirst;
import movement.vectors.Vector;
import path.Path;
import collisionAvoidance.CollisionAvoidance;


public class MainTest {

	public static void main(String[] args) {
		GUI_Manager gm = new GUI_Manager();
		gm.setCanvasSize(512,512);
		gm.setXscale(-5,25);
		gm.setYscale(-5,25);

		Kinematic character = new Kinematic(new Vector(0.0, 0.0), 3.0, new Vector(0.2,0.2), 1.0);
		Kinematic target = new Kinematic(new Vector(15.0, 5.0), 0.0, new Vector(0.0,0.0), 0.0);

    	Seek seek = new Seek(character, target, 0.15);
    	
    	Path path = new Path();
    	path.addNode(new Vector(0, 0));
    	path.addNode(new Vector(0, 10));
    	path.addNode(new Vector(10,10));
    	path.addNode(new Vector(20,20));
    	path.addNode(new Vector(20,0));
    	
    	IDynamicMovement fpf = new FollowPathFirst(seek, path, 1, 1);	// il secondo 1 non � 0 senn� d� NaN
        List<Kinematic> targets = new ArrayList<Kinematic>() {{
        	//add(new Kinematic(new Vector(5, 0.0), 0.0, new Vector(0.0,0.0), 0.0));
        	add(new Kinematic(new Vector(0, 10), 0.0, new Vector(0.0,0.0), 0.0));
        	add(new Kinematic(new Vector(5,5), 0.0, new Vector(0.0,0.0), 0.0));
        	add(new Kinematic(new Vector(7,17), 0.0, new Vector(0.0,0.0), 0.0));
        	add(new Kinematic(new Vector(20,6), 0.0, new Vector(0.0,0.0), 0.0));
        }};
    	
        IDynamicMovement ca = new CollisionAvoidance(character,0.15, targets,2.5);

    	gm.setCharacter(character);
    	gm.setMovement(fpf);	// per FollowPath
//    	gm.setMovement(ca);	// per CollisionAvoidance
    	gm.setTarget(target);
    	gm.setExtraObject(path.getNodes().toArray());	// par FollowPath
    	// gm.setExtraObject(targets.toArray());	// per CollisionAvoidance
    	gm.run();	// loop infinito
//    	gm.run(3*1000);// loop finito
    	
	}

}
