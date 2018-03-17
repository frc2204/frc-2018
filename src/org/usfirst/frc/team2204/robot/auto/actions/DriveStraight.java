package org.usfirst.frc.team2204.robot.auto.actions;

import org.usfirst.frc.team2204.robot.subsystems.Drive;

import edu.wpi.first.wpilibj.command.Command;

public class DriveStraight extends Command {

	private Drive mDrive = Drive.getInstance();
	
	private double speed;
	
	public DriveStraight() {
		
	}
	
	public DriveStraight(double speed) {
		this();
		this.speed = speed;
	}

	protected void execute() {
		mDrive.drive(speed, 0, false);
	}
	
	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}

}
