package edu.wpi.first.wpilibj;

/**
 *
 * @author Patrick
 */
public class TestCode extends IterativeRobot {

    Jaguar leftJag, rightJag;

    @Override
    public void robotInit() {
        leftJag = new Jaguar(1);
        rightJag = new Jaguar(1);
    }

    @Override
    public void teleopPeriodic() {
        leftJag.set(1);
        rightJag.set(-1);
    }
}
