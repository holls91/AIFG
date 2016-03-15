package main;

import movement.IDynamicMovement;
import movement.Static;
import movement.dynamics.Align;
import movement.dynamics.AlignFlee;
import movement.dynamics.Arrive;
import movement.dynamics.Evade;
import movement.dynamics.Face;
import movement.dynamics.Flee;
import movement.dynamics.LookWhereYoureGoing;
import movement.dynamics.Pursue;
import movement.dynamics.Reach;
import movement.dynamics.Seek;
import movement.dynamics.VelocityMatch;
import movement.dynamics.Wander2;
import movement.kinematics.Kinematic;

public class Main_Complete {

		public static void main(String[] args) {
			
			// tutti i parametri:
			Static characterS = null;
			Static targetS = null;
			double radius = 1;

			Kinematic character = null;
			Kinematic target = null;

			double maxAngularAcceleration = 0;
			Double maxAcceleration = 0.25;
			double maxSpeed = 10d;
			double maxRotation = 0;
			double maxPrediction = 1;

			double targetRadius = 0;
			double slowRadius = 0;
			double timeToTarget = 0.25;

			Double wanderOffset = 1.0;
			Double wanderRadius = 2.0; 
			Double wanderRate = 0.5;
			Double wanderOrientation = 0.5;  


			// tutti i costruttori:
//			IKinematicMovement kinematicarrive = new KinematicArrive(characterS,targetS,maxSpeed,radius,timeToTarget);
//			IKinematicMovement kinematicflee = new KinematicFlee(characterS,targetS,maxSpeed);
//			IKinematicMovement kinematicseek = new KinematicSeek(characterS,targetS,maxSpeed);
//			IKinematicMovement kinematicwander = new KinematicWander(characterS,maxSpeed, maxRotation);
			IDynamicMovement align = new Align(character,target,maxAngularAcceleration,maxRotation,targetRadius,slowRadius,timeToTarget);
			IDynamicMovement alignflee = new AlignFlee(character,target,maxAngularAcceleration,maxRotation,targetRadius,slowRadius,timeToTarget);
			IDynamicMovement arrive = new Arrive(character,target,maxAcceleration,maxSpeed,targetRadius,slowRadius,timeToTarget);
			IDynamicMovement evade = new Evade(character,target,maxAcceleration,maxPrediction);
			IDynamicMovement face = new Face(character,target,maxAngularAcceleration,maxRotation,targetRadius,slowRadius,timeToTarget);
			IDynamicMovement flee = new Flee(character,target,maxAcceleration);
			IDynamicMovement lookwhereyouregoing = new LookWhereYoureGoing(character,target,maxAngularAcceleration,maxRotation,targetRadius,slowRadius,timeToTarget);
			IDynamicMovement pursue = new Pursue(character,target,maxAcceleration,maxPrediction);
			IDynamicMovement reach = new Reach(character, target, maxAcceleration, maxSpeed, targetRadius, slowRadius, timeToTarget,maxPrediction);
			IDynamicMovement seek = new Seek(character,target,maxAcceleration);
			IDynamicMovement velocitymatch = new VelocityMatch(character,target,maxAcceleration,timeToTarget);
			IDynamicMovement wander2 = new Wander2(character,target,maxAngularAcceleration,maxRotation,targetRadius,slowRadius,timeToTarget,wanderOffset,wanderRadius,wanderRate,wanderOrientation,maxAcceleration);

		}

}
