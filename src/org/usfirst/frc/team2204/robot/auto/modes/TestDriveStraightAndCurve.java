package org.usfirst.frc.team2204.robot.auto.modes;

import org.usfirst.frc.team2204.robot.auto.actions.CurveDrive;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class TestDriveStraightAndCurve extends CommandGroup { 
	
	public TestDriveStraightAndCurve() {
		addSequential(new CurveDrive(0.5, 0.5), 1);
	}

}
