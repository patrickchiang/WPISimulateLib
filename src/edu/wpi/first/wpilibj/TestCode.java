package edu.wpi.first.wpilibj;

/**
 *
 * @author Patrick
 */
public class TestCode extends IterativeRobot {

    Jaguar leftMotor;
    Solenoid pump;

    @Override
    public void robotInit() {
        leftMotor = new Jaguar(1);
        pump = new Solenoid(1);
    }

    @Override
    public void teleopPeriodic() {
        pump.set(true);
        leftMotor.set(-1);
    }
}
