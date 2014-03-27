package edu.wpi.first.wpilibj;

/**
 *
 * @author Patrick
 */
public class TestCode extends IterativeRobot {

    Jaguar leftMotor;
    Talon rightMotor;

    @Override
    public void robotInit() {
        leftMotor = new Jaguar(1);
        rightMotor = new Talon(2);
    }

    @Override
    public void teleopPeriodic() {
        leftMotor.set(1);
        rightMotor.set(-1);
    }
}
