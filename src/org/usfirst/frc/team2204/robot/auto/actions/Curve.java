package org.usfirst.frc.team2204.robot.auto.actions;

import org.usfirst.frc.team2204.robot.subsystems.Drive;

import edu.wpi.first.wpilibj.command.Command;

public class Curve extends Command {

	private Drive mDrive = Drive.getInstance();
	
	private double speed;
	
	public Curve() {

	}
	
	public Curve(double speed) {
		this();
		this.speed = speed;
	}

	protected void execute() {
		mDrive.drive(speed, 0, false);
	}
	
	@Override
	protected boolean isFinished() {
		return false;
	}

}
