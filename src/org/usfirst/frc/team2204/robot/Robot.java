package org.usfirst.frc.team2204.robot;

import org.usfirst.frc.team2204.robot.subsystems.*;
import org.usfirst.frc.team2204.robot.auto.modes.*;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends TimedRobot {

	private Drive mDrive = Drive.getInstance();
	private Intake mIntake = Intake.getInstance();
	private Lift mLift = Lift.getInstance();
	private Controls mControls = Controls.getInstance();
	private LED mLED = LED.getInstance();
	
	private String gameData;
	private Command autoCommand;
	private SendableChooser<Command> autoChooser;
	
	private UsbCamera c922 = CameraServer.getInstance().startAutomaticCapture();
	
	private Timer timer = new Timer();

	
    @Override
    public void robotInit() {
    	try {
    		gameData = DriverStation.getInstance().getGameSpecificMessage();
    		
    		autoChooser = new SendableChooser<Command>();
    		autoChooser.addDefault("Start on Left and Score Left Switch", new StartOnLeftScoreLeftSwitch());
    		autoChooser.addObject("Start on left score right switch", new StartOnLeftScoreRightSwitch());
    		autoChooser.addObject("Start on right score left switch", new StartOnRightScoreLeftSwitch());
    		autoChooser.addObject("Start on right score right switch", new StartOnRightScoreRightSwitch());
    		autoChooser.addObject("Start on middle score left switch", new StartOnMiddleScoreLeftSwitch());		
    		autoChooser.addObject("Start on middle score right switch", new StartOnMiddleScoreRightSwitch());
    		SmartDashboard.putData("Auto Command", autoChooser);
    		
    		c922.setResolution(320, 240); //change to 640 x 480 if nothing shows up on the driver station also change fps to 10
    		c922.setFPS(20);
    		
    	} catch (Throwable t) {
    		throw t;
    	}
    }
    
    @Override
    public void disabledInit() {

    }

    @Override
    public void disabledPeriodic() {

    }

    @Override
    public void autonomousInit() { 	
    	timer.start();
    	
//    	if (gameData == "LRL") {
//    		autoCommand = autoChooser.getSelected();
//    	} else if (gameData == "RRR") {
//    		autoCommand = autoChooser.getSelected();
//    	} else {
//    		
//    	}
    	
    	mLED.setColor(LED.rainbow);
    }
    
    @Override
    public void autonomousPeriodic() {
    	
    	double time = timer.get();
    	//Cross the line at least
    	//not the whole robot needs to be past the line, just any part of it to get the 5 point
    	//the reason why there is a case for clamping the cube is because the cube might not fit all the way
    	//in our frame and because of that we can't start with the cube so we just put the cube in front 
    	//of the robot on the field and clamp it 0.5 seconds into auto
    	
    	if (time < 0.5) {
    		mDrive.curveDrive(0.6, 0, false);
    	} else if (time > 0.51 && time < 0.52) {
    		mDrive.curveDrive(0.6, 0, false);
    		mIntake.clampCube();
    	} else if (time > 0.52 && time < 5) {
    		mDrive.curveDrive(0.6, 0, false);
    	} else {
    		mLED.setColor(LED.red);
    	}
    	
    }

    @Override
    public void teleopInit() {
    	mLED.setColor(LED.fluroPurple);
    }
	
    /* THINGS TO DO AT COMPETITION 
     * calibrate drive sparks, there are 4 in total being used and we have 8 so we can see if there are faulty sparks making one
     * gearbox run slower than the other. 
     * If that doesn't work, slow down the faster side of the drivetrain by multiplying that side by a constant that 
     * will match the speed of the other side, this is a trial and error process. 
     * Change the left or right speed equalizer in constants then change mDrive.drive to mDrive.curveDrive 
     * You can also add/subtract a value to turn so it makes the robot "turn" to a straight line. 
     * If something doesn't work, try taking out the if statement and testing it alone, it might be an "impossible if". 
     */
    @Override
    public void teleopPeriodic() {
    	try {
    		
    		double throttle = mControls.getThrottle();
    		double turn = mControls.getTurn();
    		double liftSpeed = -mControls.getLiftSpeed();
    		
    		boolean isQuickTurn = mControls.isQuickTurn();
    		boolean brakeRobot = mControls.brakeRobot();
    		
    		if (!brakeRobot) {
    			mDrive.drive(-throttle * Constants.speedLimiter, turn * Constants.speedLimiter, isQuickTurn);
    		} else if (brakeRobot) {
    			mDrive.drive(0, 0, false);
    		}

    		boolean topLiftlimitSwitch = mLift.stopLiftUp();
    		boolean botLiftLimitSwitch = mLift.stopLiftDown();
    		
    		boolean isClimbing = mControls.climbRung();
    		
    		if (mControls.disableLimitSwitches()) {
    			topLiftlimitSwitch = false;
    			botLiftLimitSwitch = false;
    		} else if (!mControls.disableLimitSwitches()) {
    			topLiftlimitSwitch = mLift.stopLiftUp();
    			botLiftLimitSwitch = mLift.stopLiftDown();
    		}
    		
    		boolean cubeDetected = mIntake.cubeDetected();
    		
    		if (mControls.clampCube()) {
    			mIntake.clampCube();
    		} else if (mControls.unClampCube()) {
    			mIntake.unClampCube();
    		} else if (cubeDetected) {
    			mIntake.clampCube();
    		}
    		 
    		if (!isClimbing && !topLiftlimitSwitch && !botLiftLimitSwitch) {
    			mLift.moveLift(liftSpeed * Constants.liftSpeedLimter);
    		} else if (isClimbing && !topLiftlimitSwitch && !botLiftLimitSwitch) {
    			mLift.climbRung(0.8);
    		} else if (mControls.liftToHighScale() && !topLiftlimitSwitch && !botLiftLimitSwitch) {
    			mLift.moveToHighScale();
    		} else if (mControls.liftToMidScale() && !topLiftlimitSwitch && !botLiftLimitSwitch) {
    			mLift.moveToMidScale();
    		} else if (mControls.liftToLowScale() && !topLiftlimitSwitch && !botLiftLimitSwitch) {
    			mLift.moveToLowScale();
    		} else if (mControls.liftToSwitch() && !topLiftlimitSwitch && !botLiftLimitSwitch) {
    			mLift.moveToSwitch();
    		} else {
    			mLift.moveLift(0);
    		}
    		
    		if (topLiftlimitSwitch) {
    			mLift.moveLift(0);
    		} else if (botLiftLimitSwitch) {
    			mLift.moveLift(0);
    		}
    	
    		
    	} catch (Throwable t) {
    		throw t;
    	}
    }
    
    @Override
    public void testInit() {

    }
    
    @Override
    public void testPeriodic() {
    	
    	try {
    		
    		
    	} catch (Throwable t) {
    		throw t;
    	}
    	
    }
    
}
