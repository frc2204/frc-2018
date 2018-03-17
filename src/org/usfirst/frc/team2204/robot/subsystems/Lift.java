package org.usfirst.frc.team2204.robot.subsystems;

import org.usfirst.frc.team2204.robot.Constants;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Lift extends Subsystem {
	
	private static Lift getLift = new Lift();
	
	public static Lift getInstance() {
		return getLift;
	}
	
	private TalonSRX liftController;
	private Encoder liftEncoder; 
	private DigitalInput topLiftLimitSwitch, botLiftLimitSwitch;
	
	private Lift() {
		topLiftLimitSwitch = new DigitalInput(0);
		botLiftLimitSwitch = new DigitalInput(1);
		liftController = new TalonSRX(Constants.liftTalon);
		liftEncoder = new Encoder(0, 1, false, Encoder.EncodingType.k4X); 
		setUpEncoder();
		//http://www.ctr-electronics.com/downloads/pdf/Magnetic%20Encoder%20User's%20Guide.pdf page 7, TalonSRX only decodes 4X, at 4X, CPR is 4096
	}
	
	private void setUpEncoder() {
		liftController.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute, 0, 10);
		liftEncoder.setDistancePerPulse(Constants.distancePerPulse);
		//(2 * Math.PI * 0.716) / (4096 * 12)  = 0.0000915 inches per pulse
		//liftEncoder.setMinRate(Constants.liftstopRate); //at 0.1 of CIM reduced free speed
	}
	
	public void moveLift(double speed) {
		liftController.set(ControlMode.PercentOutput, speed);
	}
	
	public void climbRung(double speed) {
		liftController.set(ControlMode.PercentOutput, speed);
	}
	
	public void moveToSwitch() {
		if (liftEncoder.get() > Constants.numPulsesToSwitch) {
			liftController.set(ControlMode.PercentOutput, -0.7);
		} else if (liftEncoder.get() < Constants.numPulsesToSwitch) {
			liftController.set(ControlMode.PercentOutput, 0.7);
		}	
	}
	
	public void moveToHighScale() {
		if (liftEncoder.get() > Constants.numPulsesToHighScale) {
			liftController.set(ControlMode.PercentOutput, -0.7);
		} else if (liftEncoder.get() < Constants.numPulsesToHighScale) {
			liftController.set(ControlMode.PercentOutput, 0.7);
		}	
	}
	
	public void moveToMidScale() {
		if (liftEncoder.get() > Constants.numPulsesToMidScale) {
			liftController.set(ControlMode.PercentOutput, -0.7);
		} else if (liftEncoder.get() < Constants.numPulsesToMidScale) {
			liftController.set(ControlMode.PercentOutput, 0.7);
		}	
	}
	
	public void moveToLowScale() {
		if (liftEncoder.get() > Constants.numPulsesToLowScale) {
			liftController.set(ControlMode.PercentOutput, -0.7);
		} else if (liftEncoder.get() < Constants.numPulsesToLowScale) {
			liftController.set(ControlMode.PercentOutput, 0.7);
		}	
	}
	
//	public void stopLiftUp(double speed) {
//		if (topLiftLimitSwitch.get()) {
//			liftController.set(ControlMode.PercentOutput, 0);
//		} 
//	}
//	
//	public void stopLiftDown(double speed) {
//		if (botLiftLimitSwitch.get()) {
//			liftController.set(ControlMode.PercentOutput, 0);
//		}
//	}
 
	public boolean stopLiftUp() {
		return topLiftLimitSwitch.get();
	}
	
	public boolean stopLiftDown() {
		return botLiftLimitSwitch.get();
	}
	
	@Override
	protected void initDefaultCommand() {

	}

}
