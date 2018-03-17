package org.usfirst.frc.team2204.robot.subsystems;

import org.usfirst.frc.team2204.robot.Constants;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Subsystem;

public class LED extends Subsystem {

	private static LED getLED = new LED();
	
	public static LED getInstance() {
		return getLED;
	}
	
	private Spark ledController;
	
	public LED() {
		ledController = new Spark(Constants.ledPort);
	}
	
	public void setColor(double color) {
		ledController.set(color);
	}
	
	@Override
	protected void initDefaultCommand() {

	}
	
	public static final double rainbow = -0.99;
	public static final double ocean = -0.95;
	public static final double fluroPurple  = 0.57;
	public static final double fluroBlue = 0.81;
	public static final double red = 0.61;
	public static final double skyBlue = 0.83;
	public static final double darkBlue = 0.85;	

}
