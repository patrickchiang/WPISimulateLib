package edu.wpi.first.wpilibj;

/**
 *
 * @author Patrick
 */
public class TestCode extends IterativeRobot {

    Jaguar leftMotor;

    @Override
    public void robotInit() {
        leftMotor = new Jaguar(1);
    }

    @Override
    public void teleopPeriodic() {
        leftMotor.set(-1);
        System.out.println(leftMotor.getRaw());
    }
}
