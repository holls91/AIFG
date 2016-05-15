package gui;

import gui.util.Draw;
import gui.util.DrawListener;

import java.util.Optional;

import movement.dynamics.Seek;
import movement.dynamics.SteeringOutput;
import movement.kinematics.Kinematic;
import movement.pathFollowing.FollowPathFirst;
import movement.vectors.Vector;
import path.Path;

public class Main_AIFG_Path_Draw implements DrawListener {
	
	private static int SIZE = 512;

    private Draw draw = new Draw();

    public Main_AIFG_Path_Draw() {
        draw.setCanvasSize(SIZE, SIZE);
        draw.setXscale(-5, 25);
        draw.setYscale(-5, 25);
        draw.addListener(this);
        draw.show(20);
    }
    
    public void run() {
        Kinematic character = new Kinematic(new Vector(0.0, 0.0), 3.0, new Vector(0.2,0.2), 1.0);
    	Kinematic target = new Kinematic(new Vector(0.0, 0.0), 0.0, new Vector(0.0,0.0), 0.0);
    	
    	Seek seek = new Seek(character, target, 0.15);
    	
    	Path path = new Path();
    	path.addNode(new Vector(0, 0));
    	path.addNode(new Vector(0, 10));
    	path.addNode(new Vector(10,10));
    	path.addNode(new Vector(20,20));
    	path.addNode(new Vector(20,0));
    	draw.setPenColor(Draw.BLACK);
    	for(Vector v: path.getNodes()) {
    		draw.filledCircle(v.getDoubleX(),v.getDoubleZ(),0.5);
    	}
    	
    	FollowPathFirst fpf = new FollowPathFirst(seek, path, 1, 1);	// il secondo 1 non è 0 sennò dà NaN
    	
    	draw.setPenColor(Draw.GREEN);
    	for(int i=0; i>=0; i++){
    		Optional<SteeringOutput> steering = fpf.getSteering();
    		if(steering.isPresent()){
    			character.update(steering.get(), 0.7, 0.65);
    			draw.setPenColor(Draw.GREEN);
    			draw.filledCircle(character.getPosition().getDoubleX(),character.getPosition().getDoubleZ(),1);
//    			System.out.println("Character: "+character.getPosition()+" - Velocity: "+character.getVelocity());
//    			System.out.println("Target: "+fpf.getPath().getPosition(fpf.getCurrentParam()));	
    		}
    		else{
//    			System.out.println("Target raggiunto!");
    			break;
    		}
    		draw.show(25);
    		draw.setPenColor(Draw.WHITE);
    		draw.filledCircle(character.getPosition().getDoubleX(),character.getPosition().getDoubleZ(),1.1);
    		draw.setPenColor(Draw.BLACK);
    		for(Vector v: path.getNodes()) {
        		draw.filledCircle(v.getDoubleX(),v.getDoubleZ(),0.5);
        	}
    	}
    
    }
    
	public static void main(String[] args) {
		Main_AIFG_Path_Draw pippo = new Main_AIFG_Path_Draw();
		pippo.run();
	}

}
