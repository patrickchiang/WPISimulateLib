package edu.wpi.first.wpilibj;

/**
 *
 * @author Patrick
 */
public class RobotDrive {

    public MotorType kFrontLeft;
    public MotorType kFrontRight;
    public MotorType kRearLeft;
    public MotorType kRearRight;
    private double sensitivity = 0;

    public RobotDrive(int leftMotorChannel, int rightMotorChannel) {
        PWM left = new PWM(leftMotorChannel);
        PWM right = new PWM(rightMotorChannel);

        kFrontLeft = new MotorType(left);
        kFrontRight = new MotorType(right);
    }

    public RobotDrive(int frontLeftMotor, int rearLeftMotor,
            int frontRightMotor, int rearRightMotor) {
        PWM frontLeft = new PWM(frontLeftMotor);
        PWM rearLeft = new PWM(rearLeftMotor);
        PWM frontRight = new PWM(frontRightMotor);
        PWM rearRight = new PWM(rearRightMotor);

        kFrontLeft = new MotorType(frontLeft);
        kFrontRight = new MotorType(frontRight);
        kRearLeft = new MotorType(rearLeft);
        kRearLeft = new MotorType(rearRight);
    }

    public void drive(double outputMagnitude, double curve) {
    }

    public void tankDrive(Joystick leftStick, Joystick rightStick) {
        tankDrive(leftStick, rightStick, false);
    }

    public void tankDrive(Joystick leftStick, Joystick rightStick, boolean squaredInputs) {
    }

    public void tankDrive(Joystick leftStick, int leftAxis, Joystick rightStick, int rightAxis) {
        tankDrive(leftStick, leftAxis, rightStick, rightAxis, false);
    }

    public void tankDrive(Joystick leftStick, int leftAxis, Joystick rightStick, int rightAxis, boolean squaredInputs) {
    }

    public void tankDrive(double leftValue, double rightValue, boolean squaredInputs) {
    }

    public void tankDrive(double leftValue, double rightValue) {
        tankDrive(leftValue, rightValue, false);
    }

    public void arcadeDrive(Joystick stick, boolean squaredInputs) {
    }

    public void arcadeDrive(Joystick stick) {
        arcadeDrive(stick, false);
    }

    public void arcadeDrive(Joystick moveStick, int moveAxis, Joystick rotateStick, int rotateAxis, boolean squaredInputs) {
    }

    public void arcadeDrive(Joystick moveStick, int moveAxis, Joystick rotateStick, int rotateAxis) {
        arcadeDrive(moveStick, moveAxis, rotateStick, rotateAxis, false);
    }

    public void arcadeDrive(double moveValue, double rotateValue, boolean squaredInputs) {
    }

    public void arcadeDrive(double moveValue, double rotateValue) {
        arcadeDrive(moveValue, rotateValue, false);
    }

    public void mecanumDrive_Polar(double magnitude, double direction, double rotation) {
    }

    public void setLeftRightMotorOutputs(double leftOutput, double rightOutput) {
    }

    public void setSensitivity(double sensitivity) {
        this.sensitivity = sensitivity;
    }

    public void setInvertedMotor(RobotDrive.MotorType motor, boolean isInverted) {
    }

    public class MotorType {

        private PWM motor;
        private boolean inverted = false;

        public MotorType(PWM motor) {
            this.motor = motor;
        }

        public void setInverted(boolean isInverted) {
            inverted = isInverted;
        }

        public boolean getInverted() {
            return inverted;
        }

        public PWM get() {
            return motor;
        }
    }
}
