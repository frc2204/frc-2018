package org.usfirst.frc.team2204.robot.auto.actions;

import org.usfirst.frc.team2204.robot.subsystems.Lift;

import edu.wpi.first.wpilibj.command.Command;

public class RaiseLift extends Command {

	private Lift mLift = Lift.getInstance();
	
	private double seconds;
	
	public RaiseLift() {
		
	} 
	
	public RaiseLift(double seconds) {
		this();
		this.seconds = seconds;
	}
	
	protected void execute() {
		mLift.moveLift(seconds);
	}
	
	@Override
	protected boolean isFinished() {
		return false;
	}

}
