package org.usfirst.frc.team2204.robot;

public interface ControlsInterface {

	double getThrottle();
	double getTurn();
	boolean isQuickTurn();
	boolean isHighGear();
	boolean isLowGear();
	boolean brakeRobot();
	
	double getLiftSpeed();
	boolean intakeCube();
	boolean releaseCube();
	boolean clampRollers();
	boolean unClampRollers();
	boolean climbRung();
	boolean rotateRollersIn();
	boolean rotateRollersOut();
	
	boolean clampCube();
	boolean unClampCube();
	boolean disableLimitSwitches();
	
	boolean liftToSwitch();
	boolean liftToLowScale();
	boolean liftToMidScale();
	boolean liftToHighScale();
}
