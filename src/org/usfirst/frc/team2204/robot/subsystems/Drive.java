package org.usfirst.frc.team2204.robot.subsystems;

import org.usfirst.frc.team2204.robot.Constants;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class Drive extends Subsystem {

	private static Drive getDrive = new Drive();
	
	public static Drive getInstance() {
		return getDrive;
	}
	
	private Spark leftSide, rightSide;
    private DifferentialDrive differentialDrive;
    protected AHRS navX;

    private Drive() {
    	leftSide = new Spark(Constants.leftDriveSparkGroup);
    	rightSide = new Spark(Constants.rightDriveSparkGroup);
        differentialDrive = new DifferentialDrive(leftSide, rightSide);    
     } 
    
    public void drive(double throttle, double turn, boolean isQuickTurn) {
    	differentialDrive.curvatureDrive(throttle, turn, isQuickTurn);
    }
    
    public static final double kDefaultQuickStopThreshold = 0.2;
    public static final double kDefaultQuickStopAlpha = 0.1;
    
    private double m_quickStopThreshold = kDefaultQuickStopThreshold;
    private double m_quickStopAlpha = kDefaultQuickStopAlpha;
    private double m_quickStopAccumulator = 0.0;
       
    public void curveDrive(double xSpeed, double zRotation, boolean isQuickTurn) {
    	xSpeed = limit(xSpeed);
        xSpeed = applyDeadband(xSpeed, m_deadband);

        zRotation = limit(zRotation);
        zRotation = applyDeadband(zRotation, m_deadband);

        double angularPower;
        boolean overPower;

        if (isQuickTurn) {
        	if (Math.abs(xSpeed) < m_quickStopThreshold) {
        		m_quickStopAccumulator = (1 - m_quickStopAlpha) * m_quickStopAccumulator
                + m_quickStopAlpha * limit(zRotation) * 2;
          }
        	overPower = true;
        	angularPower = zRotation;
        } else {
        	overPower = false;
        	angularPower = Math.abs(xSpeed) * zRotation - m_quickStopAccumulator;

          if (m_quickStopAccumulator > 1) {
        	  m_quickStopAccumulator -= 1;
          } else if (m_quickStopAccumulator < -1) {
        	  m_quickStopAccumulator += 1;
          } else {
        	  m_quickStopAccumulator = 0.0;
          }
        }

        double leftMotorOutput = xSpeed + angularPower;
        double rightMotorOutput = xSpeed - angularPower;

        // If rotation is overpowered, reduce both outputs to within acceptable range
        if (overPower) {
          if (leftMotorOutput > 1.0) {
            rightMotorOutput -= leftMotorOutput - 1.0;
            leftMotorOutput = 1.0;
          } else if (rightMotorOutput > 1.0) {
            leftMotorOutput -= rightMotorOutput - 1.0;
            rightMotorOutput = 1.0;
          } else if (leftMotorOutput < -1.0) {
            rightMotorOutput -= leftMotorOutput + 1.0;
            leftMotorOutput = -1.0;
          } else if (rightMotorOutput < -1.0) {
            leftMotorOutput -= rightMotorOutput + 1.0;
            rightMotorOutput = -1.0;
          }
        }
      
        differentialDrive.tankDrive(leftMotorOutput * Constants.leftSpeedEqualizer, rightMotorOutput * Constants.rightSpeedEqualizer);
    }
    
    public void cheesyDrive(double throttle, double wheel, boolean isQuickTurn, boolean isHighGear) {
    	
        final double kThrottleDeadband = 0.02;
    	final double kWheelDeadband = 0.02;

    	final double kHighWheelNonLinearity = 0.65;
        final double kLowWheelNonLinearity = 0.5;
    	    
    	final double kHighNegInertiaScalar = 4.0;

    	final double kLowNegInertiaThreshold = 0.65;
    	final double kLowNegInertiaTurnScalar = 3.5;
    	final double kLowNegInertiaCloseScalar = 4.0;
        final double kLowNegInertiaFarScalar = 5.0;

    	final double kHighSensitivity = 0.95;
    	final double kLowSensitiity = 1.3;
    
    	final double kQuickStopDeadband = 0.2;
    	final double kQuickStopWeight = 0.1;
    	final double kQuickStopScalar = 5.0;

    	double mOldWheel = 0.0;
    	double mQuickStopAccumlator = 0.0;
    	double mNegInertiaAccumlator = 0.0;
    
    	wheel = handleDeadband(wheel, kWheelDeadband);
        throttle = handleDeadband(throttle, kThrottleDeadband);

        double negInertia = wheel - mOldWheel;
        mOldWheel = wheel;

        double wheelNonLinearity;
        if (isHighGear) {
            wheelNonLinearity = kHighWheelNonLinearity;
            final double denominator = Math.sin(Math.PI / 2.0 * wheelNonLinearity);
            wheel = Math.sin(Math.PI / 2.0 * wheelNonLinearity * wheel) / denominator;
            wheel = Math.sin(Math.PI / 2.0 * wheelNonLinearity * wheel) / denominator;
        } else {
            wheelNonLinearity = kLowWheelNonLinearity;
            final double denominator = Math.sin(Math.PI / 2.0 * wheelNonLinearity);
            wheel = Math.sin(Math.PI / 2.0 * wheelNonLinearity * wheel) / denominator;
            wheel = Math.sin(Math.PI / 2.0 * wheelNonLinearity * wheel) / denominator;
            wheel = Math.sin(Math.PI / 2.0 * wheelNonLinearity * wheel) / denominator;
        }

        double leftPwm, rightPwm, overPower;
        double sensitivity;

        double angularPower;
        double linearPower;

        double negInertiaScalar;
        if (isHighGear) {
            negInertiaScalar = kHighNegInertiaScalar;
            sensitivity = kHighSensitivity;
        } else {
            if (wheel * negInertia > 0) {
                // If we are moving away from 0.0, aka, trying to get more wheel.
                negInertiaScalar = kLowNegInertiaTurnScalar;
            } else {
                // Otherwise, we are attempting to go back to 0.0.
                if (Math.abs(wheel) > kLowNegInertiaThreshold) {
                    negInertiaScalar = kLowNegInertiaFarScalar;
                } else {
                    negInertiaScalar = kLowNegInertiaCloseScalar;
                }
            }
            sensitivity = kLowSensitiity;
        }
        double negInertiaPower = negInertia * negInertiaScalar;
        mNegInertiaAccumlator += negInertiaPower;

        wheel = wheel + mNegInertiaAccumlator;
        if (mNegInertiaAccumlator > 1) {
            mNegInertiaAccumlator -= 1;
        } else if (mNegInertiaAccumlator < -1) {
            mNegInertiaAccumlator += 1;
        } else {
            mNegInertiaAccumlator = 0;
        }
        linearPower = throttle;

        if (isQuickTurn) {
            if (Math.abs(linearPower) < kQuickStopDeadband) {
                double alpha = kQuickStopWeight;
                mQuickStopAccumlator = (1 - alpha) * mQuickStopAccumlator
                        + alpha * limit(wheel, 1.0) * kQuickStopScalar;
            }
            overPower = 1.0;
            angularPower = wheel;
        } else {
            overPower = 0.0;
            angularPower = Math.abs(throttle) * wheel * sensitivity - mQuickStopAccumlator;
            if (mQuickStopAccumlator > 1) {
                mQuickStopAccumlator -= 1;
            } else if (mQuickStopAccumlator < -1) {
                mQuickStopAccumlator += 1;
            } else {
                mQuickStopAccumlator = 0.0;
            }
        }

        rightPwm = leftPwm = linearPower;
        leftPwm += angularPower;
        rightPwm -= angularPower;

        if (leftPwm > 1.0) {
            rightPwm -= overPower * (leftPwm - 1.0);
            leftPwm = 1.0;
        } else if (rightPwm > 1.0) {
            leftPwm -= overPower * (rightPwm - 1.0);
            rightPwm = 1.0;
        } else if (leftPwm < -1.0) {
            rightPwm += overPower * (-1.0 - leftPwm);
            leftPwm = -1.0;
        } else if (rightPwm < -1.0) {
            leftPwm += overPower * (-1.0 - rightPwm);
            rightPwm = -1.0;
        }
    
        differentialDrive.tankDrive(leftPwm * Constants.leftSpeedEqualizer, rightPwm * Constants.rightSpeedEqualizer);
    }
    
    public static double limit(double v, double maxMagnitude) {
        return limit(v, -maxMagnitude, maxMagnitude);
    }

    public static double limit(double v, double min, double max) {
        return Math.min(max, Math.max(min, v));
    }
    
    protected double limit(double value) {
        if (value > 1.0) {
          return 1.0;
        } if (value < -1.0) {
          return -1.0;
        }
        return value;
    }

    protected double applyDeadband(double value, double deadband) {
        if (Math.abs(value) > deadband) {
        	if (value > 0.0) {
        	  return (value - deadband) / (1.0 - deadband);
          } else {
        	  return (value + deadband) / (1.0 - deadband);
          }
        } else {
        	return 0.0;
        }
    }
    
    public static final double kDefaultDeadband = 0.02;
    public static final double kDefaultMaxOutput = 1.0;

    protected double m_deadband = kDefaultDeadband;
    protected double m_maxOutput = kDefaultMaxOutput;
    
    public void setDeadband(double deadband) {
        m_deadband = deadband;
      }
    
    public double handleDeadband(double val, double deadband) {
        return (Math.abs(val) > Math.abs(deadband)) ? val : 0.0;
    }
    
	@Override
	protected void initDefaultCommand() {

	}

}
