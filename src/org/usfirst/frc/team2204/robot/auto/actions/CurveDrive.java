package org.usfirst.frc.team2204.robot.auto.actions;

import org.usfirst.frc.team2204.robot.subsystems.Drive;

import edu.wpi.first.wpilibj.command.Command;

public class CurveDrive extends Command {

	private Drive mDrive = Drive.getInstance();
	
	private double throttle, turn;
	
	public CurveDrive() {
		
	}
	
	public CurveDrive(double throttle, double turn) {
		this();
		this.throttle = throttle;
		this.turn = turn;
	}
	
	protected void execute() {
		mDrive.drive(throttle, turn, false);
	}

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}

	
	
}
