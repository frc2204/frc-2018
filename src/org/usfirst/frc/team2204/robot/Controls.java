package org.usfirst.frc.team2204.robot;

import edu.wpi.first.wpilibj.Joystick;

public class Controls implements ControlsInterface {

    private static Controls getControls = new Controls();

    public static Controls getInstance() {
        return getControls;
    }
    
    private final Joystick throttleStick;
    private final Joystick turnStick;
    private final Joystick assistStick;
    
    private Controls() {
    	assistStick = new Joystick(0);
    	throttleStick = new Joystick(1);
    	turnStick = new Joystick(2);
    }
	
	@Override
	public double getThrottle() {
		return throttleStick.getY();
	}

	@Override
	public double getTurn() {
		return turnStick.getY();
	}

	@Override
	public boolean isQuickTurn() {
		return turnStick.getRawButton(1);
	}

	@Override
	public boolean isHighGear() {
		return throttleStick.getRawButton(2);
	}

	@Override
	public boolean isLowGear() {
		return throttleStick.getRawButton(1);
	}
	
	@Override
	public boolean brakeRobot() {
		return turnStick.getRawButton(2);
	}

	@Override
	public double getLiftSpeed() {
		return assistStick.getY();
	}

	@Override
	public boolean intakeCube() {
		return assistStick.getRawButton(1);
	}

	@Override
	public boolean releaseCube() {
		return assistStick.getRawButton(2);
	}

	@Override
	public boolean clampRollers() {
		return assistStick.getRawButton(4);
	}

	@Override
	public boolean unClampRollers() {
		return assistStick.getRawButton(5);
	}

	@Override
	public boolean climbRung() {
		return assistStick.getRawButton(3);
	}	

	@Override
	public boolean rotateRollersIn() {
		return assistStick.getRawButton(6);
	}

	@Override
	public boolean rotateRollersOut() {
		return assistStick.getRawButton(7);
	}

	@Override
	public boolean liftToLowScale() {
		return assistStick.getRawButton(8);
	}

	@Override
	public boolean liftToSwitch() {
		return assistStick.getRawButton(9);
	}

	@Override
	public boolean liftToMidScale() {
		return assistStick.getRawButton(10);
	}

	@Override
	public boolean liftToHighScale() {
		return assistStick.getRawButton(11);
	}

	@Override
	public boolean clampCube() {
		return assistStick.getRawButton(1);
	}

	@Override
	public boolean unClampCube() {
		return assistStick.getRawButton(2);
	}
	
	@Override
	public boolean disableLimitSwitches() {
		return assistStick.getRawButton(4);
	}

}
