package org.usfirst.frc.team2204.robot.subsystems;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Intake extends Subsystem {

	private static Intake getIntake = new Intake();
	
	public static Intake getInstance() {
		return getIntake;
	}
	
	private DoubleSolenoid intakeClamp;
	private Compressor mCompressor;
	private DigitalOutput limitSwitch;
	
	private Intake() {
		intakeClamp = new DoubleSolenoid(0, 1, 2);
		mCompressor = new Compressor(0);
		limitSwitch = new DigitalOutput(2);
	}
	
	public void clampCube() {
		intakeClamp.set(DoubleSolenoid.Value.kForward);
	}
	
	public void unClampCube() {
		intakeClamp.set(DoubleSolenoid.Value.kReverse);
	}
	
	public void useCompressor() {
		mCompressor.setClosedLoopControl(true);
	}
	
	public boolean cubeDetected() {
		return limitSwitch.get();
	}
	
	@Override
	protected void initDefaultCommand() {

	}

}
