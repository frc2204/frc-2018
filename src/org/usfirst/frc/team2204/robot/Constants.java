package org.usfirst.frc.team2204.robot;

public class Constants {

	/*PCM Cable Ports for Single 3 Pin Connectors
	public static final int topLeftSpark = 0;
	public static final int backLeftSpark = 1;
	public static final int topRightSpark = 2;
	public static final int backRightSpark = 3;
	public static final int liftSpark = 4;
	public static final int leftIntake = 5;
	public static final int rightIntake = 6;
	public static final int leftIntakeRotation = 7;
	public static final int rightIntakeRotation = 8;
	*/
	
	//PWM Cable Ports using Y Cables
	public static final int leftDriveSparkGroup = 0;
	public static final int rightDriveSparkGroup = 1;
	public static final int liftTalon = 1;
	public static final int intakeSparkGroup = 3;
//	public static final int leftIntake = 3;
//	public static final int rightIntake = 4;
	public static final int rotatorSparks = 5;
	//Final Ports - Feb 19 8:23 PM 

	
	//PWM Port for LED Driver
	public static final int ledPort = 2;
	
	//Speed Limiters for high, low, normal speed, and the lift. 
    public static final double highSpeedLimiter = 1.0;
    public static final double lowSpeedLimiter = 0.65;
    public static final double speedLimiter = 0.70;
    public static final double liftSpeedLimter = 0.7;
    
    //Throttle and curve speed limiters for more precise turning
    public static final double throttleSpeedLimiter = 0.9;
    public static final double curveSpeedLimiter = 0.95;
    
   //Used to make the robot drive straight, one side is faster than the other and these values are supposed to fix it. 
   //Values are both 1 as they are not set yet. March 16
    public static final double leftSpeedEqualizer = 1;
    public static final double rightSpeedEqualizer = 1;
    
    //This value is subtracted by the joystick curve to make the robot drive straight.
    //For example of the robot drives straight when the turn joystick is set to 0.2, this value would be set to 0.2;
    //Value isn't set yet. March 16
    public static final double turnEqualizer = 0;
    
    //Joystick deadzone, Won't get any inputs if the joystick values are under what this value is set to
    public static final double deadZone = 0.05;
    
    public static final int encoderPPR = 4096;
    public static final int liftGearReduction = 12;
    public static final double sprocketCircumference = 2 * Math.PI * 0.716; //0.716 is pitch radius of 12T sprocket
    public static final double distancePerPulse = sprocketCircumference/(encoderPPR * liftGearReduction);
    
    public static final int cimFreeSpeed = 5310;
    public static final double cimReducedFreeSpeed = 442.5; // FreeSpeed of CIM, in RPM, after 12:1 gearbox reduction
    public static final double liftstopRate = 44.25; 		//the minimum rate at where the encoder will stop tracking rotations
    												 // at 1/10 of CIM Reduced FreeSpeed
    
    public static final double switchHeightInInches = 18.75; //1' 6.75"
    public static final double highScaleHeightInInches = 72; //6'
    public static final double midScaleHeightInInches = 60; //5'
    public static final double lowScaleHeightInInches = 48; //4'
    public static final double numPulsesToSwitch = switchHeightInInches / distancePerPulse; // distance = rate * counter;
    public static final double numPulsesToHighScale = highScaleHeightInInches / distancePerPulse;
    public static final double numPulsesToMidScale = midScaleHeightInInches / distancePerPulse;
    public static final double numPulsesToLowScale = lowScaleHeightInInches / distancePerPulse;
    
    public static final int topLiftLimitSwitchPort = 0;
    public static final int botLiftLimitSwitchPort = 1;
}
